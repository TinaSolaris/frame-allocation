package PageReplacementAlgorithms;

import DataEntities.InputParams;
import DataEntities.ProcessAndPageData;
import DataEntities.ProcessData;

import java.util.*;

public class OPT extends AbstractPageReplacementAlgorithm {
    public OPT(ProcessData process) {
        super(process);
    }

    public OPT(List<ProcessAndPageData> items, int framesQty, InputParams params) {
        super(items, framesQty, params);
    }

    public void process() {
        init();

        if (framesQty == 0)
            return;

        int[] frames = new int[framesQty];
        int frameIndex = 0;

        for (int pageIndex = 0; pageIndex < items.size(); pageIndex++) {
            ProcessAndPageData item = items.get(pageIndex);

            // Set the HIT status if the page is found in the frames.
            int foundFrameIndex = findFrameByPage(item.getPageNo(), frames);
            if (foundFrameIndex >= 0) {
                item.setTotalPageFaults(totalPageFaults);
                item.setStatus(PageProcessingStatus.HIT);
                item.setNotes("Frame: " + foundFrameIndex);
                continue;
            }

            // Set the FAULT status if the page is not found in the frames.
            totalPageFaults++;
            addToWorkingSet(item.getPageNo());
            item.setTotalPageFaults(totalPageFaults);
            item.setStatus(PageProcessingStatus.FAULT);

            if (frameIndex < this.framesQty) {
                frames[frameIndex] = item.getPageNo();
                item.setNotes("Frame: " + frameIndex);
                frameIndex++;
            }
            else {
                foundFrameIndex = findFrame(frames, pageIndex + 1);
                frames[foundFrameIndex] = item.getPageNo();
                item.setNotes("Frame: " + foundFrameIndex);
            }
        }
    }

    // Finds a frame with a page that will not be used
    // soon after the given index in the pages
    private int findFrame(int[] frames, int startPageIndex) {
        int resultFrameIndex = 0;
        int farthestPageIndex = startPageIndex;

        for (int frameIndex = 0; frameIndex < frames.length; frameIndex++) {
            int pageToFindInFuture = frames[frameIndex];

            int pageIndex;
            for (pageIndex = startPageIndex; pageIndex < items.size(); pageIndex++) {
                if (pageToFindInFuture == items.get(pageIndex).getPageNo()) {
                    if (pageIndex > farthestPageIndex) {
                        farthestPageIndex = pageIndex;
                        resultFrameIndex = frameIndex;
                    }
                    break;
                }
            }

            // If a page is never referenced in the future, return the frame index.
            if (pageIndex == items.size())
                return frameIndex;
        }

        return resultFrameIndex;
    }

    private int findFrameByPage(int page, int[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == page)
                return i;
        }

        return -1;
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public List<ProcessAndPageData> getProcessedItems() {
        return items;
    }

    @Override
    public PageReplacementAlgorithmType getAlgorithmType() {
        return PageReplacementAlgorithmType.OPT;
    }
}