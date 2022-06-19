package thesis.configuration.genetic;

import org.apache.commons.configuration.CompositeConfiguration;
import thesis.configuration.factory.ConfigurationFactory;

import static thesis.configuration.genetic.ConfigurationConstants.*;

/**
 * Created by Alex on 8/7/2016.
 */
public class MetaConfiguration {

    private final Integer metaMaximumGenerations;
    private final Integer metaPopulationSize;

    private final Double metaMutationProbabilityBoundLower;
    private final Double metaMutationProbabilityBoundUpper;
    private final Double metaOffspringFractionBoundLower;
    private final Double metaOffspringFractionBoundUpper;
    private final Double metaPopulationBoundLower;
    private final Double metaPopulationBoundUpper;

    private final Integer metaEvolutionThreadPool;

    public MetaConfiguration() {
        // defaults
        this(ConfigurationFactory.getCompositeConfiguration(META_CONFIG_PATH));
    }

    public MetaConfiguration(CompositeConfiguration config) {
        if (config != null) {
            this.metaMaximumGenerations = config.getInt(META_MAXIMUM_GENERATIONS);
            this.metaPopulationSize = config.getInt(META_INITIAL_POPULATION);

            this.metaMutationProbabilityBoundLower = config.getDouble(META_MUTATION_PROBABILITY_BOUND_LOWER);
            this.metaMutationProbabilityBoundUpper = config.getDouble(META_MUTATION_PROBABILITY_BOUND_UPPER);
            this.metaOffspringFractionBoundLower = config.getDouble(META_OFFSPRING_FRACTION_BOUND_LOWER);
            this.metaOffspringFractionBoundUpper = config.getDouble(META_OFFSPRING_FRACTION_BOUND_UPPER);
            this.metaPopulationBoundLower = config.getDouble(META_POPULATION_BOUND_LOWER);
            this.metaPopulationBoundUpper = config.getDouble(META_POPULATION_BOUND_UPPER);
            this.metaEvolutionThreadPool = config.getInt(META_EVOLUTION_THREAD_POOL);
        } else {
            throw new IllegalStateException("Could not load meta configuration!");
        }
    }

    public long getMetaMaximumGenerations() {
        return metaMaximumGenerations;
    }

    public Integer getMetaPopulationSize() {
        return metaPopulationSize;
    }

    public Double getMetaMutationProbabilityBoundLower() {
        return metaMutationProbabilityBoundLower;
    }

    public Double getMetaMutationProbabilityBoundUpper() {
        return metaMutationProbabilityBoundUpper;
    }

    public Double getMetaOffspringFractionBoundLower() {
        return metaOffspringFractionBoundLower;
    }

    public Double getMetaOffspringFractionBoundUpper() {
        return metaOffspringFractionBoundUpper;
    }

    public Double getMetaPopulationBoundLower() {
        return metaPopulationBoundLower;
    }

    public Double getMetaPopulationBoundUpper() {
        return metaPopulationBoundUpper;
    }

    public Integer getMetaEvolutionThreadPool() {
        return metaEvolutionThreadPool;
    }
}
