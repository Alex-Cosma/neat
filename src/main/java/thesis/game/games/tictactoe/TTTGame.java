package thesis.game.games.tictactoe;

import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.RandomRegistry;
import thesis.game.games.tictactoe.players.TTTNeuralPlayer;
import thesis.game.model.FitnessFunction;
import thesis.game.model.Game;
import thesis.game.model.Move;
import thesis.game.model.Player;
import thesis.neural.networks.Network;

import java.util.function.Predicate;

/**
 * Created by Alex on 29/07/2016.
 */
public class TTTGame extends Game {

    public static final int NEURAL_PLAYER_ID = 1;
    public static final int NON_NEURAL_PLAYER_ID = -1;
    private static final double FITNESS_THRESHOLD = 79.9;
    private static final long MAX_GENERATIONS = 500;
    private static final String GAME_NAME = "TicTacToe";
    private final Player nonNeuralPlayer;
    private int maxNrOfGames = 80;

    private TTTGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, Player nonNeuralPlayer, TTTNeuralPlayer neuralPlayer) {
        super(nrInputs, nrOutputs, fitnessFunction, neuralPlayer);
        this.currentGameState = TTTGameState.initialState(nrInputs);
        this.nonNeuralPlayer = nonNeuralPlayer;
        this.isOver = false;
    }

    public TTTGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, Player nonNeuralPlayer) {
        this(nrInputs, nrOutputs, fitnessFunction, nonNeuralPlayer, null);
    }

    @Override
    public Game newInstance(Network neuralPlayer) {
        if (nrInputs == null || nrOutputs == null || fitnessFunction == null || nonNeuralPlayer == null || neuralPlayer == null) {
            throw new IllegalStateException("Invalid model");
        }
        return new TTTGame(nrInputs, nrOutputs, fitnessFunction, nonNeuralPlayer, new TTTNeuralPlayer(neuralPlayer));
    }

    @Override
    public void playNextMove() {
        Move m;
        if (neuralTurn) {
            m = neuralPlayer.move(currentGameState, NEURAL_PLAYER_ID, NON_NEURAL_PLAYER_ID);
        } else {
            m = nonNeuralPlayer.move(currentGameState, NON_NEURAL_PLAYER_ID, NEURAL_PLAYER_ID);
        }

        setNetGameState(m);
        TTTGameState currentGameState = (TTTGameState) this.currentGameState;

        if (endingConditionsAreMet(currentGameState)) {
            neuralPlayer.incrementFitnessBy(fitnessFunction.computeFitness(currentGameState, null));
            manageGameStateChange();
        } else {
            neuralTurn = !neuralTurn;
        }
    }

    private void manageGameStateChange() {
        if (maxNrOfGames > 0) {
            this.currentGameState = TTTGameState.initialState(nrInputs);
            maxNrOfGames -= 1;
            this.neuralTurn = RandomRegistry.getRandom().nextBoolean();
        } else {
            this.isOver = true;
        }
    }

    private boolean endingConditionsAreMet(TTTGameState currentGameState) {
        return gameBoardIsFull() || currentGameState.gameWonByPlayer(NON_NEURAL_PLAYER_ID) || currentGameState.gameWonByPlayer(NEURAL_PLAYER_ID);
    }

    private void setNetGameState(Move m) {
        currentGameState.getNormalizedState().putScalar((int) m.getMove().getDouble(0), m.getMove().getDouble(1));
    }

    @Override
    public double getFitnessThreshold() {
        return FITNESS_THRESHOLD;
    }


    private boolean gameBoardIsFull() {
        for (int i = 0; i < currentGameState.getNormalizedState().columns(); i++) {
            if (currentGameState.getNormalizedState().getDouble(i) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getTitle() {
        return GAME_NAME;
    }

    @Override
    public Predicate<EvolutionResult<?, Double>> getTerminationPredicate() {
        return result -> result.getGeneration() < MAX_GENERATIONS && result.getBestFitness() <= FITNESS_THRESHOLD;
    }
}
