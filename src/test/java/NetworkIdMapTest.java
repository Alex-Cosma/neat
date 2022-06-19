import org.deeplearning4j.berkeley.Pair;
import org.jenetics.util.RandomRegistry;
import org.junit.Before;
import org.junit.Test;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;
import thesis.neural.models.NeuronType;
import thesis.neural.models.Synapse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static thesis.neural.models.NeuronType.HIDDEN;

/**
 * Created by Alex on 19/07/2016.
 */
public class NetworkIdMapTest {

    private static final Integer NR_INPUT_NEURONS = 8;
    private static final Integer NR_OUTPUT_NEURONS = 10;

    private NetworkIdMap idMap;
    private List<Neuron> inputNeurons;
    private List<Neuron> outputNeurons;

    @Before
    public void setUp() throws Exception {
        idMap = new NetworkIdMap(NR_INPUT_NEURONS, NR_OUTPUT_NEURONS);
        inputNeurons = new ArrayList<>();
        outputNeurons = new ArrayList<>();

        for (int i = 0; i < NR_INPUT_NEURONS; i++) {
            inputNeurons.add(Neuron.of(NeuronType.INPUT, i + 1, 0));
        }
        for (int i = 0; i < NR_OUTPUT_NEURONS; i++) {
            outputNeurons.add(Neuron.of(NeuronType.OUTPUT, i + 1 + inputNeurons.size(), Integer.MAX_VALUE));
        }
        int innovationNumber = 0;
        for (int in = 0; in < NR_INPUT_NEURONS; in++) {
            for (int out = 0; out < NR_OUTPUT_NEURONS; out++) {
                Synapse.of(
                        idMap.getOrCreateNewConnectionId(inputNeurons.get(in).getId(), outputNeurons.get(out).getId()),
                        inputNeurons.get(in),
                        outputNeurons.get(out),
                        1d,
                        true,
                        ++innovationNumber
                );
            }
        }
    }

    @Test
    public void testAddingNeuronAndSynapse() {
        Random r = RandomRegistry.getRandom();

        Neuron randomInputNeuron = inputNeurons.get(r.nextInt(inputNeurons.size()));
        Neuron randomOutputNeuron = outputNeurons.get(r.nextInt(outputNeurons.size()));

        Pair<Integer, Integer> idLayerPair = idMap.getNewNodeBetweenPairIdAndLayer(randomInputNeuron, randomOutputNeuron);
        Neuron hidden1 = Neuron.of(HIDDEN, idLayerPair.getFirst(), idLayerPair.getSecond());

        assertEquals(hidden1.getId(), Integer.valueOf(inputNeurons.size() + outputNeurons.size() + 2));

        Integer inNewId = idMap.getOrCreateNewConnectionId(randomInputNeuron.getId(), hidden1.getId());
        Integer newOldId = idMap.getOrCreateNewConnectionId(hidden1.getId(), randomOutputNeuron.getId());

        assertEquals(inNewId, Integer.valueOf(inputNeurons.size() * outputNeurons.size() + 1));
        assertEquals(newOldId, Integer.valueOf(inputNeurons.size() * outputNeurons.size() + 2));

        Integer newRandomInputNodeId = inputNeurons.get(r.nextInt(inputNeurons.size())).getId();
        while (newRandomInputNodeId.equals(randomInputNeuron.getId())) {
            newRandomInputNodeId = inputNeurons.get(r.nextInt(inputNeurons.size())).getId();
        }

        Integer newConnectionId = idMap.getOrCreateNewConnectionId(newRandomInputNodeId, hidden1.getId());
        assertEquals(newConnectionId, Integer.valueOf(inputNeurons.size() * outputNeurons.size() + 3));

    }

}
