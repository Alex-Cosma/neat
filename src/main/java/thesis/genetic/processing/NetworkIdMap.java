package thesis.genetic.processing;

import org.deeplearning4j.berkeley.Pair;
import thesis.neural.models.Neuron;
import thesis.neural.models.Synapse;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex on 19/07/2016.
 */
public class NetworkIdMap implements Serializable {

    private final Map<Pair<Integer, Integer>, Integer> synapsesIdMap = new ConcurrentHashMap<>();
    private final Map<Pair<Integer, Integer>, Integer> neuronBetweenNeuronsIdMap = new ConcurrentHashMap<>();
    private final Map<Pair<Integer, Integer>, Synapse> synapsesMap = new ConcurrentHashMap<>();
    private final AtomicInteger synapsesIdCounter = new AtomicInteger(0);
    private final AtomicInteger neuronsIdCounter;

    public NetworkIdMap(Integer nrInputNeurons, Integer nrOutputNeurons) {
        neuronsIdCounter = new AtomicInteger(nrInputNeurons + nrOutputNeurons + 1); // + 1 = bias
    }

    public Integer getOrCreateNewConnectionId(Integer neuronInId, Integer neuronOutId) {
        return getNewId(synapsesIdMap, synapsesIdCounter, neuronInId, neuronOutId);
    }

    public Pair<Integer, Integer> getNewNodeBetweenPairIdAndLayer(Neuron neuronIn, Neuron neuronOut) {
        Integer newNeuronId = getNewId(neuronBetweenNeuronsIdMap, neuronsIdCounter, neuronIn.getId(), neuronOut.getId());
        Integer newNeuronLayer = neuronIn.getLayer() + 1;

        return Pair.makePair(newNeuronId, newNeuronLayer);
    }

    private synchronized Integer getNewId(Map<Pair<Integer, Integer>, Integer> idMap, AtomicInteger counter, Integer in, Integer out) {
        Pair<Integer, Integer> idPair = Pair.makePair(in, out);
        if (!idMap.containsKey(idPair)) {
            int newId = counter.incrementAndGet();
            idMap.put(idPair, newId);
            return newId;
        } else {
            return idMap.get(idPair);
        }
    }

    public void addToSynapsesMap(Integer neuronInId, Integer neuronOutId, Synapse synapse) {
        synapsesMap.put(Pair.makePair(neuronInId, neuronOutId), synapse);
    }

    public Synapse getFromSynapsesMap(Integer neuronInId, Integer neuronOutId) {
        return synapsesMap.get(Pair.makePair(neuronInId, neuronOutId));
    }

    public boolean existsSynapseBetweenNeurons(Integer neuronInId, Integer neuronOutId) {
        return synapsesMap.containsKey(Pair.makePair(neuronInId, neuronOutId));
    }

}
