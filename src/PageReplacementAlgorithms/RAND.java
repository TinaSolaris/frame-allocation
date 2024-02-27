package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.*;

public class RAND extends AbstractPageReplacementAlgorithm {
    public RAND(ProcessData process) {
        super(process);
    }

    public RAND(List<ProcessAndPageData> items, int framesQty, InputParams params) {
        super(items, framesQty, params);
    }

    public void process() {
        init();

        if (framesQty == 0)
            return;

        // A HashSet is used to represent frames, i.e., a set of current pages.
        // So that it could be quickly checked if a page is present or not in the frames.
        HashSet<Integer> frames = new HashSet<>(framesQty);

        for (ProcessAndPageData item : items) {
            if (!frames.contains(item.getPageNo())) {
                if (frames.size() == framesQty) {
                    int pageToRemove = getRandomPageToRemove(frames);
                    frames.remove(pageToRemove);
                    item.setNotes("Removed: " + pageToRemove);
                } else {
                    item.setNotes("Used empty slot");
                }

                frames.add(item.getPageNo());
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

    private int getRandomPageToRemove(HashSet<Integer> frames) {
        Iterator<Integer> iterator = frames.iterator();

        int index = 0;
        int randomIndex = generateRandom(0, frames.size() - 1);

        while (iterator.hasNext()) {
            int page = iterator.next();
            if (index == randomIndex)
                return page;
            index++;
        }

        return 0;
    }

    private int generateRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public List<ProcessAndPageData> getProcessedItems() {
        return items;
    }

    @Override
    public PageReplacementAlgorithmType getAlgorithmType() {
        return PageReplacementAlgorithmType.RAND;
    }
}