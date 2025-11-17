import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

//just small dummy classes for testing
class DummyBox extends Box {
    public DummyBox() {
        super("DUMMY", "0", "dummy");
    }
}

class DummyProcess extends Process {
    public DummyProcess() {
        super(new AGV("D-AGV-1", new FloorPosition(0, 0), true), new AGV("D-AGV-2", new FloorPosition(0, 0), true), new Box("D-B", "0", "dummy"), new StorageArea(1,1), new ChargingStation("CS-DUMMY", new FloorPosition(0,0)));
    }

    // Methods just to check testing works
    public boolean storeBox(Box box) {
        System.out.println("Simulating storing box...");
        return true;
    }

    public Box retrieveBox(String id) {
        System.out.println("Simulating retrieving box...");
        return new DummyBox();
    }

    public boolean swapAGVState() {
        System.out.println("Simulating AGV state swap...");
        return true;
    }

    @Override
    protected void execute() throws ProcessException {
        this.status = "Done";
    }

    @Override
    protected void logProcess() {
    }
}

public class ProcessTest {

    private DummyProcess process;
    private DummyBox testBox;

    @BeforeEach
    void setup() {
        process = new DummyProcess();
        testBox = new DummyBox();
    }

    //1. Check if storing box works
    @Test
    void testStoreBox() {
        boolean result = process.storeBox(testBox);
        assertTrue(result, "Box should store properly");
        System.out.println("Test 1 Passed: Box stored successfully");
    }

    //2. Check if storing same box twice does not crash
    @Test
    void testDuplicateBoxStorage() {
        process.storeBox(testBox);
        boolean result = process.storeBox(testBox);
        assertTrue(result, "System should handle duplicate box without crashing");
        System.out.println("Test 2 Passed: Duplicate handled");
    }

    //3. check retrive function
    @Test
    void testRetrieveBox() {
        Box retrieved = process.retrieveBox("BOX1");
        assertNotNull(retrieved, "Box should be retrieved");
        System.out.println("Test 3 Passed: Box retrieved properly");
    }

    //4. try wrong box id
    @Test
    void testRetrieveInvalidBox() {
        Box result = process.retrieveBox("INVALID");
        assertNotNull(result, "Should handle non-existent box safely (returns dummy)");
        System.out.println("Test 4 Passed: Handled missing box safely");
    }

    //5. swapping AGVs
    @Test
    void testAGVSwapState() {
        boolean swapped = process.swapAGVState();
        assertTrue(swapped, "AGV state should swap successfully");
        System.out.println("Test 5 Passed: AGV swap simulated");
    }
}