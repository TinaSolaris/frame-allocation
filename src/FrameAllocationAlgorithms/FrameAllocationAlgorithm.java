package FrameAllocationAlgorithms;

import PageReplacementAlgorithms.PageReplacementAlgorithmType;

public interface FrameAllocationAlgorithm {
    FrameAllocationAlgorithmType getAlgorithmType();
    String getName();
    void allocateFrames();
}
