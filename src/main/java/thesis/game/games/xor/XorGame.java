package thesis.game.games.xor;

import org.jenetics.engine.EvolutionResult;
import thesis.game.games.xor.players.XorPlayer;
import thesis.game.model.FitnessFunction;
import thesis.game.model.Game;
import thesis.game.model.Move;
import thesis.neural.networks.Network;

import java.util.function.Predicate;

/**
 * Created by Alex on 25/07/2016.
 */
public class XorGame extends Game {

    private static final double FITNESS_THRESHOLD = 3.97;
    private static final int MAX_GENERATIONS = 300;
    private static final String GAME_NAME = "XOR";

    private XorGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, XorPlayer neuralPlayer) {
        super(nrInputs, nrOutputs, fitnessFunction, neuralPlayer);
        this.currentGameState = XorGameState.initialState(nrInputs);
        this.neuralTurn = true; // "board" already set-up
    }

    public XorGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction) {
        this(nrInputs, nrOutputs, fitnessFunction, null);
    }

    @Override
    public Game newInstance(Network neuralPlayer) {
        if (this.nrInputs == null || this.nrOutputs == null || this.fitnessFunction == null || neuralPlayer == null) {
            throw new IllegalStateException("Invalid model");
        }
        return new XorGame(nrInputs, nrOutputs, fitnessFunction, new XorPlayer(neuralPlayer));
    }

    private Move getStateForNextInputSet() {
        boolean a = currentGameState.getNormalizedState().getDouble(0, 0) != 0;
        boolean b = currentGameState.getNormalizedState().getDouble(0, 1) != 0;

        if (!a && !b) {
            return XorMove.of(this.nrInputs, false, true);
        } else if (!a && b) {
            return XorMove.of(this.nrInputs, true, false);
        } else if (a && !b) {
            return XorMove.of(this.nrInputs, true, true);
        } else {
            this.isOver = true;
            neuralPlayer.setFitness(4 - neuralPlayer.getFitness());
            return XorMove.of(this.nrInputs, false, false);
        }
    }

    @Override
    public void playNextMove() {
        Move m;
        if (neuralTurn) {
            m = neuralPlayer.move(currentGameState, null, null);
            double value = fitnessFunction.computeFitness(currentGameState, m.getMove());
            neuralPlayer.incrementFitnessBy(value);
        } else {
            m = getStateForNextInputSet();
            this.currentGameState = XorGameState.of(m.getMove());
        }

        neuralTurn = !neuralTurn;
    }

    @Override
    public double getFitnessThreshold() {
        return FITNESS_THRESHOLD;
    }

    @Override
    public String getTitle() {
        return GAME_NAME;
    }

    @Override
    public Predicate<EvolutionResult<?, Double>> getTerminationPredicate() {
        return result -> result.getGeneration() < MAX_GENERATIONS && result.getBestFitness() < FITNESS_THRESHOLD;
    }

}
