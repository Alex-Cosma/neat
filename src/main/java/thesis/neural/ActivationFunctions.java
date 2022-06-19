package thesis.neural;

/**
 * Created by Alex on 28/07/2016.
 */
public class ActivationFunctions {
    public static double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }

    public static double sigmoidNeat(double x) {
        return (1 / (1 + Math.pow(Math.E, (-4.9 * x))));
    }


}
