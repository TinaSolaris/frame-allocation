package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.*;

public class LRU extends AbstractPageReplacementAlgorithm {
    public LRU(ProcessData process) {
        super(process);
    }

    public LRU(List<ProcessAndPageData> items, int framesQty, InputParams params) {
        super(items, framesQty, params);
    }

    public void process() {
        init();

        if (framesQty == 0)
            return;

        // A HashSet is used to represent frames, i.e., a set of current pages.
        // So that it could be quickly checked if a page is present or not in the frames.
        HashSet<Integer> frames = new HashSet<>(this.framesQty);

        // To store least recently used indexes of pages.
        HashMap<Integer, Integer> indexesOfLru = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            ProcessAndPageData item = items.get(i);
            int currentPageNo = item.getPageNo();

            if (frames.size() < framesQty) {
                if (!frames.contains(currentPageNo)) {
                    frames.add(currentPageNo);
                    item.setStatus(PageProcessingStatus.FAULT);
                    item.setNotes("Used empty slot");
                    totalPageFaults++;
                }
                else {
                    item.setStatus(PageProcessingStatus.HIT);
                }

                // Store the recently used index of each page
                indexesOfLru.put(currentPageNo, i);
            }
            else { // Perform the PageReplacementAlgorithms.LRU, i.e., remove the least recently used page and insert the current page
                if (!frames.contains(currentPageNo)) {
                    // Find the least recently used pages that is present in the set
                    int lru = Integer.MAX_VALUE, pageToRemove = Integer.MIN_VALUE;

                    Iterator<Integer> iterator = frames.iterator();

                    while (iterator.hasNext()) {
                        int temp = iterator.next();
                        if (indexesOfLru.get(temp) < lru) {
                            lru = indexesOfLru.get(temp);
                            pageToRemove = temp;
                        }
                    }

                    frames.remove(pageToRemove); // remove the indexesOfLru page
                    indexesOfLru.remove(pageToRemove); // remove the lru from the hashmap

                    frames.add(currentPageNo); // insert the current page
                    item.setStatus(PageProcessingStatus.FAULT);
                    item.setNotes("Removed: " + pageToRemove);
                    totalPageFaults++;
                }
                else {
                    item.setStatus(PageProcessingStatus.HIT);
                }

                indexesOfLru.put(currentPageNo, i); // Update the current page index
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
        return PageReplacementAlgorithmType.LRU;
    }
}