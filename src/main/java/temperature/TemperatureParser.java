package temperature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * SRP: Responsible only for reading and parsing raw CSV lines into TemperatureRecords.
 */
public class TemperatureParser {

    public List<String> readLines(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public ParseResult parse(List<String> lines) {
        List<TemperatureRecord> records = new ArrayList<>();
        List<String> badLines = new ArrayList<>();
        int errors = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length != 2) {
                errors++;
                badLines.add("  Line " + (i + 1) + ": " + line);
                continue;
            }

            String timestamp = parts[0].strip();
            String value = parts[1].strip();

            if (timestamp.split(":").length != 3) {
                errors++;
                badLines.add("  Line " + (i + 1) + ": " + line);
                continue;
            }

            double temp;
            try {
                temp = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                errors++;
                badLines.add("  Line " + (i + 1) + ": " + line);
                continue;
            }

            if (temp < -100 || temp > 200) {
                errors++;
                badLines.add("  Line " + (i + 1) + ": " + line);
                continue;
            }

            records.add(new TemperatureRecord(timestamp, temp));
        }

        return new ParseResult(records, errors, badLines);
    }
}
