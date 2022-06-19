package thesis.game.games.tictactoe.players;

import org.jenetics.util.RandomRegistry;
import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.tictactoe.TTTGame;
import thesis.game.games.tictactoe.TTTMove;
import thesis.game.model.GameState;
import thesis.game.model.Move;
import thesis.game.model.Player;

import java.util.Random;

/**
 * Created by Alex on 7/30/2016.
 */
public class TTTRandomPlayer extends Player {
    @Override
    public Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId) {
        INDArray state = currentGameState.getNormalizedState().getRow(0);
        Random random = RandomRegistry.getRandom();
        int[] boardState = new int[9];

        for (int i = 0; i < state.columns(); i++) {
            boardState[i] = (int) state.getDouble(i);
        }

        int myMove = -1;

        while (myMove == -1) {
            int r = random.nextInt(9);
            if (boardState[r] == 0) {
                myMove = r;
            }
        }

        return TTTMove.of(myMove, TTTGame.NON_NEURAL_PLAYER_ID);
    }
}
