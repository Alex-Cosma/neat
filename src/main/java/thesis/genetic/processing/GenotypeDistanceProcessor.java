package thesis.genetic.processing;

import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import org.jenetics.util.Seq;
import thesis.genetic.models.NeuralGene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 17/07/2016.
 */
public class GenotypeDistanceProcessor {

    private final double COMPATIBILITY_EXCESS_COEFFICIENT;
    private final double COMPATIBILITY_DISJOINT_COEFFICIENT;
    private final double COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT;

    public GenotypeDistanceProcessor(double compatibilityExcessCoefficient, double compatibilityDisjointCoefficient, double compatibilityCommonWeightCoefficient) {

        this.COMPATIBILITY_EXCESS_COEFFICIENT = compatibilityExcessCoefficient;
        this.COMPATIBILITY_DISJOINT_COEFFICIENT = compatibilityDisjointCoefficient;
        this.COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT = compatibilityCommonWeightCoefficient;
    }

    public double distanceBetween(Phenotype<NeuralGene, Double> representative, Phenotype<NeuralGene, Double> target) {
        return distanceBetween(representative.getGenotype(), target.getGenotype());
    }

    public double distanceBetween(Genotype<NeuralGene> representative, Genotype<NeuralGene> target) {

        Seq<NeuralGene> representativeGenes = representative.getChromosome().toSeq();
        Seq<NeuralGene> targetGenes = target.getChromosome().toSeq();

        List<NeuralGene> representativeUnmatchedGenes = new ArrayList<>(representativeGenes.asList());
        representativeUnmatchedGenes.removeAll(targetGenes.asList());

        List<NeuralGene> targetUnmatchedGenes = new ArrayList<>(targetGenes.asList());
        targetUnmatchedGenes.removeAll(representativeGenes.asList());


        int representativeMaxInnovationId = extractMaximumInnovationId(representativeGenes);
        int targetMaxInnovationId = extractMaximumInnovationId(targetGenes);

        List<NeuralGene> excessGenes = (targetMaxInnovationId > representativeMaxInnovationId) ?
                extractExcessGenes(targetUnmatchedGenes, representativeMaxInnovationId) :
                extractExcessGenes(representativeUnmatchedGenes, targetMaxInnovationId);

        List<NeuralGene> disjointGenes = new ArrayList<>(targetUnmatchedGenes);
        disjointGenes.addAll(representativeUnmatchedGenes);
        disjointGenes.removeAll(excessGenes);

        List<NeuralGene> representativeCommonGenes = new ArrayList<>(representativeGenes.asList());
        representativeCommonGenes.retainAll(targetGenes.asList());
        List<NeuralGene> targetCommonGenes = new ArrayList<>(targetGenes.asList());
        targetCommonGenes.retainAll(representativeGenes.asList());

        // sanity check!
        if (representativeCommonGenes.size() != targetCommonGenes.size()) {
            throw new IllegalStateException("Sizes of representative common genes and target common genes differ!");
        }

        double averageCommonDiff;
        int numComparableCommonSynapses = 0;
        double totalCommonDiff = 0.0;

        // sanity sort
        Collections.sort(representativeCommonGenes, (o1, o2) -> Integer.compare(o1.getInnovationNumber(), o2.getInnovationNumber()));
        Collections.sort(targetCommonGenes, (o1, o2) -> Integer.compare(o1.getInnovationNumber(), o2.getInnovationNumber()));

        for (int i = 0; i < representativeCommonGenes.size(); i++) {
            //TODO: too much code, move in named function
            NeuralGene representativeGene = representativeCommonGenes.get(i);
            NeuralGene targetGene = targetCommonGenes.get(i);

            // sanity check 2
            if (!representativeGene.getInnovationNumber().equals(targetGene.getInnovationNumber())) {
                throw new IllegalStateException("Genes do not have matching innovation id!");
            }

            double distance = Math.abs(representativeGene.getAllele().getWeight() - (targetGene.getAllele()).getWeight());
            if (totalCommonDiff + distance > Double.MAX_VALUE)
                totalCommonDiff = Double.MAX_VALUE;
            else
                totalCommonDiff += distance;
            ++numComparableCommonSynapses;
        }
        averageCommonDiff = totalCommonDiff / numComparableCommonSynapses;

        long maxChromosomeSize = Math.max(representativeGenes.size(), targetGenes.size());
        double result = 0.0d;
        if (maxChromosomeSize > 0) {
            result = ((COMPATIBILITY_EXCESS_COEFFICIENT * excessGenes.size()) / maxChromosomeSize)
                    + ((COMPATIBILITY_DISJOINT_COEFFICIENT * disjointGenes.size()) / maxChromosomeSize)
                    + (COMPATIBILITY_COMMON_WEIGHT_COEFFICIENT * averageCommonDiff);
        }

        return result;
    }

    private int extractMaximumInnovationId(Seq<NeuralGene> individual) {
        return individual.stream().mapToInt(NeuralGene::getInnovationNumber).max().getAsInt();
    }


    private List<NeuralGene> extractExcessGenes(List<NeuralGene> unmatchedGenes, Integer maxInnovationId) {
        return unmatchedGenes.stream().filter(it -> it.getInnovationNumber() > maxInnovationId).collect(Collectors.toList());
    }
}
