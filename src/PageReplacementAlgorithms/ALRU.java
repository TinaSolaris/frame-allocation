package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.*;

public class ALRU extends AbstractPageReplacementAlgorithm {
    public ALRU(ProcessData process) {
        super(process);
    }

    public ALRU(List<ProcessAndPageData> items, int framesQty, InputParams params) {
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

        boolean[] secondChance = new boolean[framesQty];

        // To check if the queue is filled up or not
        int count = 0;
        for (int i = 0; i < items.size(); i++) {
            ProcessAndPageData item = items.get(i);
            int currentPageNo = item.getPageNo();

            if (!frames.contains(currentPageNo)) {
                // Queue is not filled up to framesQty
                if (count < framesQty) {
                    frames.add(currentPageNo);
                    queue.add(currentPageNo);
                    item.setNotes("Used empty slot");
                    count++;
                }
                else { // Queue is filled up to framesQty
                    // Find the first value that has its bit set to 0
                    int indexToReplace = findIndexToReplace(secondChance);

                    if (!queue.isEmpty()) {
                        int j = 0;
                        int pageNo;

                        // Rotate the queue and set the front's bit value to 0 until the value where the secondChance = 0
                        while (j < indexToReplace) {
                            pageNo = queue.poll();
                            queue.add(pageNo);
                            rotateSecondChance(secondChance);
                            j++;
                        }

                        // Remove the front element (the element with the secondChance = 0)
                        int pageToRemove = queue.poll();
                        frames.remove(pageToRemove);
                        item.setNotes("Removed: " + pageToRemove);

                        // Push the element from the page array (next input)
                        frames.add(currentPageNo);
                        queue.add(currentPageNo);
                    }
                }

                item.setStatus(PageProcessingStatus.FAULT);
                totalPageFaults++;
            }
            else { // If the input for the iteration was a hit
                Iterator<Integer> iterator = queue.iterator();
                int index = 0;

                while (iterator.hasNext()) {
                    if (iterator.next() == currentPageNo)
                        secondChance[index] = true;
                    index++;
                }

                item.setStatus(PageProcessingStatus.HIT);
            }

            addToWorkingSet(item.getPageNo());
            item.setTotalPageFaults(totalPageFaults);
        }
    }

    private void rotateSecondChance(boolean[] secondChance) {
        boolean temp = secondChance[0];
        for (int index = 0; index < framesQty - 1; index++)
            secondChance[index] = secondChance[index + 1];
        secondChance[framesQty - 1] = temp;
    }

    // Find the first value that has its bit set to 0
    private int findIndexToReplace(boolean[] secondChance) {
        for (int i = 0; i < secondChance.length; i++) {
            if (secondChance[i])
                secondChance[i] = false;
            else
                return i;
        }

        return secondChance.length - 1;
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public List<ProcessAndPageData> getProcessedItems() {
        return items;
    }

    @Override
    public PageReplacementAlgorithmType getAlgorithmType() {
        return PageReplacementAlgorithmType.ALRU;
    }
}