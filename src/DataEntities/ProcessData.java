package DataEntities;

import java.util.ArrayList;
import java.util.List;

public class ProcessData {
    private String name;
    private List<ProcessAndPageData> items; // The pages of current process;
    public int framesQty;
    public int totalPageFaultQty = 0;
    public int nextPageIndex = 0;
    public int currentWorkingSetSize = 0;
    public int iterationPageFaultQty = 0;
    public boolean isLocalExecution;
    public boolean isActive = true;

    public ProcessData(SourceProcessData source, boolean isLocalExecution) {
        this(
                source.getName(),
                convertSourceItems(source.getSourceItems()),
                isLocalExecution);
    }

    public ProcessData(String name, List<ProcessAndPageData> items, boolean isLocalExecution) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("The name should not be null, empty, or consist of white-space characters only.");
        if (items == null)
            throw new NullPointerException("The items should not be null.");

        this.name = name;
        this.items = items;
        this.isLocalExecution = isLocalExecution;
    }

    private static List<ProcessAndPageData> convertSourceItems(List<SourceProcessAndPageData> sourceItems) {
        if (sourceItems == null)
            throw new NullPointerException("The sourceItems should not be null");

        List<ProcessAndPageData> result = new ArrayList<>(sourceItems.size());

        for (SourceProcessAndPageData sourceItem : sourceItems) {
            result.add(new ProcessAndPageData(sourceItem));
        }

        return result;
    }

    public boolean hasNext() {
        return nextPageIndex < items.size();
    }

    public List<ProcessAndPageData> getItems() {
        return items;
    }

    public int getSize() {
        return items.size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (isLocalExecution)
            return "ProcessData{" +
                    "name='" + name + '\'' +
                    ", frames=" + framesQty +
                    ", size=" + getSize() +
                    ", totalPageFaults=" + totalPageFaultQty +
                    ", iterationPageFaults=" + iterationPageFaultQty +
                    ", isLocalExecution=" + isLocalExecution +
                    '}';

        return "ProcessData{" +
                "name='" + name + '\'' +
                ", frames=" + framesQty +
                ", size=" + getSize() +
                ", totalPageFaults=" + totalPageFaultQty +
                ", iterationPageFaults=" + iterationPageFaultQty +
                ", nextPageIndex=" + nextPageIndex +
                ", currentWorkingSetSize=" + currentWorkingSetSize +
                ", hasNext=" + hasNext() +
                ", isActive=" + isActive +
                ", isLocalExecution=" + isLocalExecution +
                '}';
    }
}
