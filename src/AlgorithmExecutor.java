import DataEntities.*;
import FrameAllocationAlgorithms.*;
import PageReplacementAlgorithms.*;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmExecutor {
    private final List<SourceProcessData> sourceProcesses;
    private final InputParams params;

    public AlgorithmExecutor(List<SourceProcessData> sourceProcesses, InputParams params) {
        if (sourceProcesses == null)
            throw new NullPointerException("The sourceProcesses should not be null.");
        if (params == null)
            throw new NullPointerException("The params should not be null.");

        this.sourceProcesses = sourceProcesses;
        this.params = params;
    }

    public void execute() {
        System.out.println();
        System.out.println("SUMMARY");
        System.out.println(params);

        executeLocal(FrameAllocationAlgorithmType.EQUAL);
        executeLocal(FrameAllocationAlgorithmType.PROP);
        executeLocal(FrameAllocationAlgorithmType.RANDOM);

        executeWorkingSet(FrameAllocationAlgorithmType.WORKING_SET);
        executeWorkingSet(FrameAllocationAlgorithmType.PAGE_FAULT_FREQUENCY);
    }

    private static List<ProcessData> convertSourceProcesses(List<SourceProcessData> sourceProcesses, boolean isLocalExecution) {
        List<ProcessData> result = new ArrayList<>(sourceProcesses.size());

        for (SourceProcessData sourceProcess : sourceProcesses) {
            ProcessData process = new ProcessData(sourceProcess, isLocalExecution);
            result.add(process);
        }

        return result;
    }

    private void executeLocal(FrameAllocationAlgorithmType frameAllocationType) {
        System.out.println();
        System.out.println("Frame Allocation Algorithm: " + frameAllocationType);

        executeLocal(frameAllocationType, PageReplacementAlgorithmType.FIFO);
        executeLocal(frameAllocationType, PageReplacementAlgorithmType.OPT);
        executeLocal(frameAllocationType, PageReplacementAlgorithmType.LRU);
        executeLocal(frameAllocationType, PageReplacementAlgorithmType.ALRU);
        executeLocal(frameAllocationType, PageReplacementAlgorithmType.RAND);
    }

    private void executeLocal(
            FrameAllocationAlgorithmType frameAllocationType,
            PageReplacementAlgorithmType pageReplacementType) {
        System.out.println();
        System.out.println("\tPage Replacement Algorithm: " + pageReplacementType);

        List<ProcessData> processes = convertSourceProcesses(sourceProcesses, true);

        FrameAllocationAlgorithm frameAllocation =
                FrameAllocationAlgorithmCreator.create(frameAllocationType, processes, params.totalFramesQty);
        frameAllocation.allocateFrames();

        int totalPageFaults = 0;

        for (ProcessData process : processes) {
            executePageReplacement(pageReplacementType, process);
            totalPageFaults += process.totalPageFaultQty;
        }

        System.out.println("\t\tTotal Page Faults: " + totalPageFaults);
    }

    private void executePageReplacement(PageReplacementAlgorithmType algorithmType, ProcessData process) {
        PageReplacementAlgorithm pageReplacement = PageReplacementCreator.create(algorithmType, process);
        pageReplacement.process();

        process.totalPageFaultQty = pageReplacement.getTotalPageFaults();
        process.iterationPageFaultQty = pageReplacement.getTotalPageFaults();

        System.out.println("\t\t" + process);

        if (params.printItems) {
            int index = 1;
            for (ProcessAndPageData item : pageReplacement.getProcessedItems()) {
                System.out.println("\t\t\t" + index + ": " + item);
                index++;
            }
        }
    }

    private void executeWorkingSet(FrameAllocationAlgorithmType frameAllocationType) {
        System.out.println();
        System.out.println("Frame Allocation Algorithm: " + frameAllocationType);

        executeWorkingSet(frameAllocationType, PageReplacementAlgorithmType.FIFO);
        executeWorkingSet(frameAllocationType, PageReplacementAlgorithmType.OPT);
        executeWorkingSet(frameAllocationType, PageReplacementAlgorithmType.LRU);
        executeWorkingSet(frameAllocationType, PageReplacementAlgorithmType.ALRU);
        executeWorkingSet(frameAllocationType, PageReplacementAlgorithmType.RAND);
    }

    private void executeWorkingSet(FrameAllocationAlgorithmType frameAllocationType, PageReplacementAlgorithmType pageReplacementType) {
        List<ProcessData> processes = convertSourceProcesses(sourceProcesses, false);
        WorkingSetProcessManager manager = new WorkingSetProcessManager(frameAllocationType, pageReplacementType, processes, params);
        manager.execute();
    }
}