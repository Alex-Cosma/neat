import org.jenetics.util.Seq;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;

import static thesis.neural.models.NeuronType.INPUT;
import static thesis.neural.models.NeuronType.OUTPUT;

/**
 * Created by Alex on 17/07/2016.
 */
public abstract class AbstractNeatTest {

    protected Seq<NeuralGene> createRecessiveGeneSequence() {
        Seq<NeuralGene> recGenes = Seq.empty();
        NetworkIdMap networkIdMap = new NetworkIdMap(0, 0);

        recGenes = recGenes.append(NeuralGene.of(Synapse.of(1, Neuron.of(INPUT, 1, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 1), networkIdMap));
        recGenes = recGenes.append(NeuralGene.of(Synapse.of(2, Neuron.of(INPUT, 2, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, false, 2), networkIdMap));
        recGenes = recGenes.append(NeuralGene.of(Synapse.of(3, Neuron.of(INPUT, 3, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 3), networkIdMap));
        recGenes = recGenes.append(NeuralGene.of(Synapse.of(4, Neuron.of(INPUT, 2, 0), Neuron.of(OUTPUT, 5, Integer.MAX_VALUE), 0d, true, 4), networkIdMap));
        recGenes = recGenes.append(NeuralGene.of(Synapse.of(5, Neuron.of(INPUT, 5, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 5), networkIdMap));
        recGenes = recGenes.append(NeuralGene.of(Synapse.of(6, Neuron.of(INPUT, 1, 0), Neuron.of(OUTPUT, 5, Integer.MAX_VALUE), 0d, true, 8), networkIdMap));

        return recGenes;
    }

    protected Seq<NeuralGene> createDominantGeneSequence() {
        Seq<NeuralGene> domGenes = Seq.empty();
        NetworkIdMap networkIdMap = new NetworkIdMap(0, 0);

        domGenes = domGenes.append(NeuralGene.of(Synapse.of(1, Neuron.of(INPUT, 1, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 1), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(2, Neuron.of(INPUT, 2, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, false, 2), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(3, Neuron.of(INPUT, 3, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 3), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(4, Neuron.of(INPUT, 2, 0), Neuron.of(OUTPUT, 5, Integer.MAX_VALUE), 0d, true, 4), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(5, Neuron.of(INPUT, 5, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, false, 5), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(7, Neuron.of(INPUT, 5, 0), Neuron.of(OUTPUT, 6, Integer.MAX_VALUE), 0d, true, 6), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(8, Neuron.of(INPUT, 6, 0), Neuron.of(OUTPUT, 4, Integer.MAX_VALUE), 0d, true, 7), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(9, Neuron.of(INPUT, 3, 0), Neuron.of(OUTPUT, 5, Integer.MAX_VALUE), 0d, true, 9), networkIdMap));
        domGenes = domGenes.append(NeuralGene.of(Synapse.of(10, Neuron.of(INPUT, 1, 0), Neuron.of(OUTPUT, 6, Integer.MAX_VALUE), 0d, true, 10), networkIdMap));

        return domGenes;
    }
}
