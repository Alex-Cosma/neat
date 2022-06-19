package thesis.genetic.modifiers.extra;

import org.jenetics.util.MSeq;
import thesis.genetic.models.NeuralGene;

/**
 * Created by Alex on 14/07/2016.
 */
public class MutationResult {

    private final MSeq<NeuralGene> genes;
    private final int mutations;

    public MutationResult(MSeq<NeuralGene> genes, int mutations) {
        this.genes = genes;
        this.mutations = mutations;
    }

    public MSeq<NeuralGene> getGenes() {
        return genes;
    }

    public int getMutations() {
        return mutations;
    }
}
