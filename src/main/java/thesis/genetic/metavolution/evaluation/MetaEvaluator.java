package thesis.genetic.metavolution.evaluation;

import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.stat.DoubleMomentStatistics;
import thesis.configuration.genetic.Configuration;
import thesis.genetic.evolvers.Evolver;
import thesis.genetic.models.NeuralGene;
import thesis.statistics.NeuralEvolutionStatistics;
import thesis.statistics.NeuralEvolutionStatisticsService;

import static java.lang.Math.sqrt;

/**
 * Created by Alex on 03/08/2016.
 */
public class MetaEvaluator {

    private static final Double ALPHA = 0.90;
    private static final Double BETA = 0.08;
    private static final Double GAMMA = 0.02;

    private final Evolver evolver;

    public MetaEvaluator(Evolver evolver) {
        this.evolver = evolver;
    }

    public Double eval(Configuration configuration) {
        NeuralEvolutionStatisticsService neuralEvolutionStatisticsService = new NeuralEvolutionStatisticsService();


        for(int i=0; i<1; i++) {
            Phenotype<NeuralGene, Double> evolve = evolver.evolve(configuration);
            neuralEvolutionStatisticsService.addNeuralEvolutionStatistics(evolver.getStatistics());
        }

//        return -1d * genSum;
        return extractMetaFitness(neuralEvolutionStatisticsService);
    }

    private Double extractMetaFitness(NeuralEvolutionStatisticsService stats) {

        return -1 * sqrt(stats.getAverageFitnessVariance()) * ALPHA
                +
                stats.getAverageFitness() * BETA
                +
                -1 * stats.getAveragePhenotypeAge() * GAMMA;
    }

}
