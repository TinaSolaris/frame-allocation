package FrameAllocationAlgorithms;

import DataEntities.ProcessData;

import java.util.List;

public class PageFaultFrequency extends AbstractFrameAllocationAlgorithm {
    private static final int MAX_TOTAL_ITERATION_PAGE_FAULT_QTY = 100;

    public PageFaultFrequency(List<ProcessData> processes, int totalFramesQty) {
        super(processes, totalFramesQty);
    }

    @Override
    public void allocateFrames() {
        activateProcesses();
        List<ProcessData> activeProcesses = getActiveProcesses();
        int totalIterationPageFaultQty = calculateIterationPageFaultQty(activeProcesses);

        if (totalIterationPageFaultQty == 0) {
            var equal = new EQUAL(processes, totalFramesQty);
            equal.allocateFrames();
            return;
        }

        while (totalIterationPageFaultQty > MAX_TOTAL_ITERATION_PAGE_FAULT_QTY) {
            if (activeProcesses.size() > 1) {
                var inactiveProcess = activeProcesses.get(activeProcesses.size() - 1);
                inactiveProcess.isActive = false;
                inactiveProcess.framesQty = 0;
            }
            else
                break;

            activeProcesses = getActiveProcesses();
            totalIterationPageFaultQty = calculateIterationPageFaultQty(activeProcesses);
        }

        allocateFrames(activeProcesses, totalIterationPageFaultQty);
    }

    private void allocateFrames(List<ProcessData> activeProcesses, int totalIterationPageFaultQty) {
        int framesQty;
        int usedFramesQty = 0;

        for (int i = 0; i < activeProcesses.size(); i++) {
            ProcessData process = activeProcesses.get(i);
            int maxFramesQty = totalFramesQty - usedFramesQty - (MIN_FRAMES_QTY * (activeProcesses.size() - i - 1));

            framesQty = (process.iterationPageFaultQty * totalFramesQty) / totalIterationPageFaultQty;

            if (framesQty < MIN_FRAMES_QTY)
                framesQty = MIN_FRAMES_QTY;
            else if (framesQty > maxFramesQty)
                framesQty = maxFramesQty;

            process.framesQty = framesQty;
            usedFramesQty += framesQty;

            if (usedFramesQty > totalFramesQty)
                throw new IllegalArgumentException("The quantity of frames is not enough.");
        }
    }

    private int calculateIterationPageFaultQty(List<ProcessData> activeProcesses) {
        int total = 0;

        for (ProcessData process : activeProcesses) {
            total += process.iterationPageFaultQty;
        }

        return total;
    }

    @Override
    public FrameAllocationAlgorithmType getAlgorithmType() {
        return FrameAllocationAlgorithmType.PAGE_FAULT_FREQUENCY;
    }
}