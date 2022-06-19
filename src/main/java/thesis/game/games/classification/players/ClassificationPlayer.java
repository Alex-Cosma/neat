package thesis.game.games.classification.players;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.classification.ClassificationMove;
import thesis.game.model.GameState;
import thesis.game.model.Move;
import thesis.game.model.NeuralPlayer;
import thesis.neural.networks.Network;

/**
 * Created by Alex on 8/7/2016.
 */
public class ClassificationPlayer extends NeuralPlayer {

    public ClassificationPlayer(Network net) {
        super(net);
    }

    @Override
    public Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId) {
        INDArray outputForCurrentGameState = net.feed(currentGameState);
        double max = -Double.MAX_VALUE;
        int clss = -1;
        for (int j = 0; j < outputForCurrentGameState.columns(); j++) {
            if (max <= outputForCurrentGameState.getDouble(j)) {
                clss = j;
                max = outputForCurrentGameState.getDouble(j);
            }
        }

        return ClassificationMove.of(clss);
    }
}
