package Generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PageDataGenerator {
    private final static int MIN_PAGE_COUNT = 10;
    private final static int MIN_PAGE_NO_LIMIT = 1;
    private final ProcessWithPageGroups[] processes;
    private final GeneratorInputParams params;

    public PageDataGenerator(GeneratorInputParams params) {
        if (params == null)
            throw new NullPointerException("The params should not be null.");
        if (params.maxPageNoLimit <= 0)
            throw new IllegalArgumentException("The maxPageNoLimit should be positive.");
        if (params.processesQty <= 0)
            throw new IllegalArgumentException("The processesQty should be positive.");

        this.params = params;
        this.processes = initProcessPageGroups();
    }

    private ProcessWithPageGroups[] initProcessPageGroups() {
        if (params.printDetails)
            System.out.println("Generator Summary");

        ArrayList<ProcessWithPageGroups> result = new ArrayList<>(params.processesQty);

        for (int processIndex = 0; processIndex < params.processesQty; processIndex++) {
            ProcessWithPageGroups process = new ProcessWithPageGroups();
            process.processName = ProcessNameCreator.createProcessName(processIndex);
            process.maxItemsQty = generateRandom(params.minItemsQty, params.maxItemsQty);
            process.groupsQty = process.maxItemsQty / params.GROUPS_DIVIDER;
            process.pageGroups = new PageGroup[process.groupsQty];

            if (params.printDetails) {
                System.out.println();
                System.out.println("\t" + process);
            }

            int totalPageCount = 0;
            for (int i = 0; i < process.groupsQty; i++) {
                int maxPageCount = process.maxItemsQty - totalPageCount - (MIN_PAGE_COUNT * (process.groupsQty - i - 1));
                if (maxPageCount <= 0)
                    throw new IllegalArgumentException("The input parameters: maxItems = " + process.maxItemsQty +
                            ", maxPageNoLimit = " + params.maxPageNoLimit  + ", groupCount = " + process.groupsQty +
                            " do not allow to generate a good maxPageCount.");

                int pageCount = i < (process.groupsQty - 1) ? generateRandom(MIN_PAGE_COUNT, maxPageCount) : process.maxItemsQty - totalPageCount;
                int minPageNo = generateRandom(MIN_PAGE_NO_LIMIT, params.maxPageNoLimit - 1);
                int maxPageNo = minPageNo + pageCount;
                maxPageNo = Math.min(maxPageNo, params.maxPageNoLimit);
                process.pageGroups[i] = new PageGroup(minPageNo, maxPageNo, pageCount);
                totalPageCount += pageCount;

                if (params.printDetails)
                    System.out.println("\t\t" + process.pageGroups[i] + " ");
            }

            result.add(process);
        }

        return result.toArray(new ProcessWithPageGroups[params.processesQty]);
    }

    public void writeToFile(String filePath) throws IOException {
        if (filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("The filePath should not be null, empty, or consist of white-space characters only");

        FileWriter writer = new FileWriter(filePath);
        writer.write("processName pageIndex pageNumber");

        for (int processIndex = 0; processIndex < processes.length; processIndex++) {
            ProcessWithPageGroups process = processes[processIndex];
            for (int groupIndex = 0; groupIndex < process.pageGroups.length; groupIndex++) {
                PageGroup pageGroup = process.pageGroups[groupIndex];
                for (int i = 1; i <= pageGroup.getPageCount(); i++) {
                    String name = ProcessNameCreator.createProcessName(processIndex + 1);
                    writer.write("\n" + name + " p" + groupIndex + "_" + i + " " + generateRandom(pageGroup.getMinPageNo(), pageGroup.getMaxPageNo()));
                }
            }
        }

        writer.close();
    }

    private int generateRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static final String ACTUAL_FILE = "F:\\Path\\actual_data_advanced.txt";

    public static void main(String[] args) {
        try {
            var params = new GeneratorInputParams();
            params.maxPageNoLimit = 30;
            params.processesQty = 5;
            params.printDetails = true;

            var generator = new PageDataGenerator(params);
            generator.writeToFile(ACTUAL_FILE);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}