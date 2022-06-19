package thesis.genetic.mappers;

import org.canova.api.berkeley.Pair;
import org.jenetics.Genotype;
import org.jenetics.util.RandomRegistry;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;
import thesis.neural.networks.ConsecutiveLayerNetwork;
import thesis.neural.networks.Network;
import thesis.neural.networks.SkipLayerNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static thesis.genetic.processing.ChromosomeExtractors.extractAllNeuronsOnLayers;
import static thesis.genetic.processing.ChromosomeExtractors.extractNeuronsFromGenes;

/**
 * Created by Alex on 21/07/2016.
 */
public class GenotypeToNetworkMapper {

    private final NetworkIdMap networkIdMap;

    public GenotypeToNetworkMapper(NetworkIdMap networkIdMap) {
        this.networkIdMap = networkIdMap;
    }

    public Network mapToSkipLayerNetwork(Genotype<NeuralGene> genotype) {
        List<Neuron> neurons = extractNeuronsFromGenes(genotype.getChromosome().toSeq());
        Map<Integer, Neuron> neuronIdMap = neurons.stream().collect(toMap(Neuron::getId, identity()));
        Map<Neuron, List<Synapse>> incomingSynapsesMap = neurons.stream().map(n -> Pair.makePair(n, extractIncomingSynapses(n, genotype))).collect(toMap(Pair::getFirst, Pair::getSecond));

        return new SkipLayerNetwork(neuronIdMap, incomingSynapsesMap);
    }

    private List<Synapse> extractIncomingSynapses(Neuron n, Genotype<NeuralGene> genotype) {
        return genotype.getChromosome().stream().map(NeuralGene::getAllele).filter(g -> g.getOut().getId().equals(n.getId())).collect(Collectors.toList());
    }

    @Deprecated
    public Network mapToConsecutiveLayerNetwork(Genotype<NeuralGene> genotype) {
        Map<Integer, List<Neuron>> nodesOnLayers = extractAllNeuronsOnLayers(genotype.getChromosome().toSeq());
        ArrayList<Integer> layers = new ArrayList<>(nodesOnLayers.keySet());
        List<INDArray> synapsesWeights = new ArrayList<>();

        for (int layerNr = 0; layerNr < layers.size() - 1; layerNr++) {

            Integer leftLayer = layers.get(layerNr);
            Integer rightLayer = layers.get(layerNr + 1);

            List<Neuron> leftLayerNeurons = nodesOnLayers.get(leftLayer);
            List<Neuron> rightLayerNeurons = nodesOnLayers.get(rightLayer);

            INDArray weights = Nd4j.create(leftLayerNeurons.size(), rightLayerNeurons.size());

            for (int j = 0; j < leftLayerNeurons.size(); j++) {
                Neuron leftLayerNeuron = leftLayerNeurons.get(j);
                double[] w = new double[rightLayerNeurons.size()];
                for (int k = 0; k < rightLayerNeurons.size(); k++) {
                    Neuron rightLayerNeuron = rightLayerNeurons.get(k);
                    Synapse syn = networkIdMap.getFromSynapsesMap(leftLayerNeuron.getId(), rightLayerNeuron.getId());
                    w[k] = syn != null ? syn.getWeight() : RandomRegistry.getRandom().nextDouble();
                }

                weights.putSlice(j, Nd4j.create(w));
            }

            synapsesWeights.add(weights);
        }
        return new ConsecutiveLayerNetwork(synapsesWeights);
    }

}
