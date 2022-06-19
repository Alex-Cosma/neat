package thesis.genetic.models;

import org.jenetics.Phenotype;
import thesis.configuration.genetic.Configuration;
import thesis.genetic.models.extra.StalenessTracker;
import thesis.genetic.processing.GenotypeDistanceProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Alex on 18/07/2016.
 */
public class SpeciesPool extends StalenessTracker {

    private final Configuration configuration;
    private final GenotypeDistanceProcessor genotypeDistanceProcessor;
    private final Collection<Species> species = Collections.synchronizedSet(new HashSet<>());

    public SpeciesPool(Configuration configuration) {
        super(configuration.getMaxStaleness());
        this.configuration = configuration;
        genotypeDistanceProcessor = new GenotypeDistanceProcessor(
                configuration.getCompatibilityExcessCoefficient(),
                configuration.getCompatibilityDisjointCoefficient(),
                configuration.getCompatibilityCommonWeightCoefficient()
        );
    }

    public synchronized void addToSpeciesPool(Phenotype<NeuralGene, Double> individual) {
        double minDistance = Double.MAX_VALUE;
        Species minDistanceSpecies = null;

        for (Species s : species) {
            Phenotype<NeuralGene, Double> representative = s.getRepresentative();
            double distance = genotypeDistanceProcessor.distanceBetween(representative, individual);
            if (distance < configuration.getDefaultSpeciationThreshold() && distance < minDistance) {
                minDistance = distance;
                minDistanceSpecies = s;
            }
        }
        if (minDistanceSpecies != null) {
            minDistanceSpecies.addIndividual(individual);
        } else {
            species.add(new Species(Collections.singleton(individual), configuration.getMaxStaleness()));
        }
    }

    public void cullSpecies(boolean cullToOne) {
        species.stream().parallel().forEach(s -> s.dropAllBut(cullToOne ? 1d : s.getIndividualsCount() == 1 ? 1 : s.getIndividualsCount() / 2));
    }

    public long getIndividualsCount() {
        return species.stream().parallel().mapToLong(Species::getIndividualsCount).sum();
    }

    public Collection<Species> getSpecies() {
        return this.species;
    }

    public void removeStaleSpecies() {
        species.forEach(Species::rectifyStaleness);
        species.removeIf(Species::isStale);
    }

    public void removeSpeciesIfAllStale() {
        this.adjustedFitness = getTotalFitness();
        rectifyStaleness();
        if (isStale()) {
            ArrayList<Species> sortedSpecies = new ArrayList<>();
            sortedSpecies.addAll(species);
            Collections.sort(sortedSpecies, (s1, s2) -> Double.compare(s2.getAdjustedFitness(), s1.getAdjustedFitness()));
            species.clear();
            if (sortedSpecies.size() > 1) {
                species.add(sortedSpecies.get(0));
                species.add(sortedSpecies.get(1));
            } else if (sortedSpecies.size() == 1) {
                species.add(sortedSpecies.get(0));
            }
            staleness = 0;
        }
    }

    public void calculateAdjustedFitnessForSpecies() {
        species.stream().parallel().forEach(Species::calculateAdjustedFitness);
    }

    public double getTotalFitness() {
        return species.stream().parallel().mapToDouble(Species::getAdjustedFitness).sum();
    }
}
