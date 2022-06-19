package thesis.game.games.tictactoe;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.FitnessFunction;
import thesis.game.model.GameState;

/**
 * Created by Alex on 29/07/2016.
 */
public class TTTFitness implements FitnessFunction {
    @Override
    public double computeFitness(GameState currentGameState, INDArray outputForCurrentGameState) {
        return gameWonByNeuralPlayer(currentGameState) ? 2 : gameTied(currentGameState) ? 1 : 0;
    }

    public boolean gameTied(GameState currentGameState) {
        boolean tieGame = false;
        boolean boardFull = true;
        for (int i = 0; i < currentGameState.getNormalizedState().columns(); i++) {
            if (currentGameState.getNormalizedState().getDouble(i) == 0) {
                boardFull = false;
            }
        }
        boolean wonGame = gameWonByNeuralPlayer(currentGameState);
        if (!wonGame && boardFull) {
            tieGame = true;
        }
        return tieGame;
    }


    private boolean gameWonByNeuralPlayer(GameState currentGameState) {
        return ((TTTGameState) currentGameState).gameWonByPlayer(TTTGame.NEURAL_PLAYER_ID);
    }
}
