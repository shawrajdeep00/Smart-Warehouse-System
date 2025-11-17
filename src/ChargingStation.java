class ChargingStationException extends Exception {
    public ChargingStationException(String message) {
        super(message);
    }
}

public class ChargingStation extends Thread {
    private String stationId;
    private boolean isOccupied;
    private FloorPosition floorPosition;

    public ChargingStation(String id, FloorPosition pos) {
        this.stationId = id;
        this.floorPosition = pos;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public synchronized void assignAGV(AGV agv) throws ChargingStationException {
        long startWait = System.currentTimeMillis();

        while (isOccupied) {
            long waited = System.currentTimeMillis() - startWait;
            if (waited > 15 * 60 * 1000) { // 15 minutes timeout
                throw new ChargingStationException("AGV waited >15 min, leaving queue");
            }
            try {
                wait(1000); // wait 1 sec before checking again
            } catch (InterruptedException e) {
                throw new ChargingStationException("Interrupted while waiting for station");
            }
        }

        isOccupied = true;
        agv.moveTo(floorPosition);

        // Logging
        System.out.println("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);
        MainUI.appendOutput("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);
        WarehouseAutomation.agvLog.log("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);
        WarehouseAutomation.batteryLog.log("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);
        WarehouseAutomation.systemLog.log("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);
        WarehouseAutomation.overallLog.log("[AGV] AGV#" + agv.getId() + " assigned to Charging Station#" + stationId);

        notifyAll(); // wake up any thread waiting for this station
    }


    public synchronized void chargeAGV(AGV agv) throws ChargingStationException {
        if (agv == null) {
            throw new ChargingStationException("No AGV assigned to charge at Station#" + stationId);
        }
        // Recharge battery
        agv.getBattery().recharge();
        // Free the station
        isOccupied = false;
        notifyAll();

        System.out.println("[AGV] AGV#" + agv.getId() + " fully charged at Station#" + stationId);
        MainUI.appendOutput("[AGV] AGV#" + agv.getId() + " fully charged at Station#" + stationId);
        WarehouseAutomation.agvLog.log("[AGV] AGV#" + agv.getId() + " fully charged at Station#" + stationId);
        WarehouseAutomation.batteryLog.log("[AGV] AGV#" + agv.getId() + " fully charged at Station#" + stationId);
        WarehouseAutomation.overallLog.log("[AGV] AGV#" + agv.getId() + " fully charged at Station#" + stationId);
    }

}

