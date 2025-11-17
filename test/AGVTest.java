import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JTextArea;
import java.lang.reflect.Field;

public class AGVTest {
    private AGV agv;
    private Box testBox;
    private StorageArea testStorageArea;

   @BeforeAll
    static void initMainUI() {
        try {
            // Initialize MainUI to prevent null pointer
            Field outputAreaField = MainUI.class.getDeclaredField("outputArea");
            outputAreaField.setAccessible(true);
            outputAreaField.set(null, new JTextArea());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() { 
        agv = new AGV("A1", new FloorPosition(10, 10), true);
        testBox = new Box("B1", "5", "TEST");
        testStorageArea = new StorageArea(5, 5);
    }

    @Test
    void testAGVInitialization() {
        assertEquals("A1", agv.getId(), "AGV ID should be set correctly");
        assertNotNull(agv.getBattery(), "AGV battery should be initialized");

        assertEquals(10, agv.getFloorPosition().getRow(), "Initial row should be 10");
        assertEquals(10, agv.getFloorPosition().getCol(), "Initial column should be 10");
        System.out.println("Passed");
    }

    @Test
    void testMoveToValidPosition() {
        FloorPosition target = new FloorPosition(20, 30);
        agv.moveTo(target);

        assertEquals(target.getRow(), agv.getFloorPosition().getRow(), "AGV should move to target row");
        assertEquals(target.getCol(), agv.getFloorPosition().getCol(), "AGV should move to target column");
        System.out.println("Passed");
    }

    @Test
    void testMoveToNullPosition() {
        assertDoesNotThrow(() -> agv.moveTo(null), "AGV should handle null target using exception");
        System.out.println("Passed");
    }

    @Test
    void testPickUpValidBox() {
        assertDoesNotThrow(() -> agv.pickUpBox(testBox), "AGV should pick up a valid box without exception");
        System.out.println("Passed");
    }

    @Test
    void testDropBoxIntoStorageArea() {
        FloorPosition slot = testStorageArea.findEmptySlot();
        assertNotNull(slot, "Should find an empty slot in storage area");
        testBox.setPosition(slot.getRow(), slot.getCol());

        agv.pickUpBox(testBox);
        assertDoesNotThrow(() -> agv.dropBox(testStorageArea), "AGV should drop the carried box safely");

        Box stored = testStorageArea.getBoxAt(slot.getRow(), slot.getCol());
        assertNotNull(stored, "Stored box should be present at the slot after drop");
        assertEquals(testBox.getId(), stored.getId(), "Stored box ID should match the dropped box");
        System.out.println("Passed");
    }
}
