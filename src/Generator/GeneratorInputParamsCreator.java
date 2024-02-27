package Generator;

import java.util.Scanner;

public class GeneratorInputParamsCreator {
    private static final int DEFAULT_MAX_PAGE_NO_LIMIT = 35;
    private static final int DEFAULT_PROCESSES_QTY = 5;
    private static final int DEFAULT_MIN_ITEMS_QTY = 1000;
    private static final int DEFAULT_MAX_ITEMS_QTY = 2000;

    private final Scanner scanner; // for input stream

    public GeneratorInputParamsCreator(Scanner scanner) {
        if (scanner == null)
            throw new NullPointerException("The scanner should not be null.");

        this.scanner = scanner;
    }

    public GeneratorInputParams getParams() {
        var params = new GeneratorInputParams();
        System.out.println("Enter the Generator params.");

        String line;

        // Maximum Page Number
        System.out.print("\tMaximum Page Number (virtual memory size) or press Enter (for Default=" + DEFAULT_MAX_PAGE_NO_LIMIT + "): ");
        line = scanner.nextLine();
        params.maxPageNoLimit = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_MAX_PAGE_NO_LIMIT;

        // Number of Processes
        System.out.print("\tNumber of Processes or press Enter (for Default=" + DEFAULT_PROCESSES_QTY + "): ");
        line = scanner.nextLine();
        params.processesQty = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_PROCESSES_QTY;

        // Minimum Quantity of Pages
        System.out.print("\tMinimum Quantity of Pages (one value for all processes) or press Enter (for Default=" + DEFAULT_MIN_ITEMS_QTY + "): ");
        line = scanner.nextLine();
        params.minItemsQty = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_MIN_ITEMS_QTY;

        // Maximum Quantity of Pages
        System.out.print("\tMaximum Quantity of Pages (one value for all processes) or press Enter (for Default=" + DEFAULT_MAX_ITEMS_QTY + "): ");
        line = scanner.nextLine();
        params.maxItemsQty = !line.isBlank() ? Integer.parseInt(line) : DEFAULT_MAX_ITEMS_QTY;

        if (params.minItemsQty > params.maxItemsQty)
            throw new IllegalArgumentException("The minItemsQty should be less than or equal to the maxItemsQty.");

        if (params.minItemsQty < params.GROUPS_DIVIDER)
            throw new IllegalArgumentException("The minItemsQty should be more then the GROUPS_DIVIDER: " + params.GROUPS_DIVIDER);

        params.printDetails = false;

        return params;
    }
}
