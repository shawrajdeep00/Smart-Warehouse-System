import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JTextArea;
import java.lang.reflect.Field;

public class StorageAreaTest {
    private StorageArea storageArea;
    private Box testBox;

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
        storageArea = new StorageArea(5, 5);
        testBox = new Box("B1", "5", "TEST");
    }

    @Test
    void testFindEmptySlot() {
        FloorPosition slot = storageArea.findEmptySlot();
        assertNotNull(slot);
        System.out.println("Passed");
    }

    @Test
    void testStoreAndGet() {
        FloorPosition floorPosition = new FloorPosition(0, 0);
        testBox.setPosition(floorPosition.getRow(), floorPosition.getCol());
        storageArea.storeBox(testBox);

        Box gotTestBox = storageArea.getBoxAt(0, 0);
        assertNotNull(gotTestBox);
        assertEquals("B1", gotTestBox.getId());
        System.out.println("Passed");
    }

    @Test
    void testFindBoxById() {
        testBox.setPosition(1, 2);
        storageArea.storeBox(testBox);

        FloorPosition foundTestBox = storageArea.findBoxById("B1");
        assertNotNull(foundTestBox);
        assertEquals(1, foundTestBox.getRow());
        assertEquals(2, foundTestBox.getCol());
        System.out.println("Passed");
    }

    @Test
    void testRetrieveBox() {
        testBox.setPosition(1, 1);
        storageArea.storeBox(testBox);

        Box retrievedTestBox = storageArea.retrieveBox(1, 1);
        assertNotNull(retrievedTestBox);
        assertEquals("B1", retrievedTestBox.getId());
        assertNull(storageArea.getBoxAt(1, 1), "Slot should be empty after retrieval");
        System.out.println("Passed");
    }

    @Test
    void testGetBoxAtOutOfArea() {
        assertNull(storageArea.getBoxAt(-1, 0));
        assertNull(storageArea.getBoxAt(0, -1));
        assertNull(storageArea.getBoxAt(10, 0));
        assertNull(storageArea.getBoxAt(0, 10));
        System.out.println("Passed");
    }
}
