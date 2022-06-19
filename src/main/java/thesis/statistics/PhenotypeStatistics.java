package thesis.statistics;

import org.jenetics.Phenotype;
import thesis.genetic.models.NeuralGene;

import static thesis.genetic.processing.ChromosomeExtractors.extractNeuronsFromGenes;

/**
 * Created by Alex on 8/13/2016.
 */
public class PhenotypeStatistics {

    private final Long generation;
    private final Double fitness;
    private final Integer nrNeurons;
    private final Integer nrSynapses;

    public PhenotypeStatistics(Phenotype<NeuralGene, Double> phenotype) {
        this.fitness = phenotype.getFitness();
        this.generation = phenotype.getGeneration();
        this.nrNeurons = extractNeuronsFromGenes(phenotype.getGenotype().getChromosome().toSeq()).size();
        this.nrSynapses = phenotype.getGenotype().getChromosome().toSeq().size();
    }

    public Double getFitness() {
        return fitness;
    }

    public Long getGeneration() {
        return generation;
    }

    public Integer getNrNeurons() {
        return nrNeurons;
    }

    public Integer getNrSynapses() {
        return nrSynapses;
    }

}
