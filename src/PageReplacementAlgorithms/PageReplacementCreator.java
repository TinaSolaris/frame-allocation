package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.List;

public class PageReplacementCreator {
    public static PageReplacementAlgorithm create(PageReplacementAlgorithmType algorithmType, ProcessData process) {
        if (algorithmType == null)
            throw new NullPointerException("The algorithmType should not be null.");
        if (process == null)
            throw new NullPointerException("The process should not be null.");

        switch (algorithmType) {
            case ALRU -> {
                return new ALRU(process);
            }
            case FIFO -> {
                return new FIFO(process);
            }
            case LRU -> {
                return new LRU(process);
            }
            case OPT -> {
                return new OPT(process);
            }
            case RAND -> {
                return new RAND(process);
            }
            default -> {
                throw new IllegalArgumentException("The algorithmType: " + algorithmType + " is not supported.");
            }
        }
    }

    public static PageReplacementAlgorithm create(
            PageReplacementAlgorithmType algorithmType,
            List<ProcessAndPageData> items,
            int framesQty,
            InputParams params) {
        if (algorithmType == null)
            throw new NullPointerException("The algorithmType should not be null.");
        if (items == null)
            throw new NullPointerException("The items should not be null.");
        if (framesQty < 0)
            throw new IllegalArgumentException("The framesQty should be non-negative.");
        if (params == null)
            throw new NullPointerException("The params should not be null.");

        switch (algorithmType) {
            case ALRU -> {
                return new ALRU(items, framesQty, params);
            }
            case FIFO -> {
                return new FIFO(items, framesQty, params);
            }
            case LRU -> {
                return new LRU(items, framesQty, params);
            }
            case OPT -> {
                return new OPT(items, framesQty, params);
            }
            case RAND -> {
                return new RAND(items, framesQty, params);
            }
            default -> {
                throw new IllegalArgumentException("The algorithmType: " + algorithmType + " is not supported.");
            }
        }
    }
}
