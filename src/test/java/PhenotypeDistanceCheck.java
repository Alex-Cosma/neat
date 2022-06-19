import org.jenetics.Genotype;
import org.jenetics.util.MSeq;
import org.junit.Test;
import thesis.configuration.factory.ConfigurationFactory;
import thesis.configuration.genetic.Configuration;
import thesis.genetic.models.NeuralChromosome;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.processing.GenotypeDistanceProcessor;
import thesis.genetic.processing.NetworkIdMap;

/**
 * Created by Alex on 17/07/2016.
 */
public class PhenotypeDistanceCheck extends AbstractNeatTest {

    @Test
    public void testDistance() {
        Configuration configuration = ConfigurationFactory.getBaseConfiguration();
        NetworkIdMap networkIdMap = new NetworkIdMap(0, 0);
        MSeq<NeuralGene> dominantGenes = MSeq.of(createDominantGeneSequence());
        MSeq<NeuralGene> recessiveGenes = MSeq.of(createRecessiveGeneSequence());

        Genotype<NeuralGene> dominantGenotype = Genotype.of(NeuralChromosome.of(dominantGenes.toISeq(), networkIdMap));
        Genotype<NeuralGene> recessiveGenotype = Genotype.of(NeuralChromosome.of(recessiveGenes.toISeq(), networkIdMap));
        GenotypeDistanceProcessor genotypeDistanceProcessor = new GenotypeDistanceProcessor(configuration.getCompatibilityExcessCoefficient(),
                configuration.getCompatibilityDisjointCoefficient(),
                configuration.getCompatibilityCommonWeightCoefficient());

        double v = genotypeDistanceProcessor.distanceBetween(dominantGenotype, recessiveGenotype);

        assert v > 0.5 && v < 1.5;
    }

}
