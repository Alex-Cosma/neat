package thesis.game.evaluation;

import thesis.game.model.classifications.ClassificationGame;
import thesis.neural.networks.Network;

/**
 * Created by Alex on 8/8/2016.
 */
public class ClassificationEvaluator extends Evaluator {

    public ClassificationEvaluator(ClassificationGame game) {
        super(game);
    }

    public synchronized Double eval(Network network, boolean isTraining) {
        game = game.newInstance(network);
        ((ClassificationGame) game).setIsTraining(isTraining);
        play();
        return getFitness();
    }


}
