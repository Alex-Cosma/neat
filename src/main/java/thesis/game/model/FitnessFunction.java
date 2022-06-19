package thesis.game.model;

import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * Created by Alex on 29/07/2016.
 */
public interface FitnessFunction {

    double computeFitness(GameState currentGameState, INDArray outputForCurrentGameState);

}
