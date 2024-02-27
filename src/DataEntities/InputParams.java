package DataEntities;

public class InputParams {
    public int maxPageNoLimit;
    public int totalFramesQty;
    public int workingSetSize;
    public int timerStep;
    public boolean printItems;
    public boolean printWorkingSetIterations;

    @Override
    public String toString() {
        return "InputParams{" +
                "maxPageNoLimit=" + maxPageNoLimit +
                ", totalFramesQty=" + totalFramesQty +
                ", workingSetSize=" + workingSetSize +
                ", timerStep=" + timerStep +
                ", printItems=" + printItems +
                ", printWorkingSetIterations=" + printWorkingSetIterations +
                '}';
    }
}
