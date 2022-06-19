package thesis.neural.networks;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.model.GameState;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static thesis.neural.ActivationFunctions.sigmoidNeat;
import static thesis.neural.models.NeuronType.*;

/**
 * Created by Alex on 28/07/2016.
 */
public class SkipLayerNetwork implements Network {

    private final Map<Integer, Neuron> neuronIdMap;
    private final Map<Neuron, List<Synapse>> incomingSynapsesMap;

    public SkipLayerNetwork(Map<Integer, Neuron> neuronIdMap, Map<Neuron, List<Synapse>> incomingSynapsesMap) {
        this.neuronIdMap = neuronIdMap;
        this.incomingSynapsesMap = incomingSynapsesMap;
    }

    @Override
    public INDArray feed(GameState currentState) {
        return feedForward(currentState.getNormalizedState());
    }

    private INDArray feedForward(INDArray gameState) {
        initializeInputNeurons(gameState);
        initializeBiasNeuron();

        List<Neuron> hiddenOutputNeurons = neuronIdMap.values().stream().filter(n -> !n.getNeuronType().equals(INPUT)).collect(Collectors.toList());

        hiddenOutputNeurons.sort((n1, n2) -> n1.getLayer().compareTo(n2.getLayer()));

        hiddenOutputNeurons.forEach(n -> {
            List<Synapse> synapses = incomingSynapsesMap.get(n);
            double sum = 0;
            for (Synapse synapse : synapses) {
                if (synapse.isEnabled()) {
                    Double weight = synapse.getWeight();
                    Double value = neuronIdMap.get(synapse.getIn().getId()).getValue();
                    sum += weight * value;
                }
            }
            if (synapses.size() > 0) {
                n.setValue(sigmoidNeat(sum));
            }
        });

        List<Neuron> outputNeurons = neuronIdMap.values().stream().filter(neuron -> neuron.getNeuronType().equals(OUTPUT)).collect(Collectors.toList());

        INDArray output = Nd4j.create(outputNeurons.size());
        for (int i = 0; i < outputNeurons.size(); i++) {
            output.putScalar(i, outputNeurons.get(i).getValue());
        }

        return output;
    }


    private void initializeInputNeurons(INDArray normalizedState) {
        List<Neuron> inputNeurons = neuronIdMap.values().stream().filter(neuron -> neuron.getNeuronType().equals(INPUT)).sorted((n1, n2) -> n1.getId().compareTo(n2.getId())).collect(Collectors.toList());
        for (int i = 0; i < inputNeurons.size(); i++) {
            Double input = normalizedState.getDouble(i);
            inputNeurons.get(i).setValue(input);
        }
    }

    private void initializeBiasNeuron() {
        Neuron biasNeuron = neuronIdMap.values().stream().filter(neuron -> neuron.getNeuronType().equals(BIAS)).findFirst().get();
        biasNeuron.setValue(1d);
    }
}
