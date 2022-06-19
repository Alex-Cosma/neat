package thesis.configuration.factory;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.genetic.MetaConfiguration;
import thesis.configuration.preservation.PreservationConfiguration;

import static thesis.configuration.genetic.ConfigurationConstants.*;

/**
 * Created by Alex on 02/08/2016.
 */
public class ConfigurationFactory {

    public static Configuration getBaseConfiguration() {
        return new Configuration(getCompositeConfiguration(BASE_CONFIG_PATH));
    }

    public static Configuration getBaseConfiguration(String path) {
        return new Configuration(getCompositeConfiguration(path));
    }

    public static MetaConfiguration getMetaConfiguration() {
        return new MetaConfiguration(getCompositeConfiguration(META_CONFIG_PATH));
    }

    public static PreservationConfiguration getPreservationConfiguration() {
        return new PreservationConfiguration(getCompositeConfiguration(PRESERVATION_CONFIG_PATH));
    }

    public static CompositeConfiguration getCompositeConfiguration(String configPath) {
        CompositeConfiguration config = new CompositeConfiguration();

        try {
            config.addConfiguration(new PropertiesConfiguration(configPath));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

}