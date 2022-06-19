package thesis.neural.networks;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.GameState;

/**
 * Created by Alex on 28/07/2016.
 */
public interface Network {

    INDArray feed(GameState currentState);

}
