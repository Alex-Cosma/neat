package thesis.game.model;

import thesis.neural.networks.Network;

/**
 * Created by Alex on 7/31/2016.
 */
public abstract class NeuralPlayer extends Player {

    protected final Network net;
    private Double fitness = 0d;

    public NeuralPlayer(Network net) {
        this.net = net;
    }

    public void incrementFitnessBy(Double value) {
        this.fitness += value;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }
}
