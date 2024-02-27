import DataEntities.*;
import Generator.*;

import java.util.*;

public class Main {
    static Scanner scanner; // for input stream

    private static final String GENERATED_FILE = "F:\\Path\\generated_data.txt";

    public static void main(String[] args) {
        try {
            String file = GENERATED_FILE;
            scanner = new Scanner(System.in);

            var generatorInputParamsCreator = new GeneratorInputParamsCreator(scanner);
            GeneratorInputParams generatorParams = generatorInputParamsCreator.getParams();

            var generator = new PageDataGenerator(generatorParams);
            generator.writeToFile(file);

            boolean isExit = false;
            while (!isExit) {
                var inputParamsCreator = new InputParamsCreator(scanner, generatorParams.maxPageNoLimit);
                InputParams params = inputParamsCreator.getParams();
                if (params == null) {
                    isExit = true;
                    continue;
                }

                List<SourceProcessData> sourceProcesses = SourceProcessCreator.create(file, generatorParams.processesQty);

                var executor = new AlgorithmExecutor(sourceProcesses, params);
                executor.execute();
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}