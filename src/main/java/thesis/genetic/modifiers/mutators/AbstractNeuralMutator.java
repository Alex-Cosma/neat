package thesis.genetic.modifiers.mutators;

import org.jenetics.*;
import org.jenetics.internal.util.IntRef;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.extra.MutationResult;

import static java.lang.Math.pow;
import static org.jenetics.internal.math.random.indexes;

/**
 * Created by Alex on 14/07/2016.
 */
public abstract class AbstractNeuralMutator implements Alterer<NeuralGene, Double> {

    private final double _probability;

    public AbstractNeuralMutator(double probability) {
        _probability = probability;
    }

    @Override
    public int alter(Population<NeuralGene, Double> population, long generation) {
        assert population != null : "Not null is guaranteed from base class.";

        final double p = pow(_probability, 1.0 / 3.0);
        final IntRef alterations = new IntRef(0);

        indexes(RandomRegistry.getRandom(), population.size(), p).forEach(i -> {
            final Phenotype<NeuralGene, Double> pt = population.get(i);

            final Genotype<NeuralGene> gt = pt.getGenotype();
            final Genotype<NeuralGene> mgt = mutate(gt, p, alterations);

            final Phenotype<NeuralGene, Double> mpt = pt.newInstance(mgt, generation);
            population.set(i, mpt);
        });

        return alterations.value;
    }

    private Genotype<NeuralGene> mutate(
            final Genotype<NeuralGene> genotype,
            final double p,
            final IntRef alterations
    ) {
        final MSeq<Chromosome<NeuralGene>> chromosomes = genotype.toSeq().copy();

        alterations.value +=
                indexes(RandomRegistry.getRandom(), genotype.length(), p)
                        .map(i -> mutate(chromosomes, i, p))
                        .sum();

        return Genotype.of(chromosomes.toISeq());
    }

    private int mutate(final MSeq<Chromosome<NeuralGene>> c, final int i, final double p) {
        final Chromosome<NeuralGene> chromosome = c.get(i);
        final MSeq<NeuralGene> genes = chromosome.toSeq().copy();

        final MutationResult mResult = mutate(genes, p);
        if (mResult.getMutations() > 0) {
            c.set(i, chromosome.newInstance(mResult.getGenes().toISeq()));
        }
        return mResult.getMutations();
    }

    protected abstract MutationResult mutate(MSeq<NeuralGene> genes, double p);
}
