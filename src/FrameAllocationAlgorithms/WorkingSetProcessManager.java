package FrameAllocationAlgorithms;

import DataEntities.*;
import PageReplacementAlgorithms.PageReplacementAlgorithm;
import PageReplacementAlgorithms.PageReplacementAlgorithmType;
import PageReplacementAlgorithms.PageReplacementCreator;

import java.util.ArrayList;
import java.util.List;

public class WorkingSetProcessManager {
    private final List<ProcessData> processes;
    private final FrameAllocationAlgorithm frameAllocation;
    private final InputParams params;
    private final PageReplacementAlgorithmType pageReplacementType;

    public WorkingSetProcessManager(
            FrameAllocationAlgorithmType frameAllocationType,
            PageReplacementAlgorithmType pageReplacementType,
            List<ProcessData> processes,
            InputParams params) {
        if (frameAllocationType == null)
            throw new NullPointerException("The frameAllocationType should not be null.");
        if (pageReplacementType == null)
            throw new NullPointerException("The pageReplacementType should not be null.");
        if (processes == null)
            throw new NullPointerException("The processes should not be null.");
        if (params == null)
            throw new NullPointerException("The params should not be null.");
        if (params.totalFramesQty <= 0)
            throw new IllegalArgumentException("The params.totalFramesQty should be a positive integer number.");
        if (params.timerStep <= 0)
            throw new IllegalArgumentException("The params.timerStep should be a positive integer number.");
        if (params.workingSetSize <= 0)
            throw new IllegalArgumentException("The params.workingSetSize should be a positive integer number.");

        this.processes = processes;
        this.params = params;
        this.pageReplacementType = pageReplacementType;
        this.frameAllocation = FrameAllocationAlgorithmCreator.create(frameAllocationType, processes, params.totalFramesQty);
    }

    private boolean processesHaveNext() {
        for (ProcessData process : processes) {
            if (process.hasNext())
                return true;
        }

        return false;
    }

    public void execute() {
        System.out.println();
        System.out.println("\tPage Replacement Algorithm: " + pageReplacementType);

        int iteration = 1;
        while (processesHaveNext()) {
            if (params.printWorkingSetIterations) {
                System.out.println();
                System.out.println("\t\tIteration to Re-Allocate Frames: " + iteration);
            }

            frameAllocation.allocateFrames();

            for (ProcessData process : processes) {
                if (!process.hasNext())
                    continue;

                if (process.isActive) {
                    // Get items per iteration and process
                    List<ProcessAndPageData> items = getNextItems(process);

                    PageReplacementAlgorithm pageReplacement = PageReplacementCreator.create(pageReplacementType, items, process.framesQty, params);
                    pageReplacement.process();

                    process.currentWorkingSetSize = pageReplacement.getCurrentWorkingSetSize();
                    process.iterationPageFaultQty = pageReplacement.getTotalPageFaults();
                    process.totalPageFaultQty += pageReplacement.getTotalPageFaults();
                }

                if (params.printWorkingSetIterations)
                    System.out.println("\t\t\t" + process);
            }

            iteration++;
        }

        int totalPageFaults = 0;
        System.out.println();

        for (ProcessData process : processes) {
            System.out.println("\t\t" + process);
            totalPageFaults += process.totalPageFaultQty;
        }

        --iteration;
        System.out.println("\t\tIterations: " + iteration);
        System.out.println("\t\tTotal Page Faults: " + totalPageFaults);
    }

    private List<ProcessAndPageData> getNextItems(ProcessData process) {
        List<ProcessAndPageData> items = new ArrayList<>();

        int maxIndex = process.nextPageIndex + params.timerStep - 1;

        while (process.nextPageIndex <= maxIndex && process.nextPageIndex < process.getItems().size()) {
            items.add(process.getItems().get(process.nextPageIndex));
            process.nextPageIndex++;
        }

        return items;
    }
}