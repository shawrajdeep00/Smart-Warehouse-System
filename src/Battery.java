class BatteryException extends Exception {
    public BatteryException(String message) {
        super(message);
    }
}

public class Battery {
    private double level;

    public Battery() {
        level = Math.random() * 10 + 90; // start with 90-100%
    }

    public void discharge(double amount) {
        try {
            if (amount < 0) throw new BatteryException("Discharge amount cannot be negative!");
            level = Math.max(0, level - amount);

            System.out.println("[BATTERY] Discharged " + amount + "%. Current level: " + String.format("%.1f", level) + "%");
            MainUI.appendOutput("[BATTERY] Discharged " + amount + "%. Current level: " + String.format("%.1f", level) + "%");
            WarehouseAutomation.batteryLog.log("[BATTERY] Discharged " + amount + "%. Current level: " + String.format("%.1f", level) + "%");
            WarehouseAutomation.overallLog.log("[BATTERY] Discharged " + amount + "%. Current level: " + String.format("%.1f", level) + "%");
        } catch (BatteryException e) {
            System.err.println("[BATTERY ERROR] " + e.getMessage());
        }
    }

    public void recharge() {
        try {
            if (level >= 100) throw new BatteryException("Battery is already full!");
            System.out.println("[BATTERY] Charging started...");
            MainUI.appendOutput("[BATTERY] Charging started...");
            WarehouseAutomation.batteryLog.log("[BATTERY] Charging started...");
            WarehouseAutomation.overallLog.log("[BATTERY] Charging started...");

            while (level < 100) {
                level = Math.min(level + 20, 100); // increase by 20
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

                System.out.println("[BATTERY] Battery level: " + String.format("%.1f", level) + "%");
                MainUI.appendOutput("[BATTERY] Battery level: " + String.format("%.1f", level) + "%");
                WarehouseAutomation.batteryLog.log("[BATTERY] Battery level: " + String.format("%.1f", level) + "%");
                WarehouseAutomation.overallLog.log("[BATTERY] Battery level: " + String.format("%.1f", level) + "%");
            }
            System.out.println("[BATTERY] Battery fully charged!");
            MainUI.appendOutput("[BATTERY] Battery fully charged!");
            WarehouseAutomation.batteryLog.log("[BATTERY] Battery fully charged!");
            WarehouseAutomation.overallLog.log("[BATTERY] Battery fully charged!");

        } catch (BatteryException e) {
            System.err.println("[BATTERY ERROR] " + e.getMessage());
        }
    }

    public boolean isLow() {
        return level < 30;
    }

    public double getLevel() {
        return level;
    }

    public String getLevelString() {
        return String.format("%.1f", level);
    }
}
