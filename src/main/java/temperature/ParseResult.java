package temperature;

import java.util.List;

public class ParseResult {

    private final List<TemperatureRecord> records;
    private final int errorCount;
    private final List<String> badLines;

    public ParseResult(List<TemperatureRecord> records, int errorCount, List<String> badLines) {
        this.records = records;
        this.errorCount = errorCount;
        this.badLines = badLines;
    }

    public List<TemperatureRecord> getRecords() {
        return records;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public List<String> getBadLines() {
        return badLines;
    }
}
