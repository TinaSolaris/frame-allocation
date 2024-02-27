package FrameAllocationAlgorithms;

import DataEntities.ProcessData;

import java.util.List;

public abstract class AbstractFrameAllocationAlgorithm implements FrameAllocationAlgorithm {
    protected static final int MIN_FRAMES_QTY = 2;

    protected List<ProcessData> processes;
    protected int totalFramesQty; // The total number of frames for all processes

    protected AbstractFrameAllocationAlgorithm(List<ProcessData> processes, int totalFramesQty) {
        if (processes == null)
            throw new NullPointerException("The processes should not be null.");
        if (totalFramesQty < 0)
            throw new IllegalArgumentException("The totalFramesQty should be a positive integer number.");

        this.processes = processes;
        this.totalFramesQty = totalFramesQty;

        int minTotalFramesQty = MIN_FRAMES_QTY * processes.size();
        if (totalFramesQty < minTotalFramesQty)
            throw new IllegalArgumentException("The totalFramesQty should be larger than " + minTotalFramesQty);
    }

    protected List<ProcessData> getActiveProcesses() {
        return processes.stream().filter(p -> p.hasNext() && p.isActive).toList();
    }

    protected void activateProcesses() {
        processes.stream().forEach(p -> p.isActive = true);
    }

    @Override
    public String getName() {
        return getAlgorithmType().toString();
    }
}
