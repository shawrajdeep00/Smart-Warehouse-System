import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextArea;
import java.lang.reflect.Field;

public class BoxTest {
    private Box box;

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
        box = new Box("B1", "5", "TEST");
    }

    @Test
    void testBoxInit() {
        assertEquals("B1", box.getId(), "Box ID should match");
        assertEquals("5", box.toString().contains("5") ? "5" : "", "Weight should be set correctly");
        assertEquals("TEST", box.toString().contains("TEST") ? "TEST" : "", "Content should match");
        System.out.println("Passed");
    }

    @Test
    void testSetPositionCoor() {
        box.setPosition(5, 8);
        assertEquals(5, box.getRow(), "Row should update correctly");
        assertEquals(8, box.getColumn(), "Column should update correctly");
        System.out.println("Passed");
    }

    @Test
    void testSetPositionNoThrow() {
        assertDoesNotThrow(() -> box.setPosition(9, 9));
        System.out.println("Passed");
    }

    @Test
    void testToStringContainsInfo() {
        String result = box.toString();
        assertTrue(result.contains("B1"), "toString() should include box ID");
        assertTrue(result.contains("5"), "toString() should include box weight");
        assertTrue(result.contains("TEST"), "toString() should include box content");
        System.out.println("Passed");
    }

    @Test
    void testDisplayInfoRunsWithoutError() {
        assertDoesNotThrow(() -> box.displayInfo(),
                "displayInfo() should run without throwing any exceptions");
        System.out.println("Passed");
    }
}
