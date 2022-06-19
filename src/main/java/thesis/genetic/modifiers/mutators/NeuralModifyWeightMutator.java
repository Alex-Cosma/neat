package thesis.genetic.modifiers.mutators;

import org.jenetics.Mutator;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;

import java.util.Random;
import java.util.stream.IntStream;

import static org.jenetics.internal.math.random.indexes;
import static thesis.genetic.processing.Randomizer.randomWeight;

/**
 * Created by Alex on 25/07/2016.
 */
public class NeuralModifyWeightMutator extends Mutator<NeuralGene, Double> {

    private final double uniformPerturbProbability;
    private final double perturbStep;

    public NeuralModifyWeightMutator(double probability, double uniformPerturbProbability, double perturbStep) {
        super(probability);
        this.uniformPerturbProbability = uniformPerturbProbability;
        this.perturbStep = perturbStep;
    }

    @Override
    protected int mutate(MSeq<NeuralGene> genes, double p) {
        final Random random = RandomRegistry.getRandom();

        int[] indexesOfSynapses = IntStream.range(0, genes.length()).toArray();

        return (int) indexes(random, indexesOfSynapses.length, p)
                .peek(i -> genes.set(indexesOfSynapses[i], mutate(genes.get(indexesOfSynapses[i]), random).newInstance()))
                .count();
    }

    private NeuralGene mutate(final NeuralGene gene, Random random) {
        if (random.nextDouble() < uniformPerturbProbability) {
            gene.getAllele().setWeight(gene.getAllele().getWeight() + random.nextDouble() * perturbStep - perturbStep);
        } else {
            gene.getAllele().setWeight(randomWeight());
        }
        return gene;
    }
}