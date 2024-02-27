import DataEntities.SourceProcessAndPageData;
import DataEntities.SourceProcessData;
import Generator.ProcessNameCreator;
import Generator.ScanFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SourceProcessCreator {
    public static List<SourceProcessData> create(String filePath, int processesQty) throws Exception {
        if (filePath == null || filePath.isBlank())
            throw new IllegalArgumentException("The filePath should not be null, empty, or consist of white-space characters only.");
        if (processesQty <= 0)
            throw new IllegalArgumentException("The processesQty should be positive.");

        ScanFile fileScanner = new ScanFile(filePath);
        fileScanner.scan();

        List<SourceProcessAndPageData> sourceItems = fileScanner.getItems();

        List<SourceProcessData> sourceProcesses = new ArrayList<>(processesQty);
        HashMap<String, SourceProcessData> map = new HashMap<>(processesQty);

        for (int i = 0; i < processesQty; i++) {
            String name = ProcessNameCreator.createProcessName(i + 1);
            var sourceProcess = new SourceProcessData(name);
            sourceProcesses.add(sourceProcess);
            map.put(name, sourceProcess);
        }

        for (SourceProcessAndPageData sourceItem : sourceItems) {
            SourceProcessData process = map.get(sourceItem.getProcessName());
            if (process == null)
                throw new NullPointerException("The process name is not expected.");

            process.appendSourceItem(sourceItem);
        }

        return sourceProcesses;
    }
}
