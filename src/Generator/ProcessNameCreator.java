package Generator;

public class ProcessNameCreator {
    private static final String PREFIX = "pr";

    public static String createProcessName(int index) {
        return PREFIX + index;
    }
}
