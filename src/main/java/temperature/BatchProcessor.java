package temperature;

import java.io.IOException;
import java.util.List;

/**
 * SRP: Orchestrates the batch processing pipeline — reading, parsing, stats, reporting.
 * DIP: Depends on the ReportWriter abstraction, not on any concrete implementation.
 */
public class BatchProcessor {

    private final TemperatureParser parser;
    private final ReportBuilder reportBuilder;
    private final ReportWriter reportWriter;

    public BatchProcessor(TemperatureParser parser, ReportBuilder reportBuilder, ReportWriter reportWriter) {
        this.parser = parser;
        this.reportBuilder = reportBuilder;
        this.reportWriter = reportWriter;
    }

    public void process(String filename) {
        List<String> lines;
        try {
            lines = parser.readLines(filename);
        } catch (IOException e) {
            System.out.println("Error: File not found.");
            return;
        }

        ParseResult result = parser.parse(lines);

        if (result.getRecords().isEmpty()) {
            System.out.println("No valid temperature data found.");
            return;
        }

        TemperatureStatistics stats = new TemperatureStatistics(result.getRecords());
        String report = reportBuilder.build(filename, lines.size(), result.getRecords().size(),
                result.getErrorCount(), stats, result.getBadLines());

        System.out.println("============================================================");
        System.out.print(report);
        System.out.println("============================================================");

        String outName = filename + "_summary.txt";
        try {
            reportWriter.write(outName, report);
            System.out.println("Report saved to " + outName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
