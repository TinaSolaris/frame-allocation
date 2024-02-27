package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.*;

public class FIFO extends AbstractPageReplacementAlgorithm {
    public FIFO(ProcessData process) {
        super(process);
    }

    public FIFO(List<ProcessAndPageData> items, int framesQty, InputParams params) {
        super(items, framesQty, params);
    }

    public void process() {
        init();

        if (framesQty == 0)
            return;

        // A HashSet is used to represent frames, i.e., a set of current pages.
        // So that it could be quickly checked if a page is present or not in the frames.
        HashSet<Integer> frames = new HashSet<>(framesQty);

        // To store the pages in PageReplacementAlgorithms.FIFO manner
        Queue<Integer> queue = new LinkedList<>();

        for (ProcessAndPageData item : items) {
            if (!frames.contains(item.getPageNo())) {
                if (frames.size() == framesQty) {
                    int pageToRemove = queue.poll();
                    frames.remove(pageToRemove);
                    item.setNotes("Removed: " + pageToRemove);
                } else {
                    item.setNotes("Used empty slot");
                }

                frames.add(item.getPageNo());
                queue.add(item.getPageNo());
                totalPageFaults++; // If this page is not present, this is a page fault
                item.setStatus(PageProcessingStatus.FAULT);
            }
            else {
                item.setStatus(PageProcessingStatus.HIT);
            }

            addToWorkingSet(item.getPageNo());
            item.setTotalPageFaults(totalPageFaults);
        }
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public List<ProcessAndPageData> getProcessedItems() {
        return items;
    }

    @Override
    public PageReplacementAlgorithmType getAlgorithmType() {
        return PageReplacementAlgorithmType.FIFO;
    }
}