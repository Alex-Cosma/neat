package thesis.genetic.processing;

import org.deeplearning4j.berkeley.Pair;
import org.jenetics.util.Seq;
import thesis.genetic.models.NeuralGene;
import thesis.neural.models.Neuron;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alex on 22/07/2016.
 */
public class ChromosomeExtractors {

    public static List<Neuron> extractNeuronsFromGenes(Seq<NeuralGene> genes) {
        Set<Neuron> neurons = new HashSet<>();
        genes.stream().forEach(it -> {
            neurons.add(it.getAllele().getIn());
            neurons.add(it.getAllele().getOut());
        });

        return new ArrayList<>(neurons);
    }

    public static Map<Pair<Integer, Integer>, Boolean> extractSynapsesMapFromGenes(Seq<NeuralGene> genes) {
        Map<Pair<Integer, Integer>, Boolean> synapseMap = new HashMap<>();

        genes.stream().forEach(it -> synapseMap.put(
                new Pair<>(it.getAllele().getIn().getId(), it.getAllele().getOut().getId()),
                Boolean.TRUE)
        );

        return synapseMap;
    }

    public static List<Integer> extractDistinctLayersFromGenes(Seq<NeuralGene> genes) {
        return extractNeuronsFromGenes(genes).stream().mapToInt(Neuron::getLayer).distinct().boxed().sorted().collect(Collectors.toList());
    }

    public static Map<Integer, List<Neuron>> extractAllNeuronsOnLayers(Seq<NeuralGene> genes) {
        return extractAllNeuronsOnLayers(genes, extractDistinctLayersFromGenes(genes));
    }

    public static Map<Integer, List<Neuron>> extractAllNeuronsOnLayers(Seq<NeuralGene> genes, List<Integer> distinctLayers) {
        Map<Integer, List<Neuron>> neuronsOnLayers = new HashMap<>();
        List<Neuron> uniqueNeurons = extractNeuronsFromGenes(genes);

        distinctLayers.forEach(layer ->
                neuronsOnLayers.put(layer.equals(Integer.MAX_VALUE) ? distinctLayers.size() - 1 : layer, new ArrayList<>()));

        uniqueNeurons.forEach(
                node -> {
                    Integer layer = node.getLayer();
                    layer = layer.equals(Integer.MAX_VALUE) ? distinctLayers.size() - 1 : layer;
                    neuronsOnLayers.get(layer).add(node);
                }
        );
        return neuronsOnLayers;
    }
}
