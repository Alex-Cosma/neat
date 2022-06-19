package thesis.game.games.xor;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.model.Move;

/**
 * Created by Alex on 25/07/2016.
 */
public class XorMove implements Move {

    private INDArray move;

    private XorMove(Integer nrInputs, boolean a, boolean b) {
        this.move = Nd4j.create(nrInputs);
        this.move.putScalar(0, a ? 1 : 0);
        this.move.putScalar(1, b ? 1 : 0);
    }

    private XorMove(INDArray move) {
        this.move = move;
    }

    public static XorMove of(Integer nrInputs, boolean a, boolean b) {
        return new XorMove(nrInputs, a, b);
    }

    public static XorMove of(INDArray move) {
        return new XorMove(move);
    }


    @Override
    public INDArray getMove() {
        return this.move;
    }
}
