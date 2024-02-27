package Generator;

public class ProcessWithPageGroups {
    PageGroup[] pageGroups;
    int groupsQty;
    int maxItemsQty;
    String processName;

    @Override
    public String toString() {
        return "ProcessWithPageGroups{" +
                "groupsQty=" + groupsQty +
                ", maxItemsQty=" + maxItemsQty +
                ", processName='" + processName + '\'' +
                '}';
    }
}
