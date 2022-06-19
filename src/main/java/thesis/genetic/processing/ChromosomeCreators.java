package thesis.genetic.processing;

import thesis.genetic.models.NeuralGene;
import thesis.neural.models.Neuron;
import thesis.neural.models.NeuronType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static thesis.genetic.processing.Randomizer.initialConnectionWeight;
import static thesis.neural.models.NeuronType.BIAS;
import static thesis.neural.models.NeuronType.INPUT;

/**
 * Created by Alex on 14/07/2016.
 */
public class ChromosomeCreators {

    public static List<Neuron> createNeurons(int nrNeurons, NeuronType neuronType, int idOffset) {
        List<Neuron> neurons = new ArrayList<>();
        IntStream.range(0, nrNeurons).forEach(i -> neurons.add(Neuron.of(neuronType, i + idOffset, neuronType.equals(INPUT) ? 0 : Integer.MAX_VALUE)));
        return neurons;
    }

    public static List<NeuralGene> createGeneList(List<Neuron> inputNeurons, List<Neuron> outputNeurons, NetworkIdMap networkIdMap) {
        List<NeuralGene> geneList = new ArrayList<>();


        for (Neuron inputNeuron : inputNeurons) {
            for (Neuron outputNeuron : outputNeurons) {
                NeuralGene gene = NeuralGene.of(inputNeuron, outputNeuron, initialConnectionWeight(), true, networkIdMap);
                geneList.add(gene);
            }
        }

        addBiasNode(inputNeurons, outputNeurons, geneList, networkIdMap);


        return geneList;
    }

    private static void addBiasNode(List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<NeuralGene> geneList, NetworkIdMap networkIdMap) {
        Neuron biasNeuron = Neuron.of(BIAS, inputNeurons.size() + outputNeurons.size() + 1, 0);
        biasNeuron.setValue(1d);
        for (Neuron outputNeuron : outputNeurons) {
            NeuralGene biasGene = NeuralGene.of(biasNeuron, outputNeuron, initialConnectionWeight(), true, networkIdMap);
            geneList.add(biasGene);
        }
    }
}
