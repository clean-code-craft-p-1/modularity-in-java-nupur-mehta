package temperature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TemperatureStatisticsTest {

    private List<TemperatureRecord> records(double... temps) {
        List<TemperatureRecord> list = new java.util.ArrayList<>();
        int i = 0;
        for (double t : temps) {
            list.add(new TemperatureRecord("0" + i + ":00:00", t));
            i++;
        }
        return list;
    }

    @Test
    void testMaxTemperature() {
        TemperatureStatistics stats = new TemperatureStatistics(records(10.0, 25.0, 18.0));
        assertEquals(25.0, stats.getMax(), 0.001);
    }

    @Test
    void testMinTemperature() {
        TemperatureStatistics stats = new TemperatureStatistics(records(10.0, 25.0, 18.0));
        assertEquals(10.0, stats.getMin(), 0.001);
    }

    @Test
    void testAverageTemperature() {
        TemperatureStatistics stats = new TemperatureStatistics(records(10.0, 20.0, 30.0));
        assertEquals(20.0, stats.getAverage(), 0.001);
    }

    @Test
    void testSingleRecord() {
        TemperatureStatistics stats = new TemperatureStatistics(records(42.0));
        assertEquals(42.0, stats.getMax(), 0.001);
        assertEquals(42.0, stats.getMin(), 0.001);
        assertEquals(42.0, stats.getAverage(), 0.001);
    }

    @Test
    void testNegativeTemperatures() {
        TemperatureStatistics stats = new TemperatureStatistics(records(-50.0, -10.0, -30.0));
        assertEquals(-10.0, stats.getMax(), 0.001);
        assertEquals(-50.0, stats.getMin(), 0.001);
        assertEquals(-30.0, stats.getAverage(), 0.001);
    }

    @Test
    void testAllSameTemperature() {
        TemperatureStatistics stats = new TemperatureStatistics(records(15.0, 15.0, 15.0));
        assertEquals(15.0, stats.getMax(), 0.001);
        assertEquals(15.0, stats.getMin(), 0.001);
        assertEquals(15.0, stats.getAverage(), 0.001);
    }
}
