package thesis.neural.networks;

import org.deeplearning4j.nn.api.Layer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import thesis.game.model.GameState;

import java.util.List;

/**
 * Created by Alex on 23/07/2016.
 */
public class ConsecutiveLayerNetwork implements Network {

    private final MultiLayerNetwork net;

    public ConsecutiveLayerNetwork(List<INDArray> synapseWeights) {
        net = buildNetwork(synapseWeights);
        net.init();

        Layer[] layers = net.getLayers();
        for (int i = 0; i < layers.length; i++) {
            Layer layer = layers[i];
            layer.setParam("W", synapseWeights.get(i));
        }
    }

    private MultiLayerNetwork buildNetwork(List<INDArray> synapseWeights) {
        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();

        int nrOfLayers = synapseWeights.size();

        NeuralNetConfiguration.ListBuilder listBuilder = builder.list();

        for (int layer = 0; layer < nrOfLayers - 1; layer++) {
            listBuilder.layer(layer, buildLayer(synapseWeights.get(layer)));
        }

        OutputLayer.Builder outputLayerBuilder = buildOutputLayer(synapseWeights);
        listBuilder.layer(nrOfLayers - 1, outputLayerBuilder.build());

        MultiLayerConfiguration conf = listBuilder.build();
        return new MultiLayerNetwork(conf);
    }

    private DenseLayer buildLayer(INDArray layerWeights) {
        DenseLayer.Builder layerBuilder = new DenseLayer.Builder();
        layerBuilder.nIn(layerWeights.rows());
        layerBuilder.nOut(layerWeights.columns());
        return layerBuilder.build();
    }

    private OutputLayer.Builder buildOutputLayer(List<INDArray> synapseWeights) {
        OutputLayer.Builder outputLayerBuilder = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD);
        outputLayerBuilder.nIn(synapseWeights.get(synapseWeights.size() - 1).rows());
        outputLayerBuilder.nOut(synapseWeights.get(synapseWeights.size() - 1).columns());
        return outputLayerBuilder;
    }

    public INDArray feed(GameState currentState) {
        return net.output(currentState.getNormalizedState());
    }
}
