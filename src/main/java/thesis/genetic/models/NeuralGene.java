package thesis.genetic.models;

import org.jenetics.Gene;
import thesis.genetic.processing.NetworkIdMap;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;

import java.io.Serializable;

/**
 * Created by Alex on 12/07/2016.
 */
public class NeuralGene implements Gene<Synapse, NeuralGene>, Serializable {

    private final Synapse synapse;
    private final NetworkIdMap networkIdMap;

    private NeuralGene(Synapse synapse, NetworkIdMap networkIdMap) {
        this.synapse = synapse;
        this.networkIdMap = networkIdMap;
    }

    public static NeuralGene of(Neuron inNeuron, Neuron outNeuron, double synapseWeight, boolean enabled, NetworkIdMap networkIdMap) {
        Synapse synapse = Synapse.of(
                inNeuron,
                outNeuron,
                synapseWeight,
                enabled,
                networkIdMap
        );
        return new NeuralGene(synapse, networkIdMap);
    }

    public static NeuralGene of(Synapse synapse, NetworkIdMap networkIdMap) {
        return new NeuralGene(synapse, networkIdMap);
    }

    @Override
    public Synapse getAllele() {
        return this.synapse;
    }

    @Override
    public NeuralGene newInstance() {
        return NeuralGene.of(
                this.synapse.getIn().deepClone(),
                this.synapse.getOut().deepClone(),
                this.synapse.getWeight(),
                true,
                this.networkIdMap
        );
    }

    @Override
    public NeuralGene newInstance(Synapse value) {
        return new NeuralGene(value, networkIdMap);
    }

    public Integer getInnovationNumber() {
        return synapse.getInnovationNumber();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NeuralGene)) {
            return false;
        }
        NeuralGene otherNeuralGene = (NeuralGene) obj;
        return otherNeuralGene.getAllele().equals(this.synapse);
    }

    @Override
    public int hashCode() {
        return synapse.hashCode();
    }

}
