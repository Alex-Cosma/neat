package thesis.game.model;

/**
 * Created by Alex on 29/07/2016.
 */
public abstract class Player {

    public abstract Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId);
}
