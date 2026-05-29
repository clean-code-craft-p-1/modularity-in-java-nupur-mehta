package temperature;

public class Main {

    public static void processBatch(String filename) {
        new BatchProcessor(
                new TemperatureParser(),
                new ReportBuilder(),
                new FileReportWriter()
        ).process(filename);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java temperature.Main <filename>");
            return;
        }
        processBatch(args[0]);
    }
}