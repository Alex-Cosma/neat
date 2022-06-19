package thesis.main;

import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.preservation.PreservationConfiguration;
import thesis.statistics.ConfigurationStatisticsService;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Alex on 8/15/2016.
 */
public class ConfigStatsMain {
    public static void main(String[] args) {
        ConfigurationStatisticsService configurationStatisticsService = new ConfigurationStatisticsService();
        PreservationConfiguration preservationConfiguration = ConfigurationFactory.getPreservationConfiguration();


        try {
            configurationStatisticsService.loadFromConfigurationIncubatorFolder(Paths.get(preservationConfiguration.getConfigurationsDirectory() + "/Iris_Classification/2016-09-05"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(configurationStatisticsService.getStatistics());
    }
}
