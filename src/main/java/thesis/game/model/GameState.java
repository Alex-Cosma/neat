package thesis.game.model;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Created by Alex on 25/07/2016.
 */
public abstract class GameState implements Comparable {
    protected INDArray state;

    protected GameState(Integer nrInputs) {
        state = Nd4j.create(nrInputs);
    }

    protected GameState(INDArray state) {
        this.state = state;
    }

    public abstract INDArray getNormalizedState();

    @Override
    public int compareTo(Object other) {
        if (!(other instanceof GameState)) {
            return -1;
        }
        GameState otherGameState = (GameState) other;
        return otherGameState.equals(this) ? 0 : 1;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GameState)) {
            return false;
        }
        GameState otherGameState = (GameState) other;
        return otherGameState.getNormalizedState().equals(((GameState) other).getNormalizedState());
    }

    public abstract void outputToConsole();

}
