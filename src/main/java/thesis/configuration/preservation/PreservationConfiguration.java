package thesis.configuration.preservation;

import org.apache.commons.configuration.CompositeConfiguration;
import thesis.configuration.factory.ConfigurationFactory;

import static thesis.configuration.genetic.ConfigurationConstants.*;

/**
 * Created by Alex on 8/7/2016.
 */
public class PreservationConfiguration {

    private final Boolean preserveBestPhenotype;
    private final Boolean preserveConfiguration;
    private final String preservationBaseDirectory;
    private final String preservationIndividualsDirectory;
    private final String preservationConfigurationsDirectory;
    private final String preservationGameDirectoryDailyFormat;

    public PreservationConfiguration() {
        // defaults
        this(ConfigurationFactory.getCompositeConfiguration(PRESERVATION_CONFIG_PATH));
    }

    public PreservationConfiguration(CompositeConfiguration config) {
        if (config != null) {

            this.preserveBestPhenotype = config.getBoolean(PRESERVE_BEST_PHENOTYPE);
            this.preserveConfiguration = config.getBoolean(PRESERVE_CONFIGURATION);
            this.preservationBaseDirectory = config.getString(PRESERVATION_BASE_DIRECTORY);
            this.preservationIndividualsDirectory = config.getString(PRESERVATION_INDIVIDUALS_DIRECTORY);
            this.preservationConfigurationsDirectory = config.getString(PRESERVATION_CONFIGURATIONS_DIRECTORY);
            this.preservationGameDirectoryDailyFormat = config.getString(PRESERVATION_GAME_DIRECTORY_DAILY_FORMAT);
        } else {
            throw new IllegalStateException("Could not load preservation configuration!");
        }
    }

    public Boolean preserveBestPhenotype() {
        return this.preserveBestPhenotype;
    }

    public Boolean preserveConfiguration() {
        return this.preserveConfiguration;
    }

    public String getPreservationBaseDirectory() {
        return preservationBaseDirectory;
    }

    public String getIndividualsDirectory() {
        return preservationBaseDirectory + "/" + preservationIndividualsDirectory;
    }

    public String getConfigurationsDirectory() {
        return preservationBaseDirectory + "/" + preservationConfigurationsDirectory;
    }

    public String getPreservationGameDirectoryDailyFormat() {
        return preservationGameDirectoryDailyFormat;
    }
}
