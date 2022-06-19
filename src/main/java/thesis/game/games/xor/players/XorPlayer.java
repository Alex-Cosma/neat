package thesis.game.games.xor.players;

import thesis.game.games.xor.XorMove;
import thesis.game.model.GameState;
import thesis.game.model.Move;
import thesis.game.model.NeuralPlayer;
import thesis.neural.networks.Network;

/**
 * Created by Alex on 7/31/2016.
 */
public class XorPlayer extends NeuralPlayer {

    public XorPlayer(Network net) {
        super(net);
    }

    @Override
    public Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId) {
        return XorMove.of(net.feed(currentGameState));
    }
}
