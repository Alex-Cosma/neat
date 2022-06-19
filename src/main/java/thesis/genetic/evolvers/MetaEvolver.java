package thesis.genetic.evolvers;

import org.jenetics.DoubleGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.limit;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.genetic.MetaConfiguration;
import thesis.genetic.metavolution.MetaEvolutionEngineFactory;
import thesis.genetic.metavolution.evaluation.MetaEvaluator;
import thesis.genetic.metavolution.mappers.MetaGenotypeToConfigurationMapper;
import thesis.statistics.NeuralEvolutionStatistics;

/**
 * Created by Alex on 03/08/2016.
 */
public class MetaEvolver {
    private final NeuralEvolutionStatistics statistics;
    private final MetaConfiguration metaConfiguration;
    private final Evolver evolver;

    public MetaEvolver(NeuralEvolutionStatistics statistics, MetaConfiguration metaConfiguration, Evolver evolver) {
        this.statistics = statistics;
        this.metaConfiguration = metaConfiguration;
        this.evolver = evolver;
    }

    public Configuration evolve() {
        MetaEvaluator evaluator = new MetaEvaluator(evolver);
        MetaGenotypeToConfigurationMapper metaGenotypeToConfigurationMapper = new MetaGenotypeToConfigurationMapper();

        MetaEvolutionEngineFactory metaEvolutionEngineFactory = new MetaEvolutionEngineFactory(metaGenotypeToConfigurationMapper, evaluator, metaConfiguration);
        Engine<DoubleGene, Double> engine = metaEvolutionEngineFactory.getEvolutionEngine();

        Phenotype<DoubleGene, Double> bestPhenotype = engine
                .stream()
                .limit(limit.byFixedGeneration(metaConfiguration.getMetaMaximumGenerations()))
                .peek(statistics).collect(EvolutionResult.toBestPhenotype());

        return metaGenotypeToConfigurationMapper.mapToConfiguration(bestPhenotype.getGenotype());

    }
}
