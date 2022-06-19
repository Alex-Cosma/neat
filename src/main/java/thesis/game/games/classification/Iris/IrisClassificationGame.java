package thesis.game.games.classification.Iris;

import org.jenetics.engine.EvolutionResult;
import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.classification.ClassificationGameState;
import thesis.game.games.classification.players.ClassificationPlayer;
import thesis.game.model.FitnessFunction;
import thesis.game.model.Game;
import thesis.game.model.Move;
import thesis.game.model.NeuralPlayer;
import thesis.game.model.classifications.ClassificationGame;
import thesis.neural.networks.Network;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Alex on 8/7/2016.
 */
public class IrisClassificationGame extends ClassificationGame {

    public static final String GAME_NAME = "Iris_Classification";
    private static final long MAX_GENERATIONS = 350;

    private IrisClassificationGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, NeuralPlayer neuralPlayer, List<INDArray> trainingData, List<INDArray> testData) {
        super(nrInputs, nrOutputs, fitnessFunction, neuralPlayer, trainingData, testData);
        this.currentGameState = ClassificationGameState.of(trainingData.get(0));
        neuralTurn = true;
    }

    public IrisClassificationGame(Integer nrInputs, Integer nrOutputs, FitnessFunction fitnessFunction, List<INDArray> trainingData, List<INDArray> testData) {
        super(nrInputs, nrOutputs, fitnessFunction, null, trainingData, testData);
    }

    @Override
    public Game newInstance(Network neuralPlayer) {
        return new IrisClassificationGame(nrInputs, nrOutputs, fitnessFunction, new ClassificationPlayer(neuralPlayer), trainingData, testData);
    }

    @Override
    public void playNextMove() {
        List<INDArray> currentData;
        currentData = isTraining ? trainingData : testData;

        if (!neuralTurn) {
            if (currentMoveIndex < currentData.size() - 1) {
                currentMoveIndex += 1;
                INDArray nextMove = currentData.get(currentMoveIndex);
                currentGameState = ClassificationGameState.of(nextMove);
            } else {
                isOver = true;
            }
        } else {
            Move neuralMove = neuralPlayer.move(currentGameState, null, null);
            neuralPlayer.incrementFitnessBy(fitnessFunction.computeFitness(currentGameState, neuralMove.getMove()));
        }
        neuralTurn = !neuralTurn;

    }

    @Override
    public double getFitnessThreshold() {
        return (isTraining ? trainingData.size() : testData.size()) - 1.1;
    }

    @Override
    public String getTitle() {
        return GAME_NAME;
    }

    @Override
    public Predicate<EvolutionResult<?, Double>> getTerminationPredicate() {
        return result -> result.getGeneration() < MAX_GENERATIONS && result.getBestFitness() <= getFitnessThreshold();
    }
}
