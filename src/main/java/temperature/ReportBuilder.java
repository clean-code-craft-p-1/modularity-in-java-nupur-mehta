package temperature;

import java.util.List;

/**
 * SRP: Responsible only for building the text content of a temperature report.
 */
public class ReportBuilder {

    public String build(String filename, int totalLines, int validCount,
                        int errors, TemperatureStatistics stats, List<String> badLines) {
        StringBuilder sb = new StringBuilder();
        sb.append("Temperature Analysis Summary\n");
        sb.append("==================================================\n");
        sb.append("File analyzed: ").append(filename).append("\n");
        sb.append("Total readings: ").append(totalLines).append("\n");
        sb.append("Valid readings: ").append(validCount).append("\n");
        sb.append("Errors: ").append(errors).append("\n");
        sb.append(String.format("Max temperature: %.2f%n", stats.getMax()));
        sb.append(String.format("Min temperature: %.2f%n", stats.getMin()));
        sb.append(String.format("Average temperature: %.2f%n", stats.getAverage()));
        sb.append("------------------------------------------------------------\n");
        if (errors > 0) {
            sb.append("\nInvalid lines:\n");
            for (String badLine : badLines) {
                sb.append(badLine).append("\n");
            }
        }
        return sb.toString();
    }
}
