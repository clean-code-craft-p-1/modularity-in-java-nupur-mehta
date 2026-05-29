package temperature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureRecordTest {

    @Test
    void testGetTimestamp() {
        TemperatureRecord record = new TemperatureRecord("09:15:30", 23.5);
        assertEquals("09:15:30", record.getTimestamp());
    }

    @Test
    void testGetTemperature() {
        TemperatureRecord record = new TemperatureRecord("09:15:30", 23.5);
        assertEquals(23.5, record.getTemperature(), 0.001);
    }

    @Test
    void testNegativeTemperature() {
        TemperatureRecord record = new TemperatureRecord("01:00:00", -40.0);
        assertEquals(-40.0, record.getTemperature(), 0.001);
    }

    @Test
    void testZeroTemperature() {
        TemperatureRecord record = new TemperatureRecord("00:00:00", 0.0);
        assertEquals(0.0, record.getTemperature(), 0.001);
    }
}
