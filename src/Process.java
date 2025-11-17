class ProcessException extends Exception {
    public ProcessException(String message) {
        super(message);
    }
}

public abstract class Process extends Thread{
    protected AGV activeAGV;
    protected AGV standbyAGV;
    protected Box box;
    protected StorageArea storageArea;
    protected ChargingStation station;
    protected String status;
    protected FloorPosition PICKUP_POS = new FloorPosition(-1, -1);
    protected FloorPosition DROPOFF_POS = new FloorPosition(5, 5);

    public Process(AGV active, AGV standby, Box box, StorageArea area, ChargingStation station) {
        this.activeAGV = active;
        this.standbyAGV = standby;
        this.box = box;
        this.storageArea = area;
        this.station = station;
    }

    public AGV getActiveAGV(){
        return activeAGV;
    }

    public AGV getStandbyAGV(){
        return standbyAGV;
    }

    protected synchronized void log(String msg) {
        System.out.println("[PROCESS] " + msg);
        MainUI.appendOutput("[PROCESS] " + msg);
        WarehouseAutomation.systemLog.log("[PROCESS] " + msg);
        WarehouseAutomation.overallLog.log("[PROCESS] " + msg);
    }

    protected synchronized void checkAndSwapAGV() throws ProcessException {

        if (!activeAGV.getBattery().isLow()) {
            return; // active AGV still has enough charge — keep working.
        }

        if (activeAGV.getBattery().isLow()) {
            System.out.println("[AGV] AGV#" + activeAGV.getId() + " low on battery. Switching with standby AGV.");
            MainUI.appendOutput("[AGV] AGV#" + activeAGV.getId() + " low on battery. Switching with standby AGV.");
            WarehouseAutomation.agvLog.log("[AGV] AGV#" + activeAGV.getId() + " low on battery. Switching with standby AGV.");
            WarehouseAutomation.batteryLog.log("[AGV] AGV#" + activeAGV.getId() + " low on battery. Switching with standby AGV.");
            WarehouseAutomation.overallLog.log("[AGV] AGV#" + activeAGV.getId() + " low on battery. Switching with standby AGV.");

            // swap
            AGV temp = activeAGV;
            activeAGV = standbyAGV;
            standbyAGV = temp;
            activeAGV.setActive(true);
            standbyAGV.setActive(false);

            // send active to charge
            new Thread(() -> {
                try {
                    station.assignAGV(temp);
                    station.chargeAGV(temp);
                } catch (ChargingStationException e) {
                    throw new RuntimeException(e);
                }
            }).start();

            System.out.println("[AGV] Swapped AGVs. Active: AGV#" + activeAGV.getId() +
                    ", Standby: AGV#" + standbyAGV.getId());
            MainUI.appendOutput("[AGV] Swapped AGVs. Active: AGV#" + activeAGV.getId() +
                    ", Standby: AGV#" + standbyAGV.getId());
            WarehouseAutomation.agvLog.log("[AGV] Swapped AGVs. Active: AGV#" + activeAGV.getId() +
                    ", Standby: AGV#" + standbyAGV.getId());
            WarehouseAutomation.batteryLog.log("[AGV] Swapped AGVs. Active: AGV#" + activeAGV.getId() +
                    ", Standby: AGV#" + standbyAGV.getId());
            WarehouseAutomation.overallLog.log("[AGV] Swapped AGVs. Active: AGV#" + activeAGV.getId() +
                    ", Standby: AGV#" + standbyAGV.getId());
        }
    }

    protected abstract void execute() throws ProcessException;

    protected abstract void logProcess();

    @Override
    public void run() {
        try {
            execute();
            logProcess();
        } catch (ProcessException e) {
            log("Error: " + e.getMessage());
        }
    }
}

class Storing extends Process {
    public Storing(AGV active, AGV standby, Box box, StorageArea area, ChargingStation station) {
        super(active, standby, box, area, station);
    }

    @Override
    protected synchronized void execute() throws ProcessException {
        if (!activeAGV.getBattery().isLow()) {
            System.out.println("No need to swap the AGVs");; // active AGV still has enough charge — keep working.
        } else { checkAndSwapAGV(); }

        if (box == null) throw new ProcessException("No box to store!");

        activeAGV.moveTo(PICKUP_POS);
        activeAGV.pickUpBox(box);

        activeAGV.moveTo(box.getFloorPosition());
        activeAGV.dropBox(storageArea);

        activeAGV.getBattery().discharge(15);
        status = "Stored";
    }

    @Override
    protected void logProcess() {
        log(status + " Box#" + box.getId() + " by AGV#" + activeAGV.getId());
    }
}

class Retrieving extends Process {
    public Retrieving(AGV active, AGV standby, Box box, StorageArea area, ChargingStation station) {
        super(active, standby, box, area, station);
    }

    @Override
    protected synchronized void execute() throws ProcessException {
        if (!activeAGV.getBattery().isLow()) {
            System.out.println("No need to swap the AGVs");; // active AGV still has enough charge — keep working.
        } else { checkAndSwapAGV(); }
        if (box == null) throw new ProcessException("No box assigned for retrieval!");

        int row = box.getRow();
        int col = box.getColumn();

        activeAGV.moveTo(box.getFloorPosition());
        activeAGV.pickUpBox(box);

        Box retrieved = storageArea.retrieveBox(row, col);
        if (retrieved != null) {
            activeAGV.moveTo(DROPOFF_POS);
            activeAGV.getBattery().discharge(15);
            status = "Retrieved";
        } else {
            status = "Failed";
            throw new ProcessException("Box could not be retrieved from storage!");
        }
    }

    @Override
    protected void logProcess() {
        log(status + " Box#" + box.getId() + " by AGV#" + activeAGV.getId());
    }
}