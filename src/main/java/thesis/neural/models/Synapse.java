package thesis.neural.models;

import thesis.genetic.processing.NetworkIdMap;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex on 12/07/2016.
 */
public class Synapse implements Serializable {
    public static final AtomicInteger globalInnovationNumber = new AtomicInteger(0);

    private final Integer id;
    private final Neuron in;
    private final Neuron out;
    private final Integer innovationNumber;
    private Double weight;
    private boolean enabled;


    private Synapse(Integer id, Neuron in, Neuron out, Double weight, boolean enabled, Integer innovationNumber) {
        this.id = id;
        this.in = in;
        this.out = out;
        this.weight = weight;
        this.enabled = enabled;
        this.innovationNumber = innovationNumber;
    }

    public static Synapse of(Integer id, Neuron in, Neuron out, Double weight, boolean enabled, Integer innovationNumber) { // for tests
        return new Synapse(id, in, out, weight, enabled, innovationNumber);
    }

    public static Synapse of(Neuron inNeuron, Neuron outNeuron, Double connectionWeight, boolean enabled, NetworkIdMap networkIdMap) {
        Integer innovationNumber;
        if (networkIdMap.existsSynapseBetweenNeurons(inNeuron.getId(), outNeuron.getId())) {
            innovationNumber = networkIdMap.getFromSynapsesMap(inNeuron.getId(), outNeuron.getId()).getInnovationNumber();
        } else {
            innovationNumber = globalInnovationNumber.incrementAndGet();
        }

        Integer newConnectionId = networkIdMap.getOrCreateNewConnectionId(inNeuron.getId(), outNeuron.getId());
        Synapse synapse = new Synapse(newConnectionId, inNeuron, outNeuron, connectionWeight, enabled, innovationNumber);
        networkIdMap.addToSynapsesMap(inNeuron.getId(), outNeuron.getId(), synapse);


        return synapse;
    }

    public Integer getId() {
        return id;
    }

    public Neuron getIn() {
        return in;
    }

    public Neuron getOut() {
        return out;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getInnovationNumber() {
        return innovationNumber;
    }

    public String toString() {

        return id + " : " + in.getId() + "  -->  " + out.getId() + "  :  " + weight + " " + enabled;
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Synapse)) {
            return false;
        }
        Synapse otherConnections = (Synapse) obj;
        return otherConnections.getId().equals(this.id)
                && otherConnections.getIn().getId().equals(this.in.getId())
                && otherConnections.getOut().getId().equals(this.out.getId())
                && otherConnections.getInnovationNumber().equals(this.getInnovationNumber());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + in.hashCode();
        result = 31 * result + out.hashCode();
        result = 31 * result + (innovationNumber.hashCode());
        return result;
    }
}
