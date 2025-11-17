class AGVException extends Exception {
    public AGVException(String message) {
        super(message);
    }
}

public class AGV extends Resource {
    private Battery battery;
    private Box carriedBox;
    private boolean isActive;
    private boolean isCharging = false;

    public AGV(String id, FloorPosition floorPosition, boolean isActive) {
        this.id = id;
        this.battery = new Battery();
        this.isActive = isActive;
        this.floorPosition  = floorPosition;
    }

    public Battery getBattery() {
        return battery;
    }

    public void moveTo(FloorPosition target) {
        try {
            if (target == null) {
                throw new AGVException("Target position cannot be null!");
            }
            System.out.println("[AGV] AGV#" + id + " moved from " + floorPosition  + " to " + target);
            MainUI.appendOutput("[AGV] AGV#" + id + " moved from " + floorPosition  + " to " + target);
            WarehouseAutomation.agvLog.log("[AGV] AGV#" + id + " moved from " + floorPosition  + " to " + target);
            WarehouseAutomation.systemLog.log("[AGV] AGV#" + id + " moved from " + floorPosition  + " to " + target);
            WarehouseAutomation.overallLog.log("[AGV] AGV#" + id + " moved from " + floorPosition  + " to " + target);

            this.floorPosition  = target;
            battery.discharge(2);
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        } catch (AGVException e) {
            System.err.println("[ERROR] AGV#" + id + ": " + e.getMessage());
            MainUI.appendOutput("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.agvLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
        }
    }

    public void pickUpBox(Box box) {
        try {
            if (box == null) {
                throw new AGVException("Cannot pick up a null box!");
            }
            System.out.println("[AGV] AGV#" + id + " picked up Box#" + box.getId());
            MainUI.appendOutput("[AGV] AGV#" + id + " picked up Box#" + box.getId());
            WarehouseAutomation.agvLog.log("[AGV] AGV#" + id + " picked up Box#" + box.getId());
            WarehouseAutomation.systemLog.log("[AGV] AGV#" + id + " picked up Box#" + box.getId());
            WarehouseAutomation.overallLog.log("[AGV] AGV#" + id + " picked up Box#" + box.getId());

            carriedBox = box;
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        } catch (AGVException e) {
            System.err.println("[ERROR] AGV#" + id + ": " + e.getMessage());
            MainUI.appendOutput("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.agvLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] AGV#" + id + ": " + e.getMessage());
        }
    }

    public void dropBox(StorageArea area) {
        try {
            if (carriedBox == null) {
                throw new AGVException("No box to drop!");
            }
            if (area == null) {
                throw new AGVException("Storage area is null!");
            }

            area.storeBox(carriedBox);
            carriedBox = null;
        } catch (AGVException e) {
            System.err.println("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    public void setActive(boolean state) {
        isActive = state;
    }

    public void setCharging(boolean charging) {
        this.isCharging = charging;
    }

    public boolean isCharging() {
        return isCharging;
    }

    @Override
    protected void displayInfo() {
        System.out.println("[INFO] AGV#" + id + " | Battery: " + battery.getLevelString() + "% | Active: " + isActive);
        MainUI.appendOutput("[INFO] AGV#" + id + " | Battery: " + battery.getLevelString() + "% | Active: " + isActive);

        WarehouseAutomation.agvLog.log("[INFO] AGV#" + id + " | Battery: " + battery.getLevelString() + "% | Active: " + isActive);
        WarehouseAutomation.batteryLog.log("[INFO] AGV#" + id + " | Battery: " + battery.getLevelString() + "% | Active: " + isActive);
        WarehouseAutomation.overallLog.log("[INFO] AGV#" + id + " | Battery: " + battery.getLevelString() + "% | Active: " + isActive);
    }
}
