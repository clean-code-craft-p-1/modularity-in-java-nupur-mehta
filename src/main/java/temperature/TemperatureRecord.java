package temperature;

public class TemperatureRecord {

    private final String timestamp;
    private final double temperature;

    public TemperatureRecord(String timestamp, double temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }
}
