package thesis.genetic.selectors;

import org.jenetics.Optimize;
import org.jenetics.Phenotype;
import org.jenetics.Population;
import org.jenetics.Selector;
import org.jenetics.util.RandomRegistry;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.models.Species;
import thesis.genetic.models.SpeciesPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 18/07/2016.
 */
public class OffspringSelector implements Selector<NeuralGene, Double> {

    private final SpeciesPool speciesPool;

    public OffspringSelector(SpeciesPool speciesPool) {
        this.speciesPool = speciesPool;
    }

    @Override
    public Population<NeuralGene, Double> select(Population<NeuralGene, Double> population, int count, Optimize opt) {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("Generation: " + population.stream().mapToLong(Phenotype::getGeneration).max().orElse(0));
        System.out.println("Total Pop: " + population.size());
        System.out.println("Max in offspring " + count);

        speciesPool.calculateAdjustedFitnessForSpecies();
        speciesPool.removeStaleSpecies();
        speciesPool.removeSpeciesIfAllStale();

        population.stream().forEach(speciesPool::addToSpeciesPool);
        System.out.println("Pop in species pool: " + speciesPool.getIndividualsCount());
        Collection<Species> species = speciesPool.getSpecies();
        System.out.println("# species: " + species.size());

        double totalFitnessOfAllSpecies = speciesPool.getTotalFitness();

        List<Phenotype<NeuralGene, Double>> offspring = new ArrayList<>();
        for (Species s : species) {
            Double percentage = Math.min(s.getAdjustedFitness() / totalFitnessOfAllSpecies, 100);
            Double keepCount = percentage * count;

            offspring.addAll(s.getFirstKBestIndividuals(keepCount));
        }

        speciesPool.cullSpecies(true);

        Random random = RandomRegistry.getRandom();

        if (species.size() > 0) {
            while (offspring.size() < count) {
                int randomSpecies = random.nextInt(species.size());
                Phenotype<NeuralGene, Double> representative = ((Species) species.toArray()[randomSpecies]).getRepresentative();
                offspring.add(representative.newInstance(representative.getGenotype()));
            }
        } else {
            offspring.addAll(population);
        }

        final Population<NeuralGene, Double> selection = new Population<>(offspring.size());
        selection.addAll(offspring.subList(0, Math.min(offspring.size(), count)));

        System.out.println("MAX FITNESS: " + population.stream().mapToDouble(Phenotype::getFitness).max().orElse(0d));
        System.out.println("TOTAL FITNESS " + population.stream().mapToDouble(Phenotype::getFitness).sum());
        return selection;
    }
}