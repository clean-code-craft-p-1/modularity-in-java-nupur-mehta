package temperature;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SRP: Responsible only for computing statistical summaries over temperature records.
 */
public class TemperatureStatistics {

    private final double max;
    private final double min;
    private final double average;

    public TemperatureStatistics(List<TemperatureRecord> records) {
        List<Double> temps = records.stream()
                .map(TemperatureRecord::getTemperature)
                .collect(Collectors.toList());
        this.max = Collections.max(temps);
        this.min = Collections.min(temps);
        this.average = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAverage() {
        return average;
    }
}
