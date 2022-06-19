package thesis.game.games.xor;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.GameState;

/**
 * Created by Alex on 25/07/2016.
 */
public class XorGameState extends GameState {

    private XorGameState(Integer nrInputs) {
        super(nrInputs);
    }

    private XorGameState(INDArray state) {
        super(state);
    }

    public static XorGameState initialState(Integer nrInputs) {
        return new XorGameState(nrInputs);
    }

    public static XorGameState of(INDArray state) {
        return new XorGameState(state);
    }

    @Override
    public INDArray getNormalizedState() {
        return state;
    }

    @Override
    public void outputToConsole() {
        System.out.println(state);
    }

}
