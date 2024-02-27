package FrameAllocationAlgorithms;

import DataEntities.ProcessData;

import java.util.List;

public class FrameAllocationAlgorithmCreator {
    public static FrameAllocationAlgorithm create(
            FrameAllocationAlgorithmType algorithmType,
            List<ProcessData> processes,
            int totalFramesQty) {
        if (algorithmType == null)
            throw new NullPointerException("The algorithmType should not be null.");
        if (processes == null)
            throw new NullPointerException("The processes should not be null.");
        if (totalFramesQty <= 0)
            throw new NullPointerException("The totalFramesQty should be positive.");

        switch (algorithmType) {
            case EQUAL -> {
                return new EQUAL(processes, totalFramesQty);
            }
            case PROP -> {
                return new PROP(processes, totalFramesQty);
            }
            case RANDOM -> {
                return new RANDOM(processes, totalFramesQty);
            }
            case WORKING_SET -> {
                return new WorkingSet(processes, totalFramesQty);
            }
            case PAGE_FAULT_FREQUENCY -> {
                return new PageFaultFrequency(processes, totalFramesQty);
            }
            default -> {
                throw new IllegalArgumentException("The algorithmType: " + algorithmType + " is not supported.");
            }
        }
    }
}
