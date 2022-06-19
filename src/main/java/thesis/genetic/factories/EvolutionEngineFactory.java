package thesis.genetic.factories;

import org.jenetics.Genotype;
import org.jenetics.Optimize;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import thesis.configuration.genetic.Configuration;
import thesis.game.evaluation.Evaluator;
import thesis.game.model.Game;
import thesis.genetic.mappers.GenotypeToNetworkMapper;
import thesis.genetic.models.NeuralChromosome;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.networks.Network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Alex on 02/08/2016.
 */
public class EvolutionEngineFactory {

    private final Configuration configuration;
    private final MutatorFactory mutatorFactory;
    private final SelectorFactory selectorFactory;
    private final ExecutorService executor;
    private final GenotypeToNetworkMapper genotypeToNetworkMapper;
    private final Evaluator evaluator;
    private final NetworkIdMap networkIdMap;
    private final Game game;

    public EvolutionEngineFactory(Configuration configuration, MutatorFactory mutatorFactory, SelectorFactory selectorFactory, GenotypeToNetworkMapper genotypeToNetworkMapper, Evaluator evaluator, NetworkIdMap networkIdMap, Game game) {
        this.configuration = configuration;
        this.mutatorFactory = mutatorFactory;
        this.selectorFactory = selectorFactory;
        this.genotypeToNetworkMapper = genotypeToNetworkMapper;
        this.executor = Executors.newFixedThreadPool(configuration.getEvolutionThreadPool());
        this.evaluator = evaluator;
        this.networkIdMap = networkIdMap;
        this.game = game;
    }

    public Engine<NeuralGene, Double> getEvolutionEngine() {
        Codec<Network, NeuralGene> genotypeToNetworkCodec = Codec.of(
                Genotype.of(NeuralChromosome.of(game.getNumberOfInputs(), game.getNumberOfOutputs(), networkIdMap)),
                genotypeToNetworkMapper::mapToSkipLayerNetwork
        );

        return Engine
                .builder(evaluator::eval, genotypeToNetworkCodec)
                .populationSize(configuration.getPopulationSize())
                .offspringFraction(configuration.getOffspringFraction())
                .alterers(
                        mutatorFactory.getAddSynapseMutator(networkIdMap),
                        mutatorFactory.getAddNodeMutator(networkIdMap),
                        mutatorFactory.getDisableSynapseMutator(),
                        mutatorFactory.getEnableSynapseMutator(),
                        mutatorFactory.getCrossoverAlterer(),
                        mutatorFactory.getModifyWeightMutator()
                )
                .maximalPhenotypeAge(configuration.getMaximumPhenotypeAge())
                .offspringSelector(selectorFactory.getOffspringSelector())
                .survivorsSelector(selectorFactory.getElitismSelector())
                .executor(executor)
                .optimize(Optimize.MAXIMUM)
                .build();
    }

}
