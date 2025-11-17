import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class chargingstation
public class ChargingStationTest {

    // Test: ChargingStation object can be created without any errors
    @Test
    public void testChargingStationCreation() {
        ChargingStation station = new ChargingStation("CS-01", new FloorPosition(0, 0));
        assertNotNull(station);  // station object should not be null
    }

    // Test: Assigning an AGv to a charging station and check whether its sent
    @Test
    public void testAssignAGVToChargingStation() throws ChargingStationException {
        ChargingStation station = new ChargingStation("CS-02", new FloorPosition(1, 1));
        AGV agv = new AGV("AGV-1", new FloorPosition(0, 0), true);
        station.assignAGV(agv);
        // AGV's position should now be (1,1), same as the station's position
        assertEquals(1, agv.getFloorPosition().getRow());
        assertEquals(1, agv.getFloorPosition().getCol());
    }

    // Test: test whether station occupied (WHile charging) and not occupied (while not charging)
    @Test
    public void testStationOccupiedLifecycle() throws ChargingStationException {
        ChargingStation station = new ChargingStation("CS-03", new FloorPosition(2, 3));
        AGV agv = new AGV("AGV-1", new FloorPosition(0, 0), true);

        station.assignAGV(agv);
        assertTrue(station.isOccupied(), "Station should be occupied after assigning AGV");

        station.chargeAGV(agv);
        assertFalse(station.isOccupied(), "Station should not be occupied after charging finishes");
    }

    // Test: Charging an AGV fully or near fully charges its battery
    @Test
    public void testAGVBatteryCharging() throws ChargingStationException {
        ChargingStation station = new ChargingStation("CS-04", new FloorPosition(3, 3));
        AGV agv = new AGV("AGV-1", new FloorPosition(0, 0), true);

        station.assignAGV(agv);
        station.chargeAGV(agv);

        double batteryLevel = Double.parseDouble(agv.getBattery().getLevelString());
        // Battery level should be 99% or higher after charging
        assertTrue(batteryLevel >= 99.0, "Battery should be almost full after charging");
    }

    // Test: Two AGVs assigned should both get fully charged
    @Test
    public void testChargingMultipleAGVs() throws ChargingStationException {
        ChargingStation station = new ChargingStation("CS-05", new FloorPosition(4, 4));
        AGV agv1 = new AGV("AGV-1", new FloorPosition(0, 0), true);
        AGV agv2 = new AGV("AGV-2", new FloorPosition(0, 0), true);

        station.assignAGV(agv1);
        station.chargeAGV(agv1);
        double level1 = Double.parseDouble(agv1.getBattery().getLevelString());
        assertTrue(level1 >= 99.0, "First AGV battery should be fully charged");

        station.assignAGV(agv2);
        station.chargeAGV(agv2);
        double level2 = Double.parseDouble(agv2.getBattery().getLevelString());
        assertTrue(level2 >= 99.0, "Second AGV battery should be fully charged");
    }


}
