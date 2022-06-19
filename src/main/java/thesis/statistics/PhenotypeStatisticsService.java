package thesis.statistics;

import org.jenetics.Phenotype;
import thesis.genetic.models.NeuralGene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Alex on 8/13/2016.
 */
public class PhenotypeStatisticsService {

    private final Collection<PhenotypeStatistics> phenotypeStatistics;

    public PhenotypeStatisticsService() {
        phenotypeStatistics = Collections.synchronizedList(new ArrayList<>());
    }

    public void addPhenotypeStatistics(Phenotype<NeuralGene, Double> phenotype) {
        phenotypeStatistics.add(new PhenotypeStatistics(phenotype));
    }

    public Double getAverageFitness() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getFitness).average().orElse(0d);
    }

    public Double getAverageGeneration() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getGeneration).average().orElse(0d);
    }

    public Double getAverageNrNeurons() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrNeurons).average().orElse(0d);
    }

    public Double getAverageNrSynapses() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrSynapses).average().orElse(0d);
    }

    public Double getMinimumFitness() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getFitness).min().orElse(0d);
    }

    public Double getMinimumGeneration() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getGeneration).min().orElse(0d);
    }

    public Double getMinimumNrNeurons() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrNeurons).min().orElse(0d);
    }

    public Double getMinimumNrSynapses() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrSynapses).min().orElse(0d);
    }

    public Double getMaximumFitness() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getFitness).max().orElse(0d);
    }

    public Double getMaximumGeneration() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getGeneration).max().orElse(0d);
    }

    public Double getMaximumNrNeurons() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrNeurons).max().orElse(0d);
    }

    public Double getMaximumNrSynapses() {
        return phenotypeStatistics.stream().mapToDouble(PhenotypeStatistics::getNrSynapses).max().orElse(0d);
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append(String.format("Minimum Fitness = %f\n", getMinimumFitness()));
        stats.append(String.format("Average Fitness = %f\n", getAverageFitness()));
        stats.append(String.format("Maximum Fitness = %f\n", getMaximumFitness()));

        stats.append(String.format("Minimum Generation = %f\n", getMinimumGeneration()));
        stats.append(String.format("Average Generation = %f\n", getAverageGeneration()));
        stats.append(String.format("Maximum Generation = %f\n", getMaximumGeneration()));

        stats.append(String.format("Minimum NrNeurons = %f\n", getMinimumNrNeurons()));
        stats.append(String.format("Average NrNeurons = %f\n", getAverageNrNeurons()));
        stats.append(String.format("Maximum NrNeurons = %f\n", getMaximumNrNeurons()));

        stats.append(String.format("Minimum NrSynapses = %f\n", getMinimumNrSynapses()));
        stats.append(String.format("Average NrSynapses = %f\n", getAverageNrSynapses()));
        stats.append(String.format("Maximum NrSynapses = %f\n", getMaximumNrSynapses()));

        return stats.toString();
    }
}
