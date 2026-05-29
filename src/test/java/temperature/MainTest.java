package temperature;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;

public class MainTest {

    private static final String TEST_FILENAME = "test_temps.csv";
    private static final String SUMMARY_FILE = TEST_FILENAME + "_summary.txt";

    @BeforeEach
    void setUp() throws IOException {
        String[] testData = {
            "09:15:30,23.5",
            "09:16:00,24.1",
            "09:16:30,22.8",
            "09:17:00,25.3",
            "09:17:30,23.9",
            "09:18:00,24.7",
            "09:18:30,22.4",
            "09:19:00,26.1",
            "09:19:30,23.2",
            "09:20:00,25.0"
        };
        try (PrintWriter writer = new PrintWriter(new FileWriter(TEST_FILENAME))) {
            for (String line : testData) {
                writer.println(line);
            }
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILENAME));
        Files.deleteIfExists(Paths.get(SUMMARY_FILE));
    }

    @Test
    void testProcessBatchCreatesSummaryFile() {
        Main.processBatch(TEST_FILENAME);
        assertTrue(Files.exists(Paths.get(SUMMARY_FILE)), "Summary file should be created");
    }

    @Test
    void testProcessBatchSummaryContents() throws IOException {
        Main.processBatch(TEST_FILENAME);
        String content = Files.readString(Paths.get(SUMMARY_FILE));
        assertTrue(content.contains("Total readings: 10"), "Total readings assertion failed");
        assertTrue(content.contains("Valid readings: 10"), "Valid readings assertion failed");
        assertTrue(content.contains("Errors: 0"), "Errors assertion failed");
    }
}
