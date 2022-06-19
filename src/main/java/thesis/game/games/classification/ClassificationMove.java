package thesis.game.games.classification;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.model.Move;

/**
 * Created by Alex on 8/7/2016.
 */
public class ClassificationMove implements Move {

    private INDArray move;

    private ClassificationMove(Integer clss) {
        this.move = Nd4j.create(1);
        this.move.putScalar(0, clss);
    }

    public static ClassificationMove of(Integer clss) {
        return new ClassificationMove(clss);
    }

    @Override
    public INDArray getMove() {
        return move;
    }
}
