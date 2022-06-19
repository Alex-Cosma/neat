package thesis.main;

import org.jenetics.Phenotype;
import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.preservation.PreservationConfiguration;
import thesis.game.evaluation.ClassificationEvaluator;
import thesis.game.factory.GamesFactory;
import thesis.game.games.classification.Iris.IrisClassificationGame;
import thesis.game.model.classifications.ClassificationGame;
import thesis.genetic.evolvers.Evolver;
import thesis.genetic.mappers.GenotypeToNetworkMapper;
import thesis.genetic.models.NeuralGene;
import thesis.preservation.PreservationService;
import thesis.statistics.NeuralEvolutionStatistics;
import thesis.statistics.NeuralEvolutionStatisticsService;
import thesis.statistics.PhenotypeStatisticsService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Alex on 8/8/2016.
 */
public class ClassificationMain {
    public static void main(String[] args) {
        ClassificationGame game = GamesFactory.getIrisClassificationGame();
        PreservationConfiguration preservationConfiguration = ConfigurationFactory.getPreservationConfiguration();
        PreservationService preservationService = new PreservationService(game, preservationConfiguration);

        Configuration configuration = ConfigurationFactory.getBaseConfiguration();
        PhenotypeStatisticsService phenotypeStatisticsService = new PhenotypeStatisticsService();
        NeuralEvolutionStatisticsService neuralEvolutionStatisticsService = new NeuralEvolutionStatisticsService();
        int testDataSize = ((IrisClassificationGame) game).testData.size();
        int trainingDataSize = ((IrisClassificationGame) game).trainingData.size();

        for (int i = 0; i < 100; i++) {
            NeuralEvolutionStatistics statistics = new NeuralEvolutionStatistics();

            Evolver evolver = new Evolver(game, statistics, preservationService, phenotypeStatisticsService);
            Phenotype<NeuralGene, Double> bestIndividual = evolver.evolve(configuration);

            System.out.println("B.I. fit: " + bestIndividual.getFitness());
            Double testDataAccuracy = new ClassificationEvaluator(game).eval(new GenotypeToNetworkMapper(null).mapToSkipLayerNetwork(bestIndividual.getGenotype()), false);
            System.out.println(String.format("Training data size: %d - Test data size: %d", trainingDataSize, testDataSize));

            System.out.println(testDataAccuracy);

            neuralEvolutionStatisticsService.addNeuralEvolutionStatistics(statistics);
        }

        System.out.println(phenotypeStatisticsService.getStatistics());

        neuralEvolutionStatisticsService.getAverageFitnessPerGenerationMap().entrySet().forEach(
                e -> System.out.println(e.getKey() + "," + (e.getValue() / trainingDataSize))
        );

        System.exit(0);
    }


//    void a(){
//
//        List<Map<Long, Phenotype>> bestIndividualPerGenerationList = neuralEvolutionStatisticsService.getBestIndividualPerGenerationList();
//
//        Map<Long, Double> averageFitness = bestIndividualPerGenerationList.stream().map(Function.identity())
//                .flatMap(map -> map.entrySet().stream())
//                .collect(
//                        Collectors.groupingBy(
//                                Map.Entry::getKey,
//                                Collectors.mapping(
//                                        Map.Entry::getValue,
//                                        Collectors.toList()
//                                )
//                        )
//                ).entrySet().stream().collect(Collectors.toMap(
//                        Map.Entry::getKey, entry -> entry.getValue().stream().map(Phenotype::getGenotype).mapToDouble(
//                                it -> new ClassificationEvaluator(game).eval(new GenotypeToNetworkMapper(null).mapToSkipLayerNetwork(it), false) / testDataSize
//                        ).average().orElse(0)
//                ));
//
//        averageFitness.entrySet().forEach(
//                e -> System.out.println(e.getKey() + "," + e.getValue())
//        );
//    }
}
