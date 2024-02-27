package DataEntities;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceProcessAndPageData {
    private final static Pattern valueExtract = Pattern.compile("^(?<ProcessName>\\w+)\\s(?<PageName>\\w+)\\s(?<PageNo>\\d+)$");

    private String processName;
    private String pageName; // A page name - index - location in a queue
    private int pageNo; // A page number

    public SourceProcessAndPageData(String line) throws Exception {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("The line should not be null, empty, or consist of white-space characters only");

        Matcher matcher = valueExtract.matcher(line);
        if (matcher.find()) {
            this.processName = matcher.group("ProcessName");
            this.pageName = matcher.group("PageName");
            this.pageNo = parseInteger(matcher.group("PageNo"));
        }
    }

    private int parseInteger(String value) throws Exception {
        try {
            return Integer.parseInt(value);
        }
        catch (Exception ex) {
            throw new Exception("The value: '" + value + "' cannot be parsed as a valid integer number");
        }
    }

    public String getProcessName() {
        return processName;
    }

    public String getPageName() {
        return this.pageName;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob)
            return true;

        if (!(ob instanceof SourceProcessAndPageData))
            return false;

        SourceProcessAndPageData that = (SourceProcessAndPageData)ob;
        if (this.hashCode() != that.hashCode())
            return false;

        return Objects.equals(getProcessName(), that.getProcessName()) &&
                Objects.equals(getPageName(), that.getPageName()) &&
                Objects.equals(getPageNo(), that.getPageNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessName(), getPageName(), getPageNo());
    }

    @Override
    public String toString() {
        return "[SourceProcessAndPageData]: " +
                "processName: " + processName +
                ", pageName: " + pageName +
                ", pageNo: " + pageNo;
    }
}