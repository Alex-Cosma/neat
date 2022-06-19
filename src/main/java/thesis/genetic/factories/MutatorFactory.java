package thesis.genetic.factories;

import org.jenetics.Alterer;
import thesis.configuration.genetic.Configuration;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.crossover.NeuralCrossover;
import thesis.genetic.modifiers.mutators.*;
import thesis.genetic.processing.NetworkIdMap;

/**
 * Created by Alex on 02/08/2016.
 */
public class MutatorFactory {

    private Configuration configuration;

    public MutatorFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public Alterer<NeuralGene, Double> getAddSynapseMutator(NetworkIdMap networkIdMap) {
        return new NeuralAddSynapseMutator(configuration.getAddSynapseMutationProbability(), configuration.getMaxTriesAddSynapseMutation(), networkIdMap);
    }

    public Alterer<NeuralGene, Double> getAddNodeMutator(NetworkIdMap networkIdMap) {
        return new NeuralAddNeuronMutator(configuration.getAddNodeMutationProbability(), networkIdMap);
    }

    public Alterer<NeuralGene, Double> getDisableSynapseMutator() {
        return new NeuralDisableSynapseMutator(configuration.getDisableSynapseMutationProbability());
    }

    public Alterer<NeuralGene, Double> getEnableSynapseMutator() {
        return new NeuralEnableSynapseMutator(configuration.getEnableSynapseMutationProbability());
    }

    public Alterer<NeuralGene, Double> getCrossoverAlterer() {
        return new NeuralCrossover(configuration.getCrossoverProbability());
    }

    public Alterer<NeuralGene, Double> getModifyWeightMutator() {
        return new NeuralModifyWeightMutator(configuration.getModifyWeightMutationProbability(), configuration.getUniformPerturbWeightProbability(), configuration.getPerturbWeightStep());
    }


}
