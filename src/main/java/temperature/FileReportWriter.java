package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * SRP: Responsible only for persisting a report string to a file.
 * OCP/LSP: Concrete implementation of ReportWriter — can be swapped without touching BatchProcessor.
 */
public class FileReportWriter implements ReportWriter {

    @Override
    public void write(String filename, String content) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.print(content);
        }
    }
}
