package thesis.statistics;

import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.genetic.Configuration;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Alex on 8/15/2016.
 */
public class ConfigurationStatisticsService {

    private final Collection<Configuration> configurationStatistics;

    public ConfigurationStatisticsService() {
        configurationStatistics = Collections.synchronizedList(new ArrayList<>());
    }

    public void loadFromConfigurationIncubatorFolder(Path folder) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    loadFromConfigurationIncubatorFolder(entry);
                } else {
                    configurationStatistics.add(ConfigurationFactory.getBaseConfiguration(entry.toString()));
                }
            }
        }
    }

    public Double getAveragePopulationSize() {
        return configurationStatistics.stream().mapToInt(Configuration::getPopulationSize).average().orElse(0);
    }

    public Double getAverageOffspringFraction() {
        return configurationStatistics.stream().mapToDouble(Configuration::getOffspringFraction).average().orElse(0);
    }

    public Double getAverageAddSynapseMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getAddSynapseMutationProbability).average().orElse(0);
    }

    public Double getAverageAddNodeMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getAddNodeMutationProbability).average().orElse(0);
    }

    public Double getAverageDisableSynapseMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getDisableSynapseMutationProbability).average().orElse(0);
    }

    public Double getAverageEnableSynapseMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getEnableSynapseMutationProbability).average().orElse(0);
    }

    public Double getAverageModifyWeightMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getModifyWeightMutationProbability).average().orElse(0);
    }

    public Double getAverageModifyWeightPerturbMutationProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getUniformPerturbWeightProbability).average().orElse(0);
    }

    public Double getAverageModifyWeightStep() {
        return configurationStatistics.stream().mapToDouble(Configuration::getPerturbWeightStep).average().orElse(0);
    }

    public Double getAverageCrossoverProb() {
        return configurationStatistics.stream().mapToDouble(Configuration::getCrossoverProbability).average().orElse(0);
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append(String.format("Average Population size = %f\n", getAveragePopulationSize()));
        stats.append(String.format("Average Offspring fraction = %f\n", getAverageOffspringFraction()));
        stats.append(String.format("Average Add Synapse Mutation prob = %f\n", getAverageAddSynapseMutationProb()));
        stats.append(String.format("Average Add Neuron Mutation prob = %f\n", getAverageAddNodeMutationProb()));
        stats.append(String.format("Average Disable Synapse Mutation prob = %f\n", getAverageDisableSynapseMutationProb()));
        stats.append(String.format("Average Enable Synapse Mutation prob = %f\n", getAverageEnableSynapseMutationProb()));
        stats.append(String.format("Average Modify Weight Mutation prob = %f\n", getAverageModifyWeightMutationProb()));
        stats.append(String.format("Average Modify Weight Perturb prob = %f\n", getAverageModifyWeightPerturbMutationProb()));
        stats.append(String.format("Average Modify Weight Perturb-step percent = %f\n", getAverageModifyWeightStep()));
        stats.append(String.format("Average Crossover prob = %f\n", getAverageCrossoverProb()));

        return stats.toString();
    }
}
