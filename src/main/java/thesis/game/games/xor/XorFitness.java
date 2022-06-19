package thesis.game.games.xor;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.model.FitnessFunction;
import thesis.game.model.GameState;

/**
 * Created by Alex on 25/07/2016.
 */
public class XorFitness implements FitnessFunction {

    public double computeFitness(GameState currentGameState, INDArray outputForCurrentGameState) {
        Double expectedValues = getExpectedOutput(currentGameState.getNormalizedState());

        Double output = outputForCurrentGameState.getDouble(0);

        return Math.abs(expectedValues - output);
    }

    private Double getExpectedOutput(INDArray input) {
        if (input.equals(Nd4j.create(new double[]{0, 0}))) {
            return 0d;
        } else if (input.equals(Nd4j.create(new double[]{0, 1}))) {
            return 1d;
        } else if (input.equals(Nd4j.create(new double[]{1, 0}))) {
            return 1d;
        } else {
            return 0d;
        }
    }

}
