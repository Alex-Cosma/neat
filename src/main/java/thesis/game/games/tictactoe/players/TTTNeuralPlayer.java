package thesis.game.games.tictactoe.players;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.tictactoe.TTTMove;
import thesis.game.model.GameState;
import thesis.game.model.Move;
import thesis.game.model.NeuralPlayer;
import thesis.neural.networks.Network;

/**
 * Created by Alex on 7/31/2016.
 */
public class TTTNeuralPlayer extends NeuralPlayer {

    public TTTNeuralPlayer(Network net) {
        super(net);
    }

    @Override
    public Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId) {
        INDArray outputForCurrentGameState = net.feed(currentGameState);
        double max = -Double.MAX_VALUE;
        int position = -1;
        for (int j = 0; j < 9; j++) {
            if (currentGameState.getNormalizedState().getDouble(j) == 0) {
                if (max <= outputForCurrentGameState.getDouble(j)) {
                    position = j;
                    max = outputForCurrentGameState.getDouble(j);
                }
            }
        }

        return TTTMove.of(position, myId);
    }
}
