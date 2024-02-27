package FrameAllocationAlgorithms;

import DataEntities.*;

import java.util.List;

public class EQUAL extends AbstractFrameAllocationAlgorithm {
    public EQUAL(List<ProcessData> processes, int totalFramesQty) {
        super(processes, totalFramesQty);
    }

    @Override
    public void allocateFrames() {
        int framesQty = totalFramesQty / processes.size();

        for (ProcessData process : processes) {
            process.framesQty = framesQty;
        }
    }

    @Override
    public FrameAllocationAlgorithmType getAlgorithmType() {
        return FrameAllocationAlgorithmType.EQUAL;
    }
}