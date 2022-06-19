package thesis.main;

import org.jenetics.Phenotype;
import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.preservation.PreservationConfiguration;
import thesis.game.factory.GamesFactory;
import thesis.game.model.Game;
import thesis.genetic.evolvers.Evolver;
import thesis.genetic.models.NeuralGene;
import thesis.preservation.PreservationService;
import thesis.statistics.NeuralEvolutionStatistics;
import thesis.statistics.NeuralEvolutionStatisticsService;
import thesis.statistics.PhenotypeStatisticsService;

/**
 * Created by Alex on 12/07/2016.
 */
public class Main {

    public static void main(String[] args) {
        PhenotypeStatisticsService phenotypeStatisticsService = new PhenotypeStatisticsService();
        NeuralEvolutionStatisticsService neuralEvolutionStatisticsService = new NeuralEvolutionStatisticsService();

        for (int i = 0; i < 50; i++) {
            Configuration configuration = ConfigurationFactory.getBaseConfiguration();
            PreservationConfiguration preservationConfiguration = ConfigurationFactory.getPreservationConfiguration();

            Game game = GamesFactory.getTTTGame();
            PreservationService preservationService = new PreservationService(game, preservationConfiguration);

            NeuralEvolutionStatistics statistics = new NeuralEvolutionStatistics();

            Evolver evolver = new Evolver(game, statistics, preservationService, phenotypeStatisticsService);
            Phenotype<NeuralGene, Double> bestIndividual = evolver.evolve(configuration);

            System.out.println(statistics.getBaseJeneticsStatistics());
            System.out.println(bestIndividual.getFitness());
            System.out.println(bestIndividual.getGeneration());
            System.out.println(bestIndividual.getGenotype().getChromosome().toSeq().size());

            neuralEvolutionStatisticsService.addNeuralEvolutionStatistics(statistics);
        }

        System.out.println(phenotypeStatisticsService.getStatistics());

//        neuralEvolutionStatisticsService.printSeparateMaps();
        neuralEvolutionStatisticsService.printAverageFitnessPerGenerationMapCsv();

        System.exit(0);
    }
}
