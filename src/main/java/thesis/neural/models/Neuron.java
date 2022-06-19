package thesis.neural.models;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.deeplearning4j.berkeley.Pair;
import thesis.genetic.processing.NetworkIdMap;

import java.io.Serializable;

/**
 * Created by Alex on 12/07/2016.
 */
public class Neuron implements Serializable {
    private final NeuronType neuronType;
    private final Integer id;
    private final Integer layer;
    private Double value = 0d;

    private Neuron(NeuronType neuronType, Integer id, Integer layer) {
        this.neuronType = neuronType;
        this.id = id;
        this.layer = layer;
    }

    // hidden node between two other nodes
    public static Neuron of(Neuron neuronIn, Neuron neuronOut, NetworkIdMap networkIdMap) {
        Pair<Integer, Integer> idLayerPair = networkIdMap.getNewNodeBetweenPairIdAndLayer(neuronIn, neuronOut);

        return new Neuron(NeuronType.HIDDEN, idLayerPair.getFirst(), idLayerPair.getSecond());
    }

    public static Neuron of(NeuronType neuronType, Integer id, Integer layer) {
        return new Neuron(neuronType, id, layer);
    }

    public NeuronType getNeuronType() {
        return neuronType;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLayer() {
        return layer;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"inboundSynapses", "outboundSynapses"});
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Neuron)) {
            return false;
        }
        Neuron otherNeuron = (Neuron) obj;
        return otherNeuron.getId().equals(this.id)
                && otherNeuron.getNeuronType().equals(this.neuronType)
                && otherNeuron.getLayer().equals(this.layer);
    }

    @Override
    public int hashCode() {
        int result = neuronType.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + layer.hashCode();
        return result;
    }

    public Neuron deepClone() {
        return new Neuron(neuronType, id, layer);
    }
}
