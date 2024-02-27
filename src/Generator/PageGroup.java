package Generator;

public class PageGroup {
    private final int minPageNo;
    private final int maxPageNo;
    private final int pageCount;

    public PageGroup(int minPageNo, int maxPageNo, int pageCount) {
        if (minPageNo <= 0)
            throw new IllegalArgumentException("The minPageNo should be positive.");
        if (maxPageNo <= minPageNo)
            throw new IllegalArgumentException("The maxPageNo should be more than minPageNo.");
        if (pageCount <= 0)
            throw new IllegalArgumentException("The pageCount should be positive.");

        this.minPageNo = minPageNo;
        this.maxPageNo = maxPageNo;
        this.pageCount = pageCount;
    }

    public int getMinPageNo() {
        return minPageNo;
    }

    public int getMaxPageNo() {
        return maxPageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public String toString() {
        return "PageGroup{" +
                "minPageNo=" + minPageNo +
                ", maxPageNo=" + maxPageNo +
                ", pageCount=" + pageCount +
                '}';
    }
}