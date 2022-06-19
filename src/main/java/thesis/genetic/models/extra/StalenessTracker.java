package thesis.genetic.models.extra;

/**
 * Created by Alex on 8/16/2016.
 */
public class StalenessTracker {

    private final int MAX_STALENESS;
    protected double previousAdjustedFitness = 0;
    protected double adjustedFitness = 0;
    protected int staleness;

    public StalenessTracker(int maxStaleness) {
        this.MAX_STALENESS = maxStaleness;
    }

    public boolean isStale() {
        return staleness >= MAX_STALENESS;
    }

    public void rectifyStaleness() {
        if (adjustedFitness <= previousAdjustedFitness) {
            staleness += 1;
        } else {
            staleness = 0;
            previousAdjustedFitness = adjustedFitness;
        }
    }
}
