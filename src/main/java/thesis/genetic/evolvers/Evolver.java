package thesis.genetic.evolvers;

import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.limit;
import thesis.configuration.genetic.Configuration;
import thesis.game.evaluation.Evaluator;
import thesis.game.model.Game;
import thesis.genetic.factories.EvolutionEngineFactory;
import thesis.genetic.factories.MutatorFactory;
import thesis.genetic.factories.SelectorFactory;
import thesis.genetic.mappers.GenotypeToNetworkMapper;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.models.SpeciesPool;
import thesis.genetic.processing.NetworkIdMap;
import thesis.preservation.PreservationService;
import thesis.statistics.NeuralEvolutionStatistics;
import thesis.statistics.PhenotypeStatisticsService;

import java.util.function.Predicate;

/**
 * Created by Alex on 03/08/2016.
 */
public class Evolver {

    private final Game game;
    private final NeuralEvolutionStatistics statistics;
    private final PreservationService preservationService;
    private final PhenotypeStatisticsService phenotypeStatisticsService;

    public Evolver(Game game, NeuralEvolutionStatistics statistics, PreservationService preservationService, PhenotypeStatisticsService phenotypeStatisticsService) {
        this.game = game;
        this.statistics = statistics;
        this.preservationService = preservationService;
        this.phenotypeStatisticsService = phenotypeStatisticsService;
    }

    public Phenotype<NeuralGene, Double> evolve(Configuration configuration) {
        Evaluator evaluator = new Evaluator(game);
        NetworkIdMap networkIdMap = new NetworkIdMap(game.getNumberOfInputs(), game.getNumberOfOutputs());
        MutatorFactory mutatorFactory = new MutatorFactory(configuration);
        SpeciesPool speciesPool = new SpeciesPool(configuration);
        SelectorFactory selectorFactory = new SelectorFactory(speciesPool);
        GenotypeToNetworkMapper genotypeToNetworkMapper = new GenotypeToNetworkMapper(networkIdMap);
        EvolutionEngineFactory evolutionEngineFactory = new EvolutionEngineFactory(configuration, mutatorFactory, selectorFactory, genotypeToNetworkMapper, evaluator, networkIdMap, game);

        Engine<NeuralGene, Double> engine = evolutionEngineFactory.getEvolutionEngine();

        Phenotype<NeuralGene, Double> bestPhenotype = engine.stream().limit(game.getTerminationPredicate()).peek(statistics).collect(EvolutionResult.toBestPhenotype());
        preservationService.preserve(bestPhenotype);
        phenotypeStatisticsService.addPhenotypeStatistics(bestPhenotype);
        return bestPhenotype;
    }

    public NeuralEvolutionStatistics getStatistics() {
        return statistics;
    }

}
