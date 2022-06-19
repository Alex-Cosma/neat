package thesis.genetic.modifiers.crossover;

import org.jenetics.*;
import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.extra.MutationResult;

import java.util.Random;

/**
 * Created by Alex on 16/07/2016.
 */
public abstract class AbstractNeatCrossover extends Recombinator<NeuralGene, Double> {

    protected AbstractNeatCrossover(final double probability) {
        super(probability, 2);
    }

    protected final int recombine(
            final Population<NeuralGene, Double> population,
            final int[] individuals,
            final long generation
    ) {
        final Random random = RandomRegistry.getRandom();

        final Phenotype<NeuralGene, Double> pt1 = population.get(individuals[0]);
        final Phenotype<NeuralGene, Double> pt2 = population.get(individuals[1]);
        final Genotype<NeuralGene> gt1 = pt1.getGenotype();
        final Genotype<NeuralGene> gt2 = pt2.getGenotype();

        final Genotype<NeuralGene> dominantGenotype;
        final Genotype<NeuralGene> recessiveGenotype;

        boolean equalFitness = pt1.getFitness().equals(pt2.getFitness());

        if (pt1.getFitness() > pt2.getFitness()) {
            dominantGenotype = gt1;
            recessiveGenotype = gt2;
        } else {
            dominantGenotype = gt2;
            recessiveGenotype = gt1;
        }

        //Choosing the Chromosome for crossover.
        final int chIndex = random.nextInt(dominantGenotype.length());

        final MSeq<Chromosome<NeuralGene>> dominantChromosome = dominantGenotype.toSeq().copy();
        final MSeq<Chromosome<NeuralGene>> recessiveChromosome = recessiveGenotype.toSeq().copy();
        final MSeq<NeuralGene> dominantGenes = dominantChromosome.get(chIndex).toSeq().copy();
        final MSeq<NeuralGene> recessiveGenes = recessiveChromosome.get(chIndex).toSeq().copy();

        MutationResult crossover = crossover(dominantGenes, recessiveGenes, equalFitness);

        dominantChromosome.set(chIndex, dominantChromosome.get(chIndex).newInstance(dominantGenes.toISeq()));
        recessiveChromosome.set(chIndex, recessiveChromosome.get(chIndex).newInstance(crossover.getGenes().toISeq()));

        //Creating two new Phenotypes and exchanging it with the old.
        //TODO: right now, the weaker parent gets replaced with the offspring. Should it be done differently?
        //TODO: maybe be more creative in this. -> INCEST
        // TODO: incest proved to be bad -> see paper
        population.set(
                individuals[0],
                pt1.newInstance(Genotype.of(dominantChromosome.toISeq()), generation)
        );
        population.set(
                individuals[1],
                pt2.newInstance(Genotype.of(recessiveChromosome.toISeq()), generation)
        );
        return getOrder();
    }

    protected abstract MutationResult crossover(final MSeq<NeuralGene> dominantGenes, final MSeq<NeuralGene> recessiveGenes, boolean equalFitness);
}
