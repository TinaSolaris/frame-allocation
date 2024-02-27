package PageReplacementAlgorithms;

import DataEntities.ProcessAndPageData;

import java.util.List;

public interface PageReplacementAlgorithm {
    PageReplacementAlgorithmType getAlgorithmType();
    String getName();
    void process();
    int getTotalPageFaults();
    List<ProcessAndPageData> getProcessedItems();
    int getCurrentWorkingSetSize();
}