package thesis.game.games.classification;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.GameState;

/**
 * Created by Alex on 8/7/2016.
 */
public class ClassificationGameState extends GameState {

    private ClassificationGameState(INDArray state) {
        super(state);
    }

    public static ClassificationGameState of(INDArray state) {
        return new ClassificationGameState(state);
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
