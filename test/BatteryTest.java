import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



public class BatteryTest {

    private Battery battery = new Battery();

    // Test: Battery starts with a realistic charge (80%-100%)
    @Test
    void testInitialBatteryLevelRange() {
        double level = Double.parseDouble(battery.getLevelString());
        assertTrue(level >= 80 && level <= 100,
                "Battery level should start between 80% and 100%");
    }

    // Test: Discharging the battery decreases its charge
    @Test
    void testDischargeReducesLevel() {
        double before = Double.parseDouble(battery.getLevelString());
        battery.discharge(10);
        double after = Double.parseDouble(battery.getLevelString());
        assertTrue(after <= before, "Battery should reduce after discharge");
        //System.out.println("Battery level before: " + before + ", after discharging 10%: " + after);
    }

    // Test: Discharging by a negative value should not change the charge
    @Test
    void testDischargeWithNegativeValue() {
        double before = Double.parseDouble(battery.getLevelString());
        battery.discharge(-5); // Should not affect charge
        double after = Double.parseDouble(battery.getLevelString());
        assertEquals(before, after, "Battery should not change for negative discharge");
    }

    // Test: When battery is heavily discharged, it should indicate low status
    @Test
    void testIsLowBatteryDetection() {
        battery.discharge(90); // Drains most of the energy
        assertTrue(battery.isLow(), "Battery should be low after heavy discharge");
    }

    // Test: Battery string representation should be like '85.0'
    @Test
    void testGetLevelStringIsValid() {
        String levelStr = battery.getLevelString();
        assertTrue(levelStr.matches("\\d{2}\\.\\d"), "Battery string should be a number format like 85.0");
    }

    // Test: Recharging after draining should not throw errors
    @Test
    void testRechargeRunsSafely() {
        assertDoesNotThrow(() -> {
            battery.discharge(50);
            battery.recharge();
        }, "Recharge should run without throwing exceptions");
    }
}
