package thesis.genetic.selectors;

import org.jenetics.Optimize;
import org.jenetics.Phenotype;
import org.jenetics.Population;
import org.jenetics.Selector;
import thesis.genetic.models.NeuralGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 30/07/2016.
 */
public class ElitismSelector implements Selector<NeuralGene, Double> {
    @Override
    public Population<NeuralGene, Double> select(Population<NeuralGene, Double> population, int count, Optimize opt) {

        List<Phenotype<NeuralGene, Double>> theElite = new ArrayList<>();

        theElite.addAll(population);
        theElite.sort((i1, i2) -> Double.compare(i2.getFitness(), i1.getFitness()));
        theElite = theElite.subList(0, Math.min(count, theElite.size() - 1));

        final Population<NeuralGene, Double> selection = new Population<>(theElite.size());
        selection.addAll(theElite);

        return selection;
    }
}
