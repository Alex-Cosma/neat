package thesis.configuration.genetic;

/**
 * Created by Alex on 02/08/2016.
 */
public class ConfigurationConstants {

    public static final String BASE_CONFIG_PATH = "configurations/baseConfig.prop";
    public static final String META_CONFIG_PATH = "configurations/metaConfig.prop";
    public static final String PRESERVATION_CONFIG_PATH = "configurations/preservationConfig.prop";

    public static final String POPULATION_SIZE = "population.size";
    public static final String OFFSPRING_FRACTION = "offspring.fraction";
    public static final String MAXIMUM_PHENOTYPE_AGE = "phenotype.maximum.age";
    public static final String GENERATION_LIMIT = "generation.limit";

    public static final String DEFAULT_SPECIATION_THRESHOLD = "speciation.threshold";
    public static final String MAX_STALENESS = "staleness.max";

    public static final String COMPATIBILITY_EXCESS_COEFFICIENT = "compatibility.excess";
    public static final String COMPATIBILITY_DISJOINT_COEFFICIENT = "compatibility.disjoint";
    public static final String COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT = "compatibility.weight";

    public static final String ADD_SYNAPSE_MUTATION_PROBABILITY = "mutation.addSynapse";
    public static final String MAX_TRIES_ADD_SYNAPSE_MUTATION = "mutation.addSynapse.maxTries";
    public static final String ADD_NODE_MUTATION_PROBABILITY = "mutation.addNode";
    public static final String DISABLE_SYNAPSE_MUTATION_PROBABILITY = "mutation.disableSynapse";
    public static final String ENABLE_SYNAPSE_MUTATION_PROBABILITY = "mutation.enableSynapse";
    public static final String CROSSOVER_PROBABILITY = "crossover";

    public static final String MODIFY_WEIGHT_MUTATION_PROBABILITY = "mutation.modifyWeight";
    public static final String UNIFORM_PERTURB_WEIGHT_PROBABILITY = "mutation.modifyWeight.perturb";
    public static final String PERTURB_WEIGHT_STEP = "mutation.modifyWeight.perturb.step";

    public static final String EVOLUTION_THREAD_POOL = "evolution.thread.pool";

    public static final String META_MAXIMUM_GENERATIONS = "meta.maxGenerations";
    public static final String META_INITIAL_POPULATION = "meta.population.size";

    public static final String META_MUTATION_PROBABILITY_BOUND_LOWER = "meta.mutation.probability.bound.lower";
    public static final String META_MUTATION_PROBABILITY_BOUND_UPPER = "meta.mutation.probability.bound.upper";
    public static final String META_OFFSPRING_FRACTION_BOUND_LOWER = "meta.offspring.fraction.bound.lower";
    public static final String META_OFFSPRING_FRACTION_BOUND_UPPER = "meta.offspring.fraction.bound.upper";
    public static final String META_POPULATION_BOUND_LOWER = "meta.population.bound.lower";
    public static final String META_POPULATION_BOUND_UPPER = "meta.population.bound.upper";
    public static final String META_EVOLUTION_THREAD_POOL = "meta.evolution.thread.pool";

    public static final String PRESERVE_BEST_PHENOTYPE = "preserve.bestPhenotype";
    public static final String PRESERVE_CONFIGURATION = "preserve.configuration";
    public static final String PRESERVATION_BASE_DIRECTORY = "preservation.baseDirectory";
    public static final String PRESERVATION_INDIVIDUALS_DIRECTORY = "preservation.individualsDirectory";
    public static final String PRESERVATION_CONFIGURATIONS_DIRECTORY = "preservation.configurationsDirectory";
    public static final String PRESERVATION_GAME_DIRECTORY_DAILY_FORMAT = "preservation.gameDirectoryDailyFormat";
}
