package thesis.statistics;

import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.stat.DoubleMomentStatistics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Alex on 27/08/2016.
 */
public class NeuralEvolutionStatistics implements Consumer<EvolutionResult<?, Double>> {

    private final EvolutionStatistics<Double, DoubleMomentStatistics> baseJeneticsStatistics = EvolutionStatistics.ofNumber();
    private final Map<Long, Double> fitnessPerGenerationMap;

    private final Map<Long, Phenotype> bestIndividualPerGenerationMap;

    public NeuralEvolutionStatistics() {
        fitnessPerGenerationMap = Collections.synchronizedMap(new HashMap<>());
        bestIndividualPerGenerationMap = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public void accept(EvolutionResult<?, Double> neuralGeneDoubleEvolutionResult) {
        baseJeneticsStatistics.accept(neuralGeneDoubleEvolutionResult);
        fitnessPerGenerationMap.put(neuralGeneDoubleEvolutionResult.getGeneration(), neuralGeneDoubleEvolutionResult.getBestFitness());
        bestIndividualPerGenerationMap.put(neuralGeneDoubleEvolutionResult.getGeneration(), neuralGeneDoubleEvolutionResult.getBestPhenotype());
    }

    @Override
    public Consumer<EvolutionResult<?, Double>> andThen(Consumer<? super EvolutionResult<?, Double>> after) {
        return baseJeneticsStatistics.andThen(after);
    }


    public EvolutionStatistics<Double, DoubleMomentStatistics> getBaseJeneticsStatistics() {
        return baseJeneticsStatistics;
    }

    public Map<Long, Double> getFitnessPerGenerationMap() {
        return fitnessPerGenerationMap;
    }

    public Map<Long, Phenotype> getBestIndividualPerGenerationMap() {
        return bestIndividualPerGenerationMap;
    }

    public void combineWith(NeuralEvolutionStatistics st) {

    }
}
