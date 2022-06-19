package thesis.game.model.classifications;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.FitnessFunction;
import thesis.game.model.Game;
import thesis.game.model.NeuralPlayer;

import java.util.List;

/**
 * Created by Alex on 8/7/2016.
 */
public abstract class ClassificationGame extends Game {

    public List<INDArray> trainingData;
    public List<INDArray> testData;

    protected Boolean isTraining;
    protected int currentMoveIndex = 0;

    public ClassificationGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, NeuralPlayer neuralPlayer, List<INDArray> trainingData, List<INDArray> testData) {
        super(nrInputs, nrOutputs, fitnessFunction, neuralPlayer);
        this.trainingData = trainingData;
        this.testData = testData;
        this.isTraining = true;
    }

    public void setIsTraining(boolean isTraining) {
        this.isTraining = isTraining;
        currentMoveIndex = 0;
    }

}
