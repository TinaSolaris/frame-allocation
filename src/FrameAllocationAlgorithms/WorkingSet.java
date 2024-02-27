package FrameAllocationAlgorithms;

import DataEntities.ProcessData;

import java.util.List;

public class WorkingSet extends AbstractFrameAllocationAlgorithm {
    public WorkingSet(List<ProcessData> processes, int totalFramesQty) {
        super(processes, totalFramesQty);
    }

    @Override
    public void allocateFrames() {
        activateProcesses();
        List<ProcessData> activeProcesses = getActiveProcesses();
        int totalWorkingSetSize = calculateTotalWorkingSetSize(activeProcesses);

        if (totalWorkingSetSize == 0) {
            var equal = new EQUAL(processes, totalFramesQty);
            equal.allocateFrames();
            return;
        }

        while (totalWorkingSetSize > totalFramesQty) {
            if (activeProcesses.size() > 1) {
                var inactiveProcess = activeProcesses.get(activeProcesses.size() - 1);
                inactiveProcess.isActive = false;
                inactiveProcess.framesQty = 0;
            }
            else
                break;

            activeProcesses = getActiveProcesses();
            totalWorkingSetSize = calculateTotalWorkingSetSize(activeProcesses);
        }

        allocateFrames(activeProcesses, totalWorkingSetSize);
    }

    private void allocateFrames(List<ProcessData> activeProcesses, int totalWorkingSetSize) {
        int framesQty;
        int usedFramesQty = 0;

        for (int i = 0; i < activeProcesses.size(); i++) {
            ProcessData process = activeProcesses.get(i);
            int maxFramesQty = totalFramesQty - usedFramesQty - (MIN_FRAMES_QTY * (activeProcesses.size() - i - 1));

            framesQty = (process.currentWorkingSetSize * totalFramesQty) / totalWorkingSetSize;

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

    private int calculateTotalWorkingSetSize(List<ProcessData> activeProcesses) {
        int total = 0;

        for (ProcessData process : activeProcesses) {
            total += process.currentWorkingSetSize;
        }

        return total;
    }

    @Override
    public FrameAllocationAlgorithmType getAlgorithmType() {
        return FrameAllocationAlgorithmType.WORKING_SET;
    }
}