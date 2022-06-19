package thesis.game.games.classification.Iris;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.games.classification.ClassificationModel;
import uk.elementarysoftware.quickcsv.api.CSVRecordWithHeader;

/**
 * Created by Alex on 8/7/2016.
 */
public class IrisModel extends ClassificationModel {

    private Double sepalLength;
    private Double sepalWidth;
    private Double petalLength;
    private Double petalWidth;
    private Integer clss;

    public IrisModel(CSVRecordWithHeader<CSVFields> r) {
        this.sepalLength = r.getField(CSVFields.sepalLength).asDouble();
        this.sepalWidth = r.getField(CSVFields.sepalWidth).asDouble();
        this.petalLength = r.getField(CSVFields.petalLength).asDouble();
        this.petalWidth = r.getField(CSVFields.petalWidth).asDouble();
        this.clss = mapClass(r.getField(CSVFields.clss).asString());
    }

    private Integer mapClass(String clss) {
        switch (clss) {
            case "Iris-setosa":
                return 0;
            case "Iris-versicolor":
                return 1;
            case "Iris-virginica":
                return 2;
        }
        throw new IllegalStateException("Invalid uncaught class encountered.");
    }

    @Override
    public INDArray toINDArray() {
        INDArray arr = Nd4j.create(5);
        arr.putSlice(0, Nd4j.create(new double[]{sepalLength}));
        arr.putSlice(1, Nd4j.create(new double[]{sepalWidth}));
        arr.putSlice(2, Nd4j.create(new double[]{petalLength}));
        arr.putSlice(3, Nd4j.create(new double[]{petalWidth}));
        arr.putSlice(4, Nd4j.create(new double[]{clss}));
        return arr;
    }

    public enum CSVFields {
        sepalLength,
        sepalWidth,
        petalLength,
        petalWidth,
        clss
    }

}
