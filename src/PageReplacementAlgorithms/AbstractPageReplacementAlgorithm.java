package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class AbstractPageReplacementAlgorithm implements PageReplacementAlgorithm {
    protected List<ProcessAndPageData> items;
    protected int framesQty;
    protected int totalPageFaults;
    private Queue<Integer> workingSetQueue;
    private HashSet<Integer> workingSet;
    private int workingSetSize;


    // Used for the simple mode without the working set
    protected AbstractPageReplacementAlgorithm(ProcessData process) {
        if (process == null)
            throw new NullPointerException("The process should not be null.");
        if (process.framesQty < 0)
            throw new IllegalArgumentException("The process.framesQty should be non-negative.");

        items = process.getItems();
        framesQty = process.framesQty;
        workingSetSize = 0; // No need to use the working set

        init();
    }

    // Used for the mode with the working set
    protected AbstractPageReplacementAlgorithm(List<ProcessAndPageData> items, int framesQty, InputParams params) {
        if (items == null)
            throw new NullPointerException("The items should not be null.");
        if (framesQty < 0)
            throw new IllegalArgumentException("The framesQty should be non-negative.");
        if (params == null)
            throw new NullPointerException("The params should not be null.");
        if (params.workingSetSize <= 0)
            throw new IllegalArgumentException("The params.workingSetSize should be a positive integer number.");

        this.items = items;
        this.framesQty = framesQty;
        this.workingSetSize = params.workingSetSize;

        init();
    }

    protected void init() {
        totalPageFaults = 0;

        if (workingSetSize == 0)
            return;

        workingSetQueue = new LinkedList<>();
        workingSet = new HashSet<>(workingSetSize);
    }

    protected void addToWorkingSet(int pageNo) {
        if (workingSetSize == 0)
            return;

        if (workingSet.size() >= workingSetSize && !workingSet.contains(pageNo)) {
            int pageToRemove = workingSetQueue.poll();
            workingSet.remove(pageToRemove);
        }

        if (!workingSet.contains(pageNo)) {
            workingSetQueue.add(pageNo);
            workingSet.add(pageNo);
        }
    }

    public int getCurrentWorkingSetSize() {
        if (workingSetSize == 0)
            return 0;

        return workingSet.size();
    }

    public String getName() {
        return getAlgorithmType().toString();
    }
}
