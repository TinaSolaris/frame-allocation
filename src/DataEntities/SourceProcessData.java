package DataEntities;

import java.util.ArrayList;
import java.util.List;

public class SourceProcessData {
    private String name;
    List<SourceProcessAndPageData> sourceItems; // The pages of current process;
    private int framesQty;

    public SourceProcessData(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("The name should not be null, empty, or consist of white-space characters only.");

        this.name = name;
        this.sourceItems = new ArrayList();
    }

    public void appendSourceItem(SourceProcessAndPageData sourceItem) {
        if (sourceItem == null)
            throw new NullPointerException("The sourceItem cannot be null.");

        sourceItems.add(sourceItem);
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return sourceItems.size();
    }

    public int getFramesQty() {
        return framesQty;
    }

    public void setFramesQty(int framesQty) {
        if (framesQty <= 0)
            throw new IllegalArgumentException("The number of frames should be a positive integer number.");

        this.framesQty = framesQty;
    }

    public List<SourceProcessAndPageData> getSourceItems() {
        return sourceItems;
    }
}