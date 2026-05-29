package temperature;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReportWriterTest {

    private static final String OUT_FILE = "writer_test_output.txt";
    private final FileReportWriter writer = new FileReportWriter();

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(OUT_FILE));
    }

    @Test
    void testWriteCreatesFile() throws IOException {
        writer.write(OUT_FILE, "some content");
        assertTrue(Files.exists(Paths.get(OUT_FILE)));
    }

    @Test
    void testWriteContentIsCorrect() throws IOException {
        writer.write(OUT_FILE, "Temperature: 23.5");
        String content = Files.readString(Paths.get(OUT_FILE));
        assertEquals("Temperature: 23.5", content);
    }

    @Test
    void testWriteOverwritesExistingFile() throws IOException {
        writer.write(OUT_FILE, "first write");
        writer.write(OUT_FILE, "second write");
        String content = Files.readString(Paths.get(OUT_FILE));
        assertEquals("second write", content);
    }

    @Test
    void testWriteEmptyContent() throws IOException {
        writer.write(OUT_FILE, "");
        String content = Files.readString(Paths.get(OUT_FILE));
        assertEquals("", content);
    }
}
