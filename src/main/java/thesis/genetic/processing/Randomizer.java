package thesis.genetic.processing;

import org.jenetics.util.RandomRegistry;

import java.util.Random;

/**
 * Created by Alex on 28/07/2016.
 */
public class Randomizer {

    public static double randomWeight() {
        Random random = RandomRegistry.getRandom();
        return random.nextDouble() * 10 - 5;
    }

    public static double initialConnectionWeight() {
        return 0;
    }

}
