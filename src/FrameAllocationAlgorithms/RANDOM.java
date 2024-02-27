package FrameAllocationAlgorithms;

import DataEntities.*;

import java.util.*;

public class RANDOM extends AbstractFrameAllocationAlgorithm {
    public RANDOM(List<ProcessData> processes, int totalFramesQty) {
        super(processes, totalFramesQty);
    }

    @Override
    public void allocateFrames() {
        int usedFramesQty = 0;

        for (int i = 0; i < processes.size(); i++) {
            ProcessData process = processes.get(i);
            int maxFramesQty = totalFramesQty - usedFramesQty - (MIN_FRAMES_QTY * (processes.size() - i - 1));
            if (maxFramesQty <= 0)
                throw new IllegalArgumentException(
                        "The input parameters: totalFramesQty=" + totalFramesQty + ", processes.size()=" + processes.size() +
                                " do not allow to generate a good maxFramesQty for the process: " + process.getName());

            int framesQty = generateRandom(maxFramesQty);
            usedFramesQty += framesQty;
            process.framesQty = framesQty;
        }
    }

    private int generateRandom(int max) {
        Random random = new Random();
        return random.nextInt(max - MIN_FRAMES_QTY + 1) + MIN_FRAMES_QTY;
    }

    @Override
    public FrameAllocationAlgorithmType getAlgorithmType() {
        return FrameAllocationAlgorithmType.RANDOM;
    }
}