package thesis.genetic.modifiers.mutators;

import org.jenetics.Mutator;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;

import java.util.Random;
import java.util.stream.IntStream;

import static org.jenetics.internal.math.random.indexes;

/**
 * Created by Alex on 15/07/2016.
 */
public class NeuralEnableSynapseMutator extends Mutator<NeuralGene, Double> {

    public NeuralEnableSynapseMutator(double probability) {
        super(probability);
    }

    @Override
    protected int mutate(MSeq<NeuralGene> genes, double p) {
        final Random random = RandomRegistry.getRandom();

        int[] indexesOfDisabledSynapses = IntStream.range(0, genes.length())
                .filter(i -> !genes.get(i).getAllele().isEnabled()).toArray();

        return (int) indexes(random, indexesOfDisabledSynapses.length, p)
                .peek(i -> genes.set(indexesOfDisabledSynapses[i], mutate(genes.get(indexesOfDisabledSynapses[i]).newInstance())))
                .count();
    }

    private NeuralGene mutate(final NeuralGene gene) {
        gene.getAllele().setEnabled(true);
        return gene;
    }


}
