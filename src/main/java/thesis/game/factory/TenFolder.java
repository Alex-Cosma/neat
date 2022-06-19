package thesis.game.factory;

import org.deeplearning4j.berkeley.Pair;
import org.jenetics.util.RandomRegistry;
import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.classification.ClassificationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Created by Alex on 8/7/2016.
 */
public class TenFolder {


    public static <T extends ClassificationModel> Pair<List<INDArray>, List<INDArray>> getTestTrainingData(Stream<T> csvData) {
        Random r = RandomRegistry.getRandom();
        List<INDArray> trainingData = new ArrayList<>();
        List<INDArray> testData = new ArrayList<>();
        csvData.forEach(entry -> {
            if (r.nextDouble() < 0.9) {
                trainingData.add(entry.toINDArray());
            } else {
                testData.add(entry.toINDArray());
            }
        });

        return Pair.makePair(trainingData, testData);
    }
}
