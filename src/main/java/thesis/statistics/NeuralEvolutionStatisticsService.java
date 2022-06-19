package thesis.statistics;

import org.jenetics.Phenotype;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alex on 27/08/2016.
 */
public class NeuralEvolutionStatisticsService {

    private final Collection<NeuralEvolutionStatistics> neuralEvolutionStatistics;

    public NeuralEvolutionStatisticsService() {
        neuralEvolutionStatistics = Collections.synchronizedList(new ArrayList<>());
    }

    public void addNeuralEvolutionStatistics(NeuralEvolutionStatistics statistics) {
        neuralEvolutionStatistics.add(statistics);
    }

    public void printSeparateMaps() {
        neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getFitnessPerGenerationMap).forEach(System.out::println);
    }

    public Map<Long, Double> getAverageFitnessPerGenerationMap() {
        return neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getFitnessPerGenerationMap)
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(
                                        Map.Entry::getValue,
                                        Collectors.toList()
                                )
                        )
                ).entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey, entry -> entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)
                ));
    }

    public void printAverageFitnessPerGenerationMap() {
        System.out.println(getAverageFitnessPerGenerationMap());
    }

    public void printAverageFitnessPerGenerationMapCsv() {
        Map<Long, Double> averageFitnessPerGenerationMap = getAverageFitnessPerGenerationMap();
        averageFitnessPerGenerationMap.entrySet().forEach(
                e -> System.out.println(e.getKey() + "," + e.getValue())
        );
    }

    public void printAverageFitnessPerGenerationMapCsv(Double normalizationFactor) {
        Map<Long, Double> averageFitnessPerGenerationMap = getAverageFitnessPerGenerationMap();
        averageFitnessPerGenerationMap.entrySet().forEach(
                e -> System.out.println(e.getKey() + "," + e.getValue() / normalizationFactor)
        );
    }

    public List<Map<Long, Phenotype>> getBestIndividualPerGenerationList() {
        return neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getBestIndividualPerGenerationMap).collect(Collectors.toList());
    }

    public Double getAverageFitnessVariance() {
        return neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getBaseJeneticsStatistics).mapToDouble(t -> t.getFitness().getVariance()).average().orElse(0d);
    }

    public Double getAverageFitness() {
        return neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getBaseJeneticsStatistics).mapToDouble(t -> t.getFitness().getMean()).average().orElse(0d);
    }

    public Double getAveragePhenotypeAge() {
        return neuralEvolutionStatistics.stream().map(NeuralEvolutionStatistics::getBaseJeneticsStatistics).mapToDouble(t -> t.getPhenotypeAge().getMean()).average().orElse(0d);
    }


}
