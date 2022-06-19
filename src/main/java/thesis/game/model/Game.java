package thesis.game.model;

import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.neural.networks.Network;

import java.util.function.Predicate;

/**
 * Created by Alex on 25/07/2016.
 */
public abstract class Game {

    protected final FitnessFunction fitnessFunction;
    protected final Integer nrInputs;
    protected final Integer nrOutputs;
    protected Boolean isOver = false;
    protected NeuralPlayer neuralPlayer;
    protected boolean neuralTurn;
    protected GameState currentGameState;

    public Game(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, NeuralPlayer neuralPlayer) {
        this.fitnessFunction = fitnessFunction;
        this.nrInputs = nrInputs;
        this.nrOutputs = nrOutputs;
        this.neuralPlayer = neuralPlayer;
        this.neuralTurn = RandomRegistry.getRandom().nextBoolean();
    }

    public abstract Game newInstance(Network neuralPlayer);

    public int getNumberOfInputs() {
        return this.nrInputs;
    }

    public int getNumberOfOutputs() {
        return this.nrOutputs;
    }

    public boolean isOver() {
        return this.isOver;
    }

    public abstract void playNextMove();

    public abstract double getFitnessThreshold();

    public double getFitness() {
        return neuralPlayer.getFitness();
    }

    public abstract String getTitle();

    public abstract Predicate<EvolutionResult<?, Double>> getTerminationPredicate();
}
