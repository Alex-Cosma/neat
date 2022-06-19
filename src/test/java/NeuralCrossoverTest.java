import org.jenetics.util.MSeq;
import org.junit.Test;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.crossover.NeuralCrossover;
import thesis.genetic.modifiers.extra.MutationResult;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alex on 16/07/2016.
 */
public class NeuralCrossoverTest extends AbstractNeatTest {

    @Test
    public void testNeatCrossoverSameFitness() {
        NeuralCrossover neuralCrossover = new NeuralCrossover(0.5);

        MSeq<NeuralGene> dominantGenes = MSeq.of(createDominantGeneSequence());
        MSeq<NeuralGene> recessiveGenes = MSeq.of(createRecessiveGeneSequence());

        MutationResult crossover = neuralCrossover.crossover(dominantGenes, recessiveGenes, true);
        MSeq<NeuralGene> crossoverGenes = crossover.getGenes();

        for (int i = 0; i < 5; i++) {
            assertEquals(crossoverGenes.get(i).getInnovationNumber(), Integer.valueOf(i + 1));
        }
    }

    @Test
    public void testNeatCrossoverDifferentFitness() {
        NeuralCrossover neuralCrossover = new NeuralCrossover(0.5);

        MSeq<NeuralGene> dominantGenes = MSeq.of(createDominantGeneSequence());
        MSeq<NeuralGene> recessiveGenes = MSeq.of(createRecessiveGeneSequence());

        MutationResult crossover = neuralCrossover.crossover(dominantGenes, recessiveGenes, false);
        MSeq<NeuralGene> crossoverGenes = crossover.getGenes();

        for (int i = 0; i < 7; i++) {
            assertEquals(crossoverGenes.get(i).getInnovationNumber(), Integer.valueOf(i + 1));
        }
        assertEquals(crossoverGenes.get(7).getInnovationNumber(), Integer.valueOf(9));
        assertEquals(crossoverGenes.get(8).getInnovationNumber(), Integer.valueOf(10));

        crossover = neuralCrossover.crossover(recessiveGenes, dominantGenes, false);
        crossoverGenes = crossover.getGenes();

        for (int i = 0; i < 5; i++) {
            assertEquals(crossoverGenes.get(i).getInnovationNumber(), Integer.valueOf(i + 1));
        }
        assertEquals(crossoverGenes.get(5).getInnovationNumber(), Integer.valueOf(8));
    }


}
