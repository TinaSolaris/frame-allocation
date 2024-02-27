package DataEntities;

import PageReplacementAlgorithms.PageProcessingStatus;

import java.util.*;

public class ProcessAndPageData {
    private String processName; // A serial number of the current process
    private String pageName; // A serial number of the current page (number in a sequence - index)
    private int pageNo; // A page number
    private PageProcessingStatus status;
    private int totalPageFaults; // a sum of all page faults
    private String notes;

    public ProcessAndPageData(String processName, String pageName, int pageNo) {
        this.processName = processName;
        this.pageName = pageName;
        this.pageNo = pageNo;
        this.status = PageProcessingStatus.UNPROCESSED;
        this.totalPageFaults = 0;
        this.notes = "";
    }

    public ProcessAndPageData(SourceProcessAndPageData source) {
        this(source.getProcessName(), source.getPageName(), source.getPageNo());
    }

    public String getProcessName() {
        return processName;
    }

    public String getPageName() {
        return pageName;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalPageFaults() {
        return totalPageFaults;
    }

    public void setTotalPageFaults(int value) {
        if (value < 0)
            throw new IllegalArgumentException("The value cannot be negative.");

        totalPageFaults = value;
    }

    public PageProcessingStatus getStatus() {
        return status;
    }

    public void setStatus(PageProcessingStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String value) {
        notes = value;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob)
            return true;

        if (!(ob instanceof ProcessAndPageData))
            return false;

        ProcessAndPageData that = (ProcessAndPageData)ob;
        if (this.hashCode() != that.hashCode())
            return false;

        return Objects.equals(getProcessName(), that.getProcessName()) &&
                Objects.equals(getPageName(), that.getPageName()) &&
                Objects.equals(getPageNo(), that.getPageNo()) &&
                Objects.equals(getTotalPageFaults(), that.getTotalPageFaults()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessName(), getPageName(), getPageNo(), getTotalPageFaults(), getStatus(), getNotes());
    }

    @Override
    public String toString() {
        return "[ProcessAndPageData]: " +
                "processName: " + processName +
                ", pageName: " + pageName +
                ", pageNo: " + pageNo +
                ", status: " + status +
                ", totalPageFaults: " + totalPageFaults +
                ", notes: \"" + notes + "\"";
    }
}