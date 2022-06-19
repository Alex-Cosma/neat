package thesis.genetic.modifiers.mutators;

import org.deeplearning4j.berkeley.Pair;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.extra.MutationResult;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static thesis.genetic.processing.ChromosomeExtractors.extractNeuronsFromGenes;
import static thesis.genetic.processing.ChromosomeExtractors.extractSynapsesMapFromGenes;
import static thesis.genetic.processing.Randomizer.randomWeight;
import static thesis.neural.models.NeuronType.*;

/**
 * Created by Alex on 14/07/2016.
 */
public class NeuralAddSynapseMutator extends AbstractNeuralMutator {

    private final int maxTries;
    private final NetworkIdMap networkIdMap;

    public NeuralAddSynapseMutator(double probability, int maxTries, NetworkIdMap networkIdMap) {
        super(probability);
        this.maxTries = maxTries;
        this.networkIdMap = networkIdMap;
    }

    @Override
    protected MutationResult mutate(MSeq<NeuralGene> genes, double p) {
        Random random = RandomRegistry.getRandom();

        if (random.nextDouble() < p) {

            Map<Pair<Integer, Integer>, Boolean> synapseMap = extractSynapsesMapFromGenes(genes);
            List<Neuron> neurons = extractNeuronsFromGenes(genes);
            List<Neuron> inputOrBiasNeurons = neurons.stream().filter(n -> n.getNeuronType().equals(INPUT) || n.getNeuronType().equals(BIAS)).collect(Collectors.toList());
            List<Neuron> hiddenNeurons = neurons.stream().filter(n -> n.getNeuronType().equals(HIDDEN)).collect(Collectors.toList());
            List<Neuron> outputNeurons = neurons.stream().filter(n -> n.getNeuronType().equals(OUTPUT)).collect(Collectors.toList());

            boolean addedSynapse = false;
            int nrTries = 0;

            while (!addedSynapse && nrTries < maxTries) {
                Neuron randomInputNeuron = inputOrBiasNeurons.get(Math.abs(random.nextInt(inputOrBiasNeurons.size())));
                Neuron inNeuron = random.nextBoolean() ? randomInputNeuron : hiddenNeurons.size() > 0 ? hiddenNeurons.get(Math.abs(random.nextInt(hiddenNeurons.size()))) : randomInputNeuron;
                Neuron randomOutputNeuron = outputNeurons.get(Math.abs(random.nextInt(outputNeurons.size())));
                Neuron outNeuron = random.nextBoolean() && hiddenNeurons.size() > 0 ? hiddenNeurons.get(Math.abs(random.nextInt(hiddenNeurons.size()))) : randomOutputNeuron;

                if (neuronsConditionsMet(inNeuron, outNeuron) && !synapseMap.containsKey(Pair.newPair(inNeuron.getId(), outNeuron.getId()))) {
                    NeuralGene newGene = NeuralGene.of(inNeuron, outNeuron, randomWeight(), true, networkIdMap);
                    genes = genes.append(
                            newGene
                    );
                    addedSynapse = true;
                }
                nrTries++;
            }
            return new MutationResult(genes, nrTries < maxTries ? 1 : 0);
        }
        return new MutationResult(genes, 0);
    }

    private boolean neuronsConditionsMet(Neuron inNeuron, Neuron outNeuron) {
        return (!(inNeuron.equals(outNeuron)) &&
                betweenInputAndHiddenOrOutput(inNeuron, outNeuron) ||
                betweenHiddenAndHiddenOrOutput(inNeuron, outNeuron) ||
                betweenBiasAndHiddenOrOutput(inNeuron, outNeuron)
        );
    }

    private boolean betweenBiasAndHiddenOrOutput(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(BIAS) &&
                ((outNeuron.getNeuronType().equals(HIDDEN)) || outNeuron.getNeuronType().equals(OUTPUT))
                && noPointingBack(inNeuron, outNeuron);
    }

    private boolean betweenHiddenAndHiddenOrOutput(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(HIDDEN) &&
                ((outNeuron.getNeuronType().equals(HIDDEN)) || outNeuron.getNeuronType().equals(OUTPUT))
                && noPointingBack(inNeuron, outNeuron);
    }

    private boolean betweenInputAndHiddenOrOutput(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(INPUT) && ((outNeuron.getNeuronType().equals(HIDDEN)) || outNeuron.getNeuronType().equals(OUTPUT));
    }


    private boolean noPointingBack(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getLayer() < outNeuron.getLayer();
    }

}