package thesis.game.games.classification;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.FitnessFunction;
import thesis.game.model.GameState;

/**
 * Created by Alex on 8/7/2016.
 */
public class ClassificationFitness implements FitnessFunction {
    @Override
    public double computeFitness(GameState currentGameState, INDArray outputForCurrentGameState) {
        Integer expectedValue = extractExpectedValue(currentGameState);
        Integer output = ((int) outputForCurrentGameState.getDouble(0));

        return expectedValue.equals(output) ? 1 : 0;
    }

    private Integer extractExpectedValue(GameState currentGameState) {
        INDArray normalizedState = currentGameState.getNormalizedState();
        return (int) normalizedState.getDouble(normalizedState.columns() - 1);
    }
}
