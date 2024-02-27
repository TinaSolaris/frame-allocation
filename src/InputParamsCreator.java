import DataEntities.InputParams;

import java.util.Scanner;

public class InputParamsCreator {
    private static final int DEFAULT_TOTAL_FRAMES_QTY = 50;
    private static final int DEFAULT_WORKING_SET_SIZE = 10;
    private static final int DEFAULT_TIMER_STEP = 100;

    private final Scanner scanner; // for input stream
    private final int maxPageNoLimit;

    public InputParamsCreator(Scanner scanner, int maxPageNoLimit) {
        if (scanner == null)
            throw new NullPointerException("The scanner should not be null.");
        if (maxPageNoLimit <= 0)
            throw new IllegalArgumentException("The maxPageNoLimit should be positive.");

        this.scanner = scanner;
        this.maxPageNoLimit = maxPageNoLimit;
    }

    public InputParams getParams() {
        var params = new InputParams();
        params.printItems = false;
        params.printWorkingSetIterations = false;
        params.maxPageNoLimit = maxPageNoLimit;

        System.out.println();
        System.out.println("Enter the Execution params or \"exit\".");
        String line;

        // Total Number of Frames
        System.out.print("\tTotal Number of Frames or press Enter (for Default=" + DEFAULT_TOTAL_FRAMES_QTY + "): ");
        line = scanner.nextLine();
        if (line.equalsIgnoreCase("exit"))
            return null;

        params.totalFramesQty = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_TOTAL_FRAMES_QTY;

        // Timer Step
        System.out.print("\tTimer Step or press Enter (for Default=" + DEFAULT_TIMER_STEP + "): ");
        line = scanner.nextLine();
        if (line.equalsIgnoreCase("exit"))
            return null;

        params.timerStep = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_TIMER_STEP;

        // Working Set Size
        System.out.print("\tWorking Set Size or press Enter (for Default=" + DEFAULT_WORKING_SET_SIZE + "): ");
        line = scanner.nextLine();
        if (line.equalsIgnoreCase("exit"))
            return null;

        params.workingSetSize = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_WORKING_SET_SIZE;

        return params;
    }
}
