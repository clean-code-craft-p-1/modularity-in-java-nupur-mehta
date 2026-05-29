package temperature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParseResultTest {

    @Test
    void testGetRecords() {
        List<TemperatureRecord> records = Arrays.asList(
                new TemperatureRecord("09:00:00", 20.0),
                new TemperatureRecord("09:01:00", 21.0)
        );
        ParseResult result = new ParseResult(records, 0, Collections.emptyList());
        assertEquals(2, result.getRecords().size());
    }

    @Test
    void testGetErrorCount() {
        ParseResult result = new ParseResult(Collections.emptyList(), 3, Collections.emptyList());
        assertEquals(3, result.getErrorCount());
    }

    @Test
    void testGetBadLines() {
        List<String> badLines = Arrays.asList("  Line 1: bad", "  Line 2: alsoBad");
        ParseResult result = new ParseResult(Collections.emptyList(), 2, badLines);
        assertEquals(2, result.getBadLines().size());
        assertTrue(result.getBadLines().contains("  Line 1: bad"));
    }

    @Test
    void testZeroErrors() {
        ParseResult result = new ParseResult(Collections.emptyList(), 0, Collections.emptyList());
        assertEquals(0, result.getErrorCount());
        assertTrue(result.getBadLines().isEmpty());
    }
}
