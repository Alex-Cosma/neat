package thesis.genetic.metavolution.mappers;

import java.util.HashMap;
import java.util.Map;

import static thesis.configuration.genetic.ConfigurationConstants.*;

/**
 * Created by Alex on 03/08/2016.
 */
public class MetaConfigurationChromosomeFieldsMap {

    public static final Map<String, Integer> mutationChromosomeConfigurationMap = new HashMap<>();
    public static final Map<String, Integer> offspringChromosomeConfigurationMap = new HashMap<>();
    public static final Map<String, Integer> populationChromosomeConfigurationMap = new HashMap<>();


    static {
        mutationChromosomeConfigurationMap.put(ADD_SYNAPSE_MUTATION_PROBABILITY, 0);
        mutationChromosomeConfigurationMap.put(ADD_NODE_MUTATION_PROBABILITY, 1);
        mutationChromosomeConfigurationMap.put(DISABLE_SYNAPSE_MUTATION_PROBABILITY, 2);
        mutationChromosomeConfigurationMap.put(ENABLE_SYNAPSE_MUTATION_PROBABILITY, 3);
        mutationChromosomeConfigurationMap.put(MODIFY_WEIGHT_MUTATION_PROBABILITY, 4);
        mutationChromosomeConfigurationMap.put(UNIFORM_PERTURB_WEIGHT_PROBABILITY, 5);
        mutationChromosomeConfigurationMap.put(PERTURB_WEIGHT_STEP, 6);
        mutationChromosomeConfigurationMap.put(CROSSOVER_PROBABILITY, 7);

        offspringChromosomeConfigurationMap.put(OFFSPRING_FRACTION, 0);

        populationChromosomeConfigurationMap.put(POPULATION_SIZE, 0);


    }

}
