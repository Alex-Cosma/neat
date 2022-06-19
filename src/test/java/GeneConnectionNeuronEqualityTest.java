import org.junit.Test;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;

import java.util.ArrayList;
import java.util.Collections;

import static thesis.neural.models.NeuronType.INPUT;
import static thesis.neural.models.NeuronType.OUTPUT;

/**
 * Created by Alex on 17/07/2016.
 */
public class GeneConnectionNeuronEqualityTest {

    @Test
    public void testGeneEquality() {
        NetworkIdMap networkIdMap = new NetworkIdMap(0, 0);
        NeuralGene ng1 = NeuralGene.of(Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0), networkIdMap);
        NeuralGene ng2 = NeuralGene.of(Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0), networkIdMap);
        NeuralGene ng3 = NeuralGene.of(Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0), networkIdMap);

        ArrayList<NeuralGene> neuralGenes = new ArrayList<>();
        neuralGenes.add(ng1);
        neuralGenes.add(ng2);
        neuralGenes.removeAll(Collections.singletonList(ng3));

        assert ng1.equals(ng2);
        assert neuralGenes.isEmpty();
    }


    @Test
    public void testConnectionEquality() {
        Synapse c1 = Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0);
        Synapse c2 = Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0);
        Synapse c3 = Synapse.of(1, Neuron.of(INPUT, 0, 0), Neuron.of(OUTPUT, 1, Integer.MAX_VALUE), 0d, true, 0);

        ArrayList<Synapse> conList = new ArrayList<>();
        conList.add(c1);
        conList.add(c2);
        conList.removeAll(Collections.singletonList(c3));

        assert c1.equals(c2);
        assert conList.isEmpty();
    }

}
