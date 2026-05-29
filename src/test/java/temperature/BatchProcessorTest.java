package temperature;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BatchProcessorTest {

    private static final String TEST_FILE = "batch_test.csv";
    private static final String SUMMARY_FILE = TEST_FILE + "_summary.txt";

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
        Files.deleteIfExists(Paths.get(SUMMARY_FILE));
    }

    private BatchProcessor buildProcessor() {
        return new BatchProcessor(new TemperatureParser(), new ReportBuilder(), new FileReportWriter());
    }

    private void writeLines(String... lines) throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(TEST_FILE))) {
            for (String line : lines) {
                w.println(line);
            }
        }
    }

    @Test
    void testProcessCreatesSummaryFile() throws IOException {
        writeLines("09:00:00,20.0", "09:01:00,21.0");
        buildProcessor().process(TEST_FILE);
        assertTrue(Files.exists(Paths.get(SUMMARY_FILE)));
    }

    @Test
    void testProcessSummaryContainsCorrectCounts() throws IOException {
        writeLines("09:00:00,20.0", "09:01:00,21.0", "bad-line");
        buildProcessor().process(TEST_FILE);
        String content = Files.readString(Paths.get(SUMMARY_FILE));
        assertTrue(content.contains("Total readings: 3"));
        assertTrue(content.contains("Valid readings: 2"));
        assertTrue(content.contains("Errors: 1"));
    }

    @Test
    void testProcessMissingFileDoesNotThrow() {
        assertDoesNotThrow(() -> buildProcessor().process("nonexistent.csv"));
    }

    @Test
    void testProcessAllInvalidLinesDoesNotCreateSummary() throws IOException {
        writeLines("garbage1", "garbage2");
        buildProcessor().process(TEST_FILE);
        assertFalse(Files.exists(Paths.get(SUMMARY_FILE)));
    }

    @Test
    void testProcessSummaryContainsStats() throws IOException {
        writeLines("09:00:00,10.0", "09:01:00,30.0");
        buildProcessor().process(TEST_FILE);
        String content = Files.readString(Paths.get(SUMMARY_FILE));
        assertTrue(content.contains("Max temperature: 30.00"));
        assertTrue(content.contains("Min temperature: 10.00"));
        assertTrue(content.contains("Average temperature: 20.00"));
    }
}
