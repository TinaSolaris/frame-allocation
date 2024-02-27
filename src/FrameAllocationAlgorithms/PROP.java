package FrameAllocationAlgorithms;

import DataEntities.*;

import java.util.List;

public class PROP extends AbstractFrameAllocationAlgorithm {
    public PROP(List<ProcessData> processes, int totalFramesQty) {
        super(processes, totalFramesQty);
    }

    @Override
    public void allocateFrames() {
        int usedFramesQty = 0;

        int framesQty;
        int totalSize = calculateTotalSize();
        for (int i = 0; i < processes.size(); i++) {
            ProcessData process = processes.get(i);
            int maxFramesQty = totalFramesQty - usedFramesQty - (MIN_FRAMES_QTY * (processes.size() - i - 1));

            framesQty = (process.getSize() * totalFramesQty) / totalSize;

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

    private int calculateTotalSize() {
        int totalSize = 0;

        for (ProcessData process : processes) {
            totalSize += process.getSize();
        }

        return totalSize;
    }

    @Override
    public FrameAllocationAlgorithmType getAlgorithmType() {
        return FrameAllocationAlgorithmType.PROP;
    }
}