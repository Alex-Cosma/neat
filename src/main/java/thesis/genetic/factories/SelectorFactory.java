package thesis.genetic.factories;

import org.jenetics.Selector;
import thesis.genetic.models.NeuralGene;
import thesis.genetic.models.SpeciesPool;
import thesis.genetic.selectors.ElitismSelector;
import thesis.genetic.selectors.OffspringSelector;

/**
 * Created by Alex on 02/08/2016.
 */
public class SelectorFactory {

    private final SpeciesPool speciesPool;

    public SelectorFactory(SpeciesPool speciesPool) {
        this.speciesPool = speciesPool;
    }

    public Selector<NeuralGene, Double> getOffspringSelector() {
        return new OffspringSelector(speciesPool);
    }

    public Selector<NeuralGene, Double> getElitismSelector() {
        return new ElitismSelector();
    }

}
