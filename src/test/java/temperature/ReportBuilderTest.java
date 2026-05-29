package temperature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReportBuilderTest {

    private final ReportBuilder builder = new ReportBuilder();

    private TemperatureStatistics statsFor(double... temps) {
        List<TemperatureRecord> records = new java.util.ArrayList<>();
        int i = 0;
        for (double t : temps) {
            records.add(new TemperatureRecord("0" + i + ":00:00", t));
            i++;
        }
        return new TemperatureStatistics(records);
    }

    @Test
    void testReportContainsFilename() {
        String report = builder.build("data.csv", 5, 5, 0, statsFor(10.0, 20.0), Collections.emptyList());
        assertTrue(report.contains("File analyzed: data.csv"));
    }

    @Test
    void testReportContainsTotalReadings() {
        String report = builder.build("data.csv", 10, 8, 2, statsFor(10.0, 20.0), Collections.emptyList());
        assertTrue(report.contains("Total readings: 10"));
    }

    @Test
    void testReportContainsValidReadings() {
        String report = builder.build("data.csv", 10, 8, 2, statsFor(10.0, 20.0), Collections.emptyList());
        assertTrue(report.contains("Valid readings: 8"));
    }

    @Test
    void testReportContainsErrorCount() {
        String report = builder.build("data.csv", 10, 8, 2, statsFor(10.0, 20.0), Collections.emptyList());
        assertTrue(report.contains("Errors: 2"));
    }

    @Test
    void testReportContainsMaxTemperature() {
        String report = builder.build("data.csv", 2, 2, 0, statsFor(10.0, 30.0), Collections.emptyList());
        assertTrue(report.contains("Max temperature: 30.00"));
    }

    @Test
    void testReportContainsMinTemperature() {
        String report = builder.build("data.csv", 2, 2, 0, statsFor(10.0, 30.0), Collections.emptyList());
        assertTrue(report.contains("Min temperature: 10.00"));
    }

    @Test
    void testReportContainsAverageTemperature() {
        String report = builder.build("data.csv", 2, 2, 0, statsFor(10.0, 30.0), Collections.emptyList());
        assertTrue(report.contains("Average temperature: 20.00"));
    }

    @Test
    void testReportContainsBadLinesWhenErrorsExist() {
        List<String> badLines = Arrays.asList("  Line 1: garbage");
        String report = builder.build("data.csv", 2, 1, 1, statsFor(20.0), badLines);
        assertTrue(report.contains("Invalid lines:"));
        assertTrue(report.contains("  Line 1: garbage"));
    }

    @Test
    void testReportDoesNotContainInvalidLinesSectionWhenNoErrors() {
        String report = builder.build("data.csv", 1, 1, 0, statsFor(20.0), Collections.emptyList());
        assertFalse(report.contains("Invalid lines:"));
    }
}
