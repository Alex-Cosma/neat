package thesis.genetic.models;

import org.jenetics.Phenotype;
import thesis.genetic.models.extra.StalenessTracker;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Alex on 17/07/2016.
 */
public class Species extends StalenessTracker {

    private final List<Phenotype<NeuralGene, Double>> individuals = new CopyOnWriteArrayList<>();

    public Species(Collection<Phenotype<NeuralGene, Double>> individuals, int maxStaleness) {
        super(maxStaleness);
        this.individuals.addAll(individuals);
    }


    public void addIndividual(Phenotype<NeuralGene, Double> individual) {
        individuals.add(individual);
    }

    public Collection<Phenotype<NeuralGene, Double>> getFirstKBestIndividuals(Double keepCount) {
        if (keepCount.isNaN() || keepCount.equals(0d)) { // should not drop all!
            return individuals;
        } else {
            return individuals.subList(0, Math.min(individuals.size(), keepCount.intValue() < 1 ? 1 : keepCount.intValue()));
        }

    }

    public void dropAllBut(Double keepCount) {
        if (keepCount.isNaN() || keepCount.equals(0d)) { // should not drop all!
            return;
        }
        int keepCnt = (int) Math.ceil(keepCount);
        if (keepCount <= individuals.size()) {
            List<Phenotype<NeuralGene, Double>> keptElements = new CopyOnWriteArrayList<>();
            Iterator<Phenotype<NeuralGene, Double>> i = individuals.iterator();

            while (keepCnt > 0 && i.hasNext()) {
                keptElements.add(i.next());
                keepCnt -= 1;
            }

            individuals.clear();
            individuals.addAll(keptElements);
        }
    }

    public Phenotype<NeuralGene, Double> getRepresentative() {
        individuals.sort((i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
        return individuals.iterator().next();
    }

    public List<Phenotype<NeuralGene, Double>> getIndividuals() {
        return this.individuals;
    }

    public long getIndividualsCount() {
        return individuals.size();
    }

    public void calculateAdjustedFitness() {
        this.adjustedFitness = individuals.stream().parallel().mapToDouble(Phenotype::getFitness).average().orElse(0d);
    }

    public double getAdjustedFitness() {
        return adjustedFitness;
    }
}
