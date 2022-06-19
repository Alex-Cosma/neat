package thesis.game.evaluation;

import thesis.game.model.Game;
import thesis.neural.networks.Network;

/**
 * Created by Alex on 25/07/2016.
 */
public class Evaluator {

    protected Game game;

    public Evaluator(Game game) {
        this.game = game;
    }

    public synchronized Double eval(Network network) {
        game = game.newInstance(network);
        play();
        return getFitness();
    }

    protected void play() {
        while (!game.isOver()) {
            game.playNextMove();
        }
    }

    public double getFitness() {
        return game.getFitness();
    }

}
