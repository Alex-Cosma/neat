package thesis.genetic.modifiers.mutators;

import org.deeplearning4j.berkeley.Pair;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.extra.MutationResult;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static thesis.genetic.processing.ChromosomeExtractors.extractNeuronsFromGenes;
import static thesis.genetic.processing.ChromosomeExtractors.extractSynapsesMapFromGenes;
import static thesis.neural.models.NeuronType.*;

/**
 * Created by Alex on 14/07/2016.
 */
public class NeuralAddNeuronMutator extends AbstractNeuralMutator {

    private final NetworkIdMap networkIdMap;

    public NeuralAddNeuronMutator(double probability, NetworkIdMap networkIdMap) {
        super(probability);
        this.networkIdMap = networkIdMap;
    }

    @Override
    protected MutationResult mutate(MSeq<NeuralGene> genes, double p) {
        List<Neuron> neurons = extractNeuronsFromGenes(genes);
        Map<Pair<Integer, Integer>, Boolean> synapseMap = extractSynapsesMapFromGenes(genes);

        Random random = RandomRegistry.getRandom();
        if (random.nextDouble() < p) {
            List<NeuralGene> genesCopy = new ArrayList<>(genes.asList());
            boolean addedNeuron = false;
            while (!addedNeuron) {
                Neuron inNeuron = neurons.get(random.nextInt(neurons.size()));
                Neuron outNeuron = neurons.get(random.nextInt(neurons.size()));
                if (nodeConditionsMet(inNeuron, outNeuron) && synapseMap.containsKey(new Pair<>(inNeuron.getId(), outNeuron.getId()))) {
                    NeuralGene oldGene = genesCopy.stream().filter(g -> (g.getAllele().getIn().getId().equals(inNeuron.getId()) && g.getAllele().getOut().getId().equals(outNeuron.getId()))).findFirst().get();

                    oldGene.getAllele().setEnabled(false);
                    Neuron newNeuron = Neuron.of(inNeuron, outNeuron, networkIdMap);

                    int r = RandomRegistry.getRandom().nextInt();
                    NeuralGene inboundGene = NeuralGene.of(inNeuron, newNeuron, 1, true, networkIdMap);
                    NeuralGene outboundGene = NeuralGene.of(newNeuron, outNeuron, oldGene.getAllele().getWeight(), true, networkIdMap);
                    genesCopy.add(inboundGene);
                    genesCopy.add(outboundGene);

                    addedNeuron = true;
                }
            }
            List<NeuralGene> newGenes = genesCopy.stream().map(NeuralGene::newInstance).collect(Collectors.toList());
            return new MutationResult(MSeq.of(newGenes), 2);
        }
        return new MutationResult(genes, 0);

    }

    private boolean nodeConditionsMet(Neuron inNeuron, Neuron outNeuron) {
        return (!(inNeuron.equals(outNeuron)) &&
                (betweenInputAndHidden(inNeuron, outNeuron) ||
                        betweenInputAndOutput(inNeuron, outNeuron) ||
                        betweenHiddenAndHidden(inNeuron, outNeuron) ||
                        betweenHiddenAndOutput(inNeuron, outNeuron)
                )
        );
    }

    private boolean betweenInputAndHidden(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(INPUT) && outNeuron.getNeuronType().equals(HIDDEN)
                && inNeuron.getLayer() == outNeuron.getLayer() - 2;
    }

    private boolean betweenInputAndOutput(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(INPUT) && outNeuron.getNeuronType().equals(OUTPUT);
    }

    private boolean betweenHiddenAndHidden(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(HIDDEN) && outNeuron.getNeuronType().equals(HIDDEN)
                && noPointingBack(inNeuron, outNeuron)
                && inNeuron.getLayer() == outNeuron.getLayer() - 2;
    }

    private boolean betweenHiddenAndOutput(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getNeuronType().equals(HIDDEN) && outNeuron.getNeuronType().equals(OUTPUT);
    }

    private boolean noPointingBack(Neuron inNeuron, Neuron outNeuron) {
        return inNeuron.getLayer() < outNeuron.getLayer();
    }
}
