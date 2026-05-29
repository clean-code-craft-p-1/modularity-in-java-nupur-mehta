package temperature;

import java.io.IOException;

/**
 * DIP: High-level modules depend on this abstraction, not on a concrete file writer.
 */
public interface ReportWriter {
    void write(String filename, String content) throws IOException;
}
