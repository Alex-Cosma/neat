package thesis.main;

import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.genetic.MetaConfiguration;
import thesis.configuration.preservation.PreservationConfiguration;
import thesis.game.factory.GamesFactory;
import thesis.game.model.Game;
import thesis.genetic.evolvers.Evolver;
import thesis.genetic.evolvers.MetaEvolver;
import thesis.preservation.PreservationService;
import thesis.statistics.NeuralEvolutionStatistics;
import thesis.statistics.NeuralEvolutionStatisticsService;
import thesis.statistics.PhenotypeStatisticsService;

/**
 * Created by Alex on 06/08/2016.
 */
public class MetaMain {
    public static void main(String[] args) {
        PhenotypeStatisticsService phenotypeStatisticsService = new PhenotypeStatisticsService();
        NeuralEvolutionStatistics statistics = new NeuralEvolutionStatistics();

        for (int i = 0; i < 10; i++) {
            PreservationConfiguration preservationConfiguration = ConfigurationFactory.getPreservationConfiguration();
            MetaConfiguration metaConfiguration = ConfigurationFactory.getMetaConfiguration();

            Game game = GamesFactory.getIrisClassificationGame();
            PreservationService preservationService = new PreservationService(game, preservationConfiguration);

            Evolver evolver = new Evolver(game, statistics, preservationService, phenotypeStatisticsService);
            MetaEvolver metaEvolver = new MetaEvolver(statistics, metaConfiguration, evolver);
            Configuration configuration = metaEvolver.evolve();

            preservationService.preserve(configuration);
        }
        System.out.println(phenotypeStatisticsService.getStatistics());

        System.exit(0);
    }
}
