package thesis.game.games.tictactoe;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import thesis.game.model.Move;

/**
 * Created by Alex on 29/07/2016.
 */
public class TTTMove implements Move {

    private INDArray move;

    private TTTMove(Integer move, Integer player) {
        this.move = Nd4j.create(2).putSlice(0, Nd4j.create(new double[]{move, player}));
    }

    public static TTTMove of(Integer move, Integer player) {
        return new TTTMove(move, player);
    }

    @Override
    public INDArray getMove() {
        return this.move;
    }
}
