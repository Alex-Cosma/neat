package thesis.genetic.metavolution.mappers;

import org.jenetics.Chromosome;
import org.jenetics.DoubleGene;
import org.jenetics.Genotype;
import thesis.configuration.genetic.Configuration;

import static thesis.configuration.genetic.ConfigurationConstants.*;
import static thesis.genetic.metavolution.mappers.MetaConfigurationChromosomeFieldsMap.*;

/**
 * Created by Alex on 03/08/2016.
 */
public class MetaGenotypeToConfigurationMapper {

    public Configuration mapToConfiguration(Genotype<DoubleGene> g) {

        Chromosome<DoubleGene> mutationsChromosome = g.getChromosome(0);
        Chromosome<DoubleGene> offspringFractionChromosome = g.getChromosome(1);
        Chromosome<DoubleGene> populationSizeChromosome = g.getChromosome(2);

        Configuration configuration = new Configuration();
        fillWithMutationProbabilities(configuration, mutationsChromosome);
        fillWithPopulationSize(configuration, populationSizeChromosome);
        fillWithOffspringFraction(configuration, offspringFractionChromosome);

        return configuration;
    }


    private void fillWithMutationProbabilities(Configuration configuration, Chromosome<DoubleGene> mutationsChromosome) {

        configuration.setAddSynapseMutationProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(ADD_SYNAPSE_MUTATION_PROBABILITY)).getAllele());
        configuration.setAddNodeMutationProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(ADD_NODE_MUTATION_PROBABILITY)).getAllele());
        configuration.setDisableSynapseMutationProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(DISABLE_SYNAPSE_MUTATION_PROBABILITY)).getAllele());
        configuration.setEnableSynapseMutationProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(ENABLE_SYNAPSE_MUTATION_PROBABILITY)).getAllele());
        configuration.setModifyWeightMutationProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(MODIFY_WEIGHT_MUTATION_PROBABILITY)).getAllele());
        configuration.setUniformPerturbWeightProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(UNIFORM_PERTURB_WEIGHT_PROBABILITY)).getAllele());
        configuration.setPerturbWeightStep(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(PERTURB_WEIGHT_STEP)).getAllele());
        configuration.setCrossoverProbability(mutationsChromosome.getGene(mutationChromosomeConfigurationMap.get(CROSSOVER_PROBABILITY)).getAllele());

    }

    private void fillWithPopulationSize(Configuration configuration, Chromosome<DoubleGene> populationSizeChromosome) {
        configuration.setPopulationSize(populationSizeChromosome.getGene(populationChromosomeConfigurationMap.get(POPULATION_SIZE)).getAllele().intValue());
    }

    private void fillWithOffspringFraction(Configuration configuration, Chromosome<DoubleGene> offspringFractionChromosome) {
        configuration.setOffspringFraction(offspringFractionChromosome.getGene(offspringChromosomeConfigurationMap.get(OFFSPRING_FRACTION)).getAllele());
    }

}
