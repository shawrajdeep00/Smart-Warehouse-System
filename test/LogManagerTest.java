import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.*;

public class LogManagerTest {

    private static final String TEST_LOG_PATH = "test_logs/";
    private String testFolder;

    @BeforeEach
    void setup() throws Exception {

        testFolder = TEST_LOG_PATH + System.nanoTime() + "/";
        Path base = Paths.get("Logs").resolve(testFolder.replaceAll("/+$", ""));
        if (Files.exists(base)) {
            Files.walk(base)
                    .sorted(java.util.Comparator.reverseOrder())
                    .forEach(p -> { try { Files.deleteIfExists(p); } catch (IOException ignored) {} });
        }
        Files.createDirectories(base);
    }

    // After each test, delete the folder and files created to keep everything tidy
    @AfterEach
    void cleanup() throws Exception {
        Path base = Paths.get("Logs").resolve(testFolder.replaceAll("/+$", ""));
        if (Files.exists(base)) {
            Files.walk(base)
                    .sorted(java.util.Comparator.reverseOrder())
                    .forEach(p -> { try { Files.deleteIfExists(p); } catch (IOException ignored) {} });
        }
    }

    //Test 1: Creating a log in valid folder should actually create a file
    @Test
    void testValidPathCreatesLogFile() throws Exception {
        LogManager log = new LogManager(testFolder);
        log.initializeLog();

        java.lang.reflect.Field f = LogManager.class.getDeclaredField("currentLogFile");
        f.setAccessible(true);
        File logFile = (File) f.get(log);

        assertNotNull(logFile, "Log file should be created");
        assertTrue(logFile.exists(), "Log file should exist in valid folder");

        log.closeLog();
    }

    //Test 2: Trying to log to an invalid path should not crash the system
    @Test
    void testInvalidPathHandling() {
        String invalidPath = "?:/invalidpath/";
        LogManager log = new LogManager(invalidPath);

        try {
            log.initializeLog();
        } catch (Exception e) {
            // it is okay for this to fail, as long as the whole test  stays running
        }
    }

    //Test 3: Logging file can be closed safely, even if already closed
    @Test
    void testLogFileClose() throws Exception {
        LogManager log = new LogManager(testFolder);
        log.initializeLog();

        // Close twice to ensure safe shutdown
        log.closeLog();
        assertDoesNotThrow(log::closeLog, "Closing an already closed log shouldn't throw errors");
    }

    //Test 4: After closing a log, its file can be moved to another location
    @Test
    void testFileMoveOperation() throws Exception {
        LogManager log = new LogManager(testFolder);
        log.initializeLog();

        java.lang.reflect.Field f = LogManager.class.getDeclaredField("currentLogFile");
        f.setAccessible(true);
        File original = (File) f.get(log);

        log.closeLog();
        Thread.sleep(500);

        File moved = new File("Logs/" + TEST_LOG_PATH + "moved_log.txt");
        Files.move(original.toPath(), moved.toPath(), StandardCopyOption.REPLACE_EXISTING);

        assertTrue(moved.exists(), "Log file should be successfully moved");
    }

    //Test 5: Logging before calling init should not crash anything
    @Test
    void testLoggingBeforeInitialization() {
        LogManager log = new LogManager(TEST_LOG_PATH);
        assertDoesNotThrow(() -> log.log("Logging without init"),
                "Logging before initialization should not crash");
    }
}