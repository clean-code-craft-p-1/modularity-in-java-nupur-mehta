package temperature;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TemperatureParserTest {

    private static final String TEST_FILE = "parser_test.csv";
    private final TemperatureParser parser = new TemperatureParser();

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    // --- readLines ---

    @Test
    void testReadLinesReturnsAllLines() throws IOException {
        try (PrintWriter w = new PrintWriter(new FileWriter(TEST_FILE))) {
            w.println("09:00:00,20.0");
            w.println("09:01:00,21.0");
        }
        List<String> lines = parser.readLines(TEST_FILE);
        assertEquals(2, lines.size());
    }

    @Test
    void testReadLinesMissingFileThrowsIOException() {
        assertThrows(IOException.class, () -> parser.readLines("nonexistent_file.csv"));
    }

    // --- parse: valid data ---

    @Test
    void testParseValidLines() {
        List<String> lines = Arrays.asList("09:00:00,20.0", "09:01:00,21.5");
        ParseResult result = parser.parse(lines);
        assertEquals(2, result.getRecords().size());
        assertEquals(0, result.getErrorCount());
    }

    @Test
    void testParseRecordValues() {
        List<String> lines = Arrays.asList("09:15:30,23.5");
        ParseResult result = parser.parse(lines);
        TemperatureRecord record = result.getRecords().get(0);
        assertEquals("09:15:30", record.getTimestamp());
        assertEquals(23.5, record.getTemperature(), 0.001);
    }

    // --- parse: invalid formats ---

    @Test
    void testParseSkipsEmptyLines() {
        List<String> lines = Arrays.asList("", "  ", "09:00:00,20.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getRecords().size());
        assertEquals(0, result.getErrorCount());
    }

    @Test
    void testParseMissingCommaIsError() {
        List<String> lines = Arrays.asList("09:00:00 20.0");
        ParseResult result = parser.parse(lines);
        assertEquals(0, result.getRecords().size());
        assertEquals(1, result.getErrorCount());
    }

    @Test
    void testParseInvalidTimestampFormatIsError() {
        List<String> lines = Arrays.asList("0900,20.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getErrorCount());
    }

    @Test
    void testParseNonNumericTemperatureIsError() {
        List<String> lines = Arrays.asList("09:00:00,abc");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getErrorCount());
    }

    // --- parse: boundary temperatures ---

    @Test
    void testParseTempAbove200IsError() {
        List<String> lines = Arrays.asList("09:00:00,201.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getErrorCount());
    }

    @Test
    void testParseTempBelow100NegativeIsError() {
        List<String> lines = Arrays.asList("09:00:00,-101.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getErrorCount());
    }

    @Test
    void testParseBoundaryTempExactly200IsValid() {
        List<String> lines = Arrays.asList("09:00:00,200.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getRecords().size());
        assertEquals(0, result.getErrorCount());
    }

    @Test
    void testParseBoundaryTempExactlyMinus100IsValid() {
        List<String> lines = Arrays.asList("09:00:00,-100.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getRecords().size());
        assertEquals(0, result.getErrorCount());
    }

    @Test
    void testParseBadLinesAreRecorded() {
        List<String> lines = Arrays.asList("badline", "09:00:00,20.0");
        ParseResult result = parser.parse(lines);
        assertEquals(1, result.getBadLines().size());
        assertTrue(result.getBadLines().get(0).contains("badline"));
    }
}
