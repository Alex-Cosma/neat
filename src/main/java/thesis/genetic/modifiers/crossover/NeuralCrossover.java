package thesis.genetic.modifiers.crossover;

import org.jenetics.util.MSeq;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.modifiers.extra.MutationResult;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Alex on 15/07/2016.
 */
public class NeuralCrossover extends AbstractNeatCrossover {

    public NeuralCrossover(double probability) {
        super(probability);
    }

    @Override
    public MutationResult crossover(final MSeq<NeuralGene> dominantGenes, final MSeq<NeuralGene> recessiveGenes, boolean equalFitness) {

        Set<Integer> innovationNumbers = new TreeSet<>(Stream.concat(dominantGenes.stream(), recessiveGenes.stream()).map(NeuralGene::getInnovationNumber).collect(Collectors.toSet()));

        Map<Integer, NeuralGene> dominantGeneInnovationMap = getGeneInnovationMap(dominantGenes);
        Map<Integer, NeuralGene> recessiveGeneInnovationMap = getGeneInnovationMap(recessiveGenes);

        List<NeuralGene> crossoverGenes = getCrossoverGenes(equalFitness, innovationNumbers, dominantGeneInnovationMap, recessiveGeneInnovationMap);

        return new MutationResult(MSeq.of(crossoverGenes), crossoverGenes.size());
    }

    private List<NeuralGene> getCrossoverGenes(boolean equalFitness, Set<Integer> innovationNumbersSet, Map<Integer, NeuralGene> dominantGeneInnovationMap, Map<Integer, NeuralGene> recessiveGeneInnovationMap) {
        List<NeuralGene> crossoverGenes = new ArrayList<>();
        Random random = RandomRegistry.getRandom();

        for (int i : innovationNumbersSet) {
            NeuralGene dominantGene = dominantGeneInnovationMap.getOrDefault(i, null);
            NeuralGene recessiveGene = recessiveGeneInnovationMap.getOrDefault(i, null);

            if (dominantGene != null && recessiveGene != null) {
                crossoverGenes.add(recessiveGene.getAllele().isEnabled() ? (random.nextBoolean() ? recessiveGene : dominantGene) : dominantGene);
            } else {
                if (equalFitness) {
                    if ((recessiveGene != null) && random.nextBoolean()) {
                        crossoverGenes.add(recessiveGene);
                    } else if (dominantGene != null) {
                        crossoverGenes.add(dominantGene);
                    }
                } else if (dominantGene != null) {
                    crossoverGenes.add(dominantGene);
                }
            }
        }
        return crossoverGenes;
    }

    private Map<Integer, NeuralGene> getGeneInnovationMap(MSeq<NeuralGene> genes) {
        Map<Integer, NeuralGene> dominantGeneInnovationMap = new HashMap<>();
        genes.forEach(gene -> dominantGeneInnovationMap.put(gene.getInnovationNumber(), gene));
        return dominantGeneInnovationMap;
    }

}
