package thesis.genetic.metavolution;

import org.jenetics.DoubleChromosome;
import org.jenetics.DoubleGene;
import org.jenetics.Genotype;
import org.jenetics.Optimize;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import thesis.configuration.genetic.Configuration;
import thesis.configuration.genetic.MetaConfiguration;
import thesis.genetic.metavolution.evaluation.MetaEvaluator;
import thesis.genetic.metavolution.mappers.MetaGenotypeToConfigurationMapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static thesis.genetic.metavolution.mappers.MetaConfigurationChromosomeFieldsMap.*;

/**
 * Created by Alex on 03/08/2016.
 */
public class MetaEvolutionEngineFactory {

    private final MetaGenotypeToConfigurationMapper metaGenotypeToConfigurationMapper;
    private final MetaEvaluator metaEvaluator;
    private final MetaConfiguration metaConfiguration;
    private final Executor executor;

    public MetaEvolutionEngineFactory(MetaGenotypeToConfigurationMapper metaGenotypeToConfigurationMapper, MetaEvaluator evaluator, MetaConfiguration metaConfiguration) {
        this.metaGenotypeToConfigurationMapper = metaGenotypeToConfigurationMapper;
        this.metaEvaluator = evaluator;
        this.metaConfiguration = metaConfiguration;
        this.executor = Executors.newFixedThreadPool(metaConfiguration.getMetaEvolutionThreadPool());
    }

    public Engine<DoubleGene, Double> getEvolutionEngine() {
        Codec<Configuration, DoubleGene> genotypeToConfigurationCodec = Codec.of(
                Genotype.of(
                        DoubleChromosome.of(
                                metaConfiguration.getMetaMutationProbabilityBoundLower(),
                                metaConfiguration.getMetaMutationProbabilityBoundUpper(),
                                mutationChromosomeConfigurationMap.keySet().size()),
                        DoubleChromosome.of(
                                metaConfiguration.getMetaOffspringFractionBoundLower(),
                                metaConfiguration.getMetaOffspringFractionBoundUpper(),
                                offspringChromosomeConfigurationMap.keySet().size()),
                        DoubleChromosome.of(
                                metaConfiguration.getMetaPopulationBoundLower(),
                                metaConfiguration.getMetaPopulationBoundUpper(),
                                populationChromosomeConfigurationMap.keySet().size())
                ),
                metaGenotypeToConfigurationMapper::mapToConfiguration
        );

        return Engine
                .builder(metaEvaluator::eval, genotypeToConfigurationCodec)
                .populationSize(metaConfiguration.getMetaPopulationSize())
                .executor(executor)
                .optimize(Optimize.MAXIMUM)
                .build();
    }

}
