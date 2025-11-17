import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class StorageSystemTest {

    private isBoxEntered boxEnteredSystem;
    private isBoxStored boxStoredSystem;
    private isBoxExited boxExitedSystem;
    private Box testBox;


    //Prepare test systems and a sample box, reset box counts
    @BeforeEach
    void setup() {
        boxEnteredSystem = new isBoxEntered();
        boxStoredSystem = new isBoxStored();
        boxExitedSystem = new isBoxExited();
        testBox = new Box("B1", "0", "test"); // the Box(id, weight, content) constructor

        // Reset counters before each test
        StorageSystem.totalBoxes = 0;
        StorageSystem.enteredCount = 0;
        StorageSystem.exitedCount = 0;
    }

    //1. Every box entry should increase both counters by 1
    @Test
    void testBoxEntryIncrementsCounters() {
        try {
            boxEnteredSystem.recordEvent(testBox);
        } catch (StorageException e) {
            fail("Unexpected StorageException during recordEvent: " + e.getMessage());
        }

        assertEquals(1, StorageSystem.enteredCount, "Entered count should increase to 1");
        assertEquals(1, StorageSystem.totalBoxes, "Total boxes should increase to 1");
        System.out.println("Test 1 Passed: Box entry updates counters properly");
    }

    //2. Two box entries should produce counts of 2
    @Test
    void testMultipleBoxEntries() {
        try {
            boxEnteredSystem.recordEvent(testBox);
            boxEnteredSystem.recordEvent(new Box("B2", "0", "another"));
        } catch (StorageException e) {
            fail("Unexpected StorageException during multiple entries: " + e.getMessage());
        }

        assertEquals(2, StorageSystem.enteredCount, "Two boxes should have entered");
        assertEquals(2, StorageSystem.totalBoxes, "Total boxes should be 2");
        System.out.println("Test 2 Passed: Multiple entries tracked properly");
    }

    //3. Recording a 'stored' event should not change box counts
    @Test
    void testBoxStorageDoesNotChangeCounters() {
        StorageSystem.totalBoxes = 2;
        try {
            boxStoredSystem.recordEvent(testBox);
        } catch (StorageException e) {
            fail("Unexpected StorageException during storage: " + e.getMessage());
        }

        assertEquals(2, StorageSystem.totalBoxes, "Total boxes should remain unchanged after storage");
        System.out.println("Test 3 Passed: Storing does not change counters");
    }

    // 4. Box exit should decrease total boxes and increase exited count
    @Test
    void testBoxExitDecrementsCounters() {
        StorageSystem.totalBoxes = 2;
        try {
            boxExitedSystem.recordEvent(testBox);
        } catch (StorageException e) {
            fail("Unexpected StorageException during exit: " + e.getMessage());
        }

        assertEquals(1, StorageSystem.totalBoxes, "Total boxes should decrease by 1 after exit");
        assertEquals(1, StorageSystem.exitedCount, "Exited count should increase by 1");
        System.out.println("Test 4 Passed: Exiting updates counters properly");
    }

    //5. Displaying logs should not throw any erros
    @Test
    void testDisplayLogRunsSafely() {
        assertDoesNotThrow(() -> {
            boxEnteredSystem.displayLog();
            boxStoredSystem.displayLog();
            boxExitedSystem.displayLog();
        }, "displayLog() should run safely without exceptions");
        System.out.println("Test 5 Passed: displayLog() runs safely");
    }

    // 6. Display log works after actions and reflects correct counters
    @Test
    void testDisplayLogAfterActions() {
        try {
            boxEnteredSystem.recordEvent(testBox);
            boxEnteredSystem.recordEvent(new Box("B2", "0", "another"));
            boxStoredSystem.recordEvent(testBox);
            boxExitedSystem.recordEvent(testBox);
        } catch (StorageException e) {
            fail("Unexpected StorageException");
        }
        assertEquals(2, StorageSystem.enteredCount);
        assertEquals(1, StorageSystem.totalBoxes);
        assertEquals(1, StorageSystem.exitedCount);
        assertDoesNotThrow(() -> {
            boxEnteredSystem.displayLog();
            boxStoredSystem.displayLog();
            boxExitedSystem.displayLog();
        });


    }
}
