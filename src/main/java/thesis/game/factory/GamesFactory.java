package thesis.game.factory;

import org.deeplearning4j.berkeley.Pair;
import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.classification.ClassificationFitness;
import thesis.game.games.classification.Iris.IrisClassificationGame;
import thesis.game.games.classification.Iris.IrisModel;
import thesis.game.games.tictactoe.TTTFitness;
import thesis.game.games.tictactoe.TTTGame;
import thesis.game.games.tictactoe.players.TTTBestPlayer;
import thesis.game.games.xor.XorFitness;
import thesis.game.games.xor.XorGame;
import thesis.game.model.Game;
import thesis.game.model.classifications.ClassificationGame;
import uk.elementarysoftware.quickcsv.api.CSVParser;
import uk.elementarysoftware.quickcsv.api.CSVParserBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Alex on 29/07/2016.
 */
public class GamesFactory {

    private static final Integer XOR_NR_INPUTS = 2;
    private static final Integer XOR_NR_OUTPUTS = 1;

    private static final Integer TTT_NR_INPUTS = 9;
    private static final Integer TTT_NR_OUTPUTS = 9;

    private static final Integer IRIS_CLASSIFICATION_NR_INPUTS = 4;
    private static final Integer IRIS_CLASSIFICATION_NR_OUTPUTS = 3;

    public static Game getXorGame() {
        return new XorGame(XOR_NR_INPUTS, XOR_NR_OUTPUTS, new XorFitness());
    }

    public static Game getTTTGame() {
        return new TTTGame(TTT_NR_INPUTS, TTT_NR_OUTPUTS, new TTTFitness(), new TTTBestPlayer());
    }

    public static ClassificationGame getIrisClassificationGame() {
        CSVParser<IrisModel> parser = CSVParserBuilder.aParser(IrisModel::new, IrisModel.CSVFields.class)
                .forRfc4180()
                .usingExplicitHeader("sepalLength", "sepalWidth", "petalLength", "petalWidth", "clss")
                .build();

        Stream<IrisModel> csvData = null;
        try {
            csvData = parser.parse(new File("classification-data/Iris/iris.data")).sequential();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pair<List<INDArray>, List<INDArray>> testTrainingData = TenFolder.getTestTrainingData(csvData);
        return new IrisClassificationGame(IRIS_CLASSIFICATION_NR_INPUTS, IRIS_CLASSIFICATION_NR_OUTPUTS, new ClassificationFitness(), testTrainingData.getFirst(), testTrainingData.getSecond());
    }


}
