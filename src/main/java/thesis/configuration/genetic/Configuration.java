package thesis.configuration.genetic;


import org.apache.commons.configuration.CompositeConfiguration;
import thesis.configuration.factory.ConfigurationFactory;

import java.io.Serializable;

import static thesis.configuration.genetic.ConfigurationConstants.*;

/**
 * Created by Alex on 02/08/2016.
 */
public class Configuration implements Serializable {

    private Integer populationSize;
    private Double offspringFraction;
    private Integer maximumPhenotypeAge;

    private Integer generationLimit;

    private Double defaultSpeciationThreshold;
    private Integer maxStaleness;

    private Double compatibilityExcessCoefficient;
    private Double compatibilityDisjointCoefficient;
    private Double compatibilityCommonWeightCoefficient;

    private Double addSynapseMutationProbability;
    private Integer maxTriesAddSynapseMutation;
    private Double addNodeMutationProbability;
    private Double disableSynapseMutationProbability;
    private Double enableSynapseMutationProbability;
    private Double crossoverProbability;

    private Double modifyWeightMutationProbability;
    private Double uniformPerturbWeightProbability;
    private Double perturbWeightStep;

    private Integer evolutionThreadPool = 1;

    public Configuration() {
        // defaults
        this(ConfigurationFactory.getCompositeConfiguration(BASE_CONFIG_PATH));
    }

    public Configuration(CompositeConfiguration config) {
        if (config != null) {
            this.populationSize = config.getInt(POPULATION_SIZE);
            this.offspringFraction = config.getDouble(OFFSPRING_FRACTION);
            this.maximumPhenotypeAge = config.getInt(MAXIMUM_PHENOTYPE_AGE);
            this.generationLimit = config.getInt(GENERATION_LIMIT);

            this.defaultSpeciationThreshold = config.getDouble(DEFAULT_SPECIATION_THRESHOLD);
            this.maxStaleness = config.getInt(MAX_STALENESS);

            this.compatibilityExcessCoefficient = config.getDouble(COMPATIBILITY_EXCESS_COEFFICIENT);
            this.compatibilityDisjointCoefficient = config.getDouble(COMPATIBILITY_DISJOINT_COEFFICIENT);
            this.compatibilityCommonWeightCoefficient = config.getDouble(COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT);

            this.addSynapseMutationProbability = config.getDouble(ADD_SYNAPSE_MUTATION_PROBABILITY);
            this.maxTriesAddSynapseMutation = config.getInt(MAX_TRIES_ADD_SYNAPSE_MUTATION);
            this.addNodeMutationProbability = config.getDouble(ADD_NODE_MUTATION_PROBABILITY);
            this.disableSynapseMutationProbability = config.getDouble(DISABLE_SYNAPSE_MUTATION_PROBABILITY);
            this.enableSynapseMutationProbability = config.getDouble(ENABLE_SYNAPSE_MUTATION_PROBABILITY);
            this.crossoverProbability = config.getDouble(CROSSOVER_PROBABILITY);
            this.modifyWeightMutationProbability = config.getDouble(MODIFY_WEIGHT_MUTATION_PROBABILITY);
            this.uniformPerturbWeightProbability = config.getDouble(UNIFORM_PERTURB_WEIGHT_PROBABILITY);
            this.perturbWeightStep = config.getDouble(PERTURB_WEIGHT_STEP);
            this.evolutionThreadPool = config.getInt(EVOLUTION_THREAD_POOL);
        }
    }


    public Integer getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(final Integer populationSize) {
        this.populationSize = populationSize;
    }

    public Double getOffspringFraction() {
        return offspringFraction;
    }

    public void setOffspringFraction(final Double offspringFraction) {
        this.offspringFraction = offspringFraction;
    }

    public Integer getMaximumPhenotypeAge() {
        return maximumPhenotypeAge;
    }

    public void setMaximumPhenotypeAge(Integer maximumPhenotypeAge) {
        this.maximumPhenotypeAge = maximumPhenotypeAge;
    }

    public Integer getGenerationLimit() {
        return generationLimit;
    }

    public void setGenerationLimit(Integer generationLimit) {
        this.generationLimit = generationLimit;
    }

    public Double getDefaultSpeciationThreshold() {
        return defaultSpeciationThreshold;
    }

    public void setDefaultSpeciationThreshold(Double defaultSpeciationThreshold) {
        this.defaultSpeciationThreshold = defaultSpeciationThreshold;
    }

    public Integer getMaxStaleness() {
        return maxStaleness;
    }

    public void setMaxStaleness(Integer maxStaleness) {
        this.maxStaleness = maxStaleness;
    }

    public Double getCompatibilityExcessCoefficient() {
        return compatibilityExcessCoefficient;
    }

    public void setCompatibilityExcessCoefficient(Double compatibilityExcessCoefficient) {
        this.compatibilityExcessCoefficient = compatibilityExcessCoefficient;
    }

    public Double getCompatibilityDisjointCoefficient() {
        return compatibilityDisjointCoefficient;
    }

    public void setCompatibilityDisjointCoefficient(Double compatibilityDisjointCoefficient) {
        this.compatibilityDisjointCoefficient = compatibilityDisjointCoefficient;
    }

    public Double getCompatibilityCommonWeightCoefficient() {
        return compatibilityCommonWeightCoefficient;
    }

    public void setCompatibilityCommonWeightCoefficient(Double compatibilityCommonWeightCoefficient) {
        this.compatibilityCommonWeightCoefficient = compatibilityCommonWeightCoefficient;
    }

    public Double getAddSynapseMutationProbability() {
        return addSynapseMutationProbability;
    }

    public void setAddSynapseMutationProbability(Double addSynapseMutationProbability) {
        this.addSynapseMutationProbability = addSynapseMutationProbability;
    }

    public Integer getMaxTriesAddSynapseMutation() {
        return maxTriesAddSynapseMutation;
    }

    public void setMaxTriesAddSynapseMutation(Integer maxTriesAddSynapseMutation) {
        this.maxTriesAddSynapseMutation = maxTriesAddSynapseMutation;
    }

    public Double getAddNodeMutationProbability() {
        return addNodeMutationProbability;
    }

    public void setAddNodeMutationProbability(Double addNodeMutationProbability) {
        this.addNodeMutationProbability = addNodeMutationProbability;
    }

    public Double getDisableSynapseMutationProbability() {
        return disableSynapseMutationProbability;
    }

    public void setDisableSynapseMutationProbability(Double disableSynapseMutationProbability) {
        this.disableSynapseMutationProbability = disableSynapseMutationProbability;
    }

    public Double getEnableSynapseMutationProbability() {
        return enableSynapseMutationProbability;
    }

    public void setEnableSynapseMutationProbability(Double enableSynapseMutationProbability) {
        this.enableSynapseMutationProbability = enableSynapseMutationProbability;
    }

    public Double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(Double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public Double getModifyWeightMutationProbability() {
        return modifyWeightMutationProbability;
    }

    public void setModifyWeightMutationProbability(Double modifyWeightMutationProbability) {
        this.modifyWeightMutationProbability = modifyWeightMutationProbability;
    }

    public Double getUniformPerturbWeightProbability() {
        return uniformPerturbWeightProbability;
    }

    public void setUniformPerturbWeightProbability(Double uniformPerturbWeightProbability) {
        this.uniformPerturbWeightProbability = uniformPerturbWeightProbability;
    }

    public Double getPerturbWeightStep() {
        return perturbWeightStep;
    }

    public void setPerturbWeightStep(Double perturbWeightStep) {
        this.perturbWeightStep = perturbWeightStep;
    }

    public Integer getEvolutionThreadPool() {
        return evolutionThreadPool;
    }

    public void setEvolutionThreadPool(Integer evolutionThreadPool) {
        this.evolutionThreadPool = evolutionThreadPool;
    }

    @Override
    public String toString() {
        StringBuilder c = new StringBuilder();

        c.append(String.format("%s = %d\n", POPULATION_SIZE, this.populationSize));
        c.append(String.format("%s = %f\n", OFFSPRING_FRACTION, this.offspringFraction));
        c.append(String.format("%s = %d\n", MAXIMUM_PHENOTYPE_AGE, this.maximumPhenotypeAge));
        c.append(String.format("%s = %d\n", GENERATION_LIMIT, this.generationLimit));
        c.append("\n");
        c.append(String.format("%s = %f\n", DEFAULT_SPECIATION_THRESHOLD, this.defaultSpeciationThreshold));
        c.append(String.format("%s = %d\n", MAX_STALENESS, this.maxStaleness));
        c.append("\n");
        c.append(String.format("%s = %f\n", COMPATIBILITY_EXCESS_COEFFICIENT, this.compatibilityExcessCoefficient));
        c.append(String.format("%s = %f\n", COMPATIBILITY_DISJOINT_COEFFICIENT, this.compatibilityDisjointCoefficient));
        c.append(String.format("%s = %f\n", COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT, this.compatibilityCommonWeightCoefficient));
        c.append("\n");
        c.append(String.format("%s = %f\n", ADD_SYNAPSE_MUTATION_PROBABILITY, this.addSynapseMutationProbability));
        c.append(String.format("%s = %d\n", MAX_TRIES_ADD_SYNAPSE_MUTATION, this.maxTriesAddSynapseMutation));
        c.append(String.format("%s = %f\n", ADD_NODE_MUTATION_PROBABILITY, this.addNodeMutationProbability));
        c.append(String.format("%s = %f\n", DISABLE_SYNAPSE_MUTATION_PROBABILITY, this.disableSynapseMutationProbability));
        c.append(String.format("%s = %f\n", ENABLE_SYNAPSE_MUTATION_PROBABILITY, this.enableSynapseMutationProbability));
        c.append(String.format("%s = %f\n", MODIFY_WEIGHT_MUTATION_PROBABILITY, this.modifyWeightMutationProbability));
        c.append(String.format("%s = %f\n", UNIFORM_PERTURB_WEIGHT_PROBABILITY, this.uniformPerturbWeightProbability));
        c.append(String.format("%s = %f\n", PERTURB_WEIGHT_STEP, this.perturbWeightStep));
        c.append("\n");
        c.append(String.format("%s = %f\n", CROSSOVER_PROBABILITY, this.crossoverProbability));
        c.append("\n");
        c.append(String.format("%s = %d\n", EVOLUTION_THREAD_POOL, this.evolutionThreadPool));

        return c.toString();
    }
}
