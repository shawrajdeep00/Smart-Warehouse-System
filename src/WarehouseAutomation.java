import javax.swing.*;

public class WarehouseAutomation {
    private StorageArea area = new StorageArea(5, 5);
    private AGV storingActive = new AGV("1", new FloorPosition(5, 1), true);
    private AGV storingStandby = new AGV("2", new FloorPosition(5, 2), false);
    private AGV retrievingActive = new AGV("3", new FloorPosition(5, 3), true);
    private AGV retrievingStandby = new AGV("4", new FloorPosition(5, 4), false);
    private ChargingStation station = new ChargingStation("CS1", new FloorPosition(0, 5));
    private ChargingStation station2 = new ChargingStation("CS2", new FloorPosition(1, 5));

    private isBoxEntered enteredLog = new isBoxEntered();
    private isBoxStored storedLog = new isBoxStored();
    private isBoxExited exitedLog = new isBoxExited();

    public static LogManager agvLog = new LogManager("AGV");
    public static LogManager batteryLog = new LogManager("Battery");
    public static LogManager systemLog = new LogManager("System");
    public static LogManager overallLog = new LogManager("Overall");

    public static void main(String[] args) {
        try {
            agvLog.initializeLog();
            batteryLog.initializeLog();
            systemLog.initializeLog();
            overallLog.initializeLog();

            systemLog.log("[INFO] Warehouse automation simulation started.");
            overallLog.log("[INFO] Warehouse automation simulation started.");
            systemLog.log("[INFO] Storage area is 5x5");
            overallLog.log("[INFO] Storage area is 5x5");

            JFrame frame = new JFrame("DHL Warehouse Simulator");
            frame.setContentPane(new MainUI());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Add a window listener
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    agvLog.log("[INFO] Simulation completed successfully.");
                    batteryLog.log("[INFO] Simulation completed successfully.");
                    systemLog.log("[INFO] Simulation completed successfully.");
                    overallLog.log("[INFO] Simulation completed successfully.");

                    // Close all static logs
                    if (agvLog != null) agvLog.closeLog();
                    if (batteryLog != null) batteryLog.closeLog();
                    if (systemLog != null) systemLog.closeLog();
                    if (overallLog != null) overallLog.closeLog();

                    agvLog.archiveLog();
                    batteryLog.archiveLog();
                    systemLog.archiveLog();
                    overallLog.archiveLog();

                    System.out.println("Logs closed. Exiting application.");
                }
            });
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
        }
    }

    public void storeBox(String id, String weight, String desc) {
        try {
            if (area.findBoxById(id) != null) {
                System.err.println("[ERROR] Box ID already exists!");
                MainUI.appendOutput("[ERROR] Box ID already exists!");
                WarehouseAutomation.systemLog.log("[ERROR] Box ID already exists!");
                WarehouseAutomation.overallLog.log("[ERROR] Box ID already exists!");
                return;
            }

            if (!weight.matches("\\d+(\\.\\d+)?")) {
                System.err.println("[ERROR] Weight must be numeric!");
                MainUI.appendOutput("[ERROR] Weight must be numeric!");
                WarehouseAutomation.systemLog.log("[ERROR] Invalid weight: " + weight);
                WarehouseAutomation.overallLog.log("[ERROR] Invalid weight: " + weight);
                return;
            }

            Box userBox = new Box(id, weight, desc);
            System.out.println("[EVENT] Box#" + id + " is being stored...");
            MainUI.appendOutput("[EVENT] Box#" + id + " is being stored...");
            WarehouseAutomation.systemLog.log("[EVENT] Box#" + id + " is being stored...");
            WarehouseAutomation.overallLog.log("[EVENT] Box#" + id + " is being stored...");

            FloorPosition emptySlot = area.findEmptySlot();
            if (emptySlot == null) {
                throw new ProcessException("Storage area is full! Cannot store Box#" + id);
            }
            userBox.setPosition(emptySlot.getRow(), emptySlot.getCol());
            enteredLog.recordEvent(userBox);

            Storing storeProcess = new Storing(storingActive, storingStandby, userBox, area, station);
            storeProcess.start();
            storeProcess.join();  // wait until storing finishes
            // update WarehouseAutomation references
            storingActive = storeProcess.getActiveAGV();
            storingStandby = storeProcess.getStandbyAGV();

            storedLog.recordEvent(userBox);

        } catch (ProcessException | StorageException | InterruptedException pe) {
            System.err.println("[PROCESS ERROR] " + pe.getMessage());
            MainUI.appendOutput("[PROCESS ERROR] " + pe.getMessage());
            systemLog.log("[PROCESS ERROR] " + pe.getMessage());
            overallLog.log("[PROCESS ERROR] " + pe.getMessage());
        }
    }

    public void retrieveBox(String id) {
        try {
            FloorPosition boxPos = area.findBoxById(id);
            System.out.println("[EVENT] Box#" + id + " is being retrieved...");
            MainUI.appendOutput("[EVENT] Box#" + id + " is being retrieved...");
            WarehouseAutomation.systemLog.log("[EVENT] Box#" + id + " is being retrieved...");
            WarehouseAutomation.overallLog.log("[EVENT] Box#" + id + " is being retrieved...");

            if (boxPos != null) {
                Box storedBox = area.getBoxAt(boxPos.getRow(), boxPos.getCol());
                storedBox.setPosition(boxPos.getRow(), boxPos.getCol());

                Retrieving retrieveProcess = new Retrieving(retrievingActive, retrievingStandby, storedBox, area, station2);
                retrieveProcess.start();
                retrieveProcess.join();  // wait until retrieval finishes
                retrievingActive = retrieveProcess.getActiveAGV();
                retrievingStandby = retrieveProcess.getStandbyAGV();

                exitedLog.recordEvent(storedBox);
            } else {
                throw new ProcessException("Box with ID " + id + " not found in storage.");
            }
        } catch (ProcessException | StorageException| InterruptedException pe) {
            System.err.println("[PROCESS ERROR] " + pe.getMessage());
            MainUI.appendOutput("[PROCESS ERROR] " + pe.getMessage());
            systemLog.log("[PROCESS ERROR] " + pe.getMessage());
            overallLog.log("[PROCESS ERROR] " + pe.getMessage());
        }
    }

    public void displayInfo() {
        // Storage info
        System.out.println("=== Storage Info ===");
        MainUI.appendOutput("\n=== Storage Info ===");
        area.displayAllBoxes();

        // AGV info
        System.out.println("== AGV Info ===");
        MainUI.appendOutput("\n=== AGV Info ===");
        storingActive.displayInfo();
        storingStandby.displayInfo();
        retrievingActive.displayInfo();
        retrievingStandby.displayInfo();

        // Logs
        System.out.println("== Box Info ===");
        MainUI.appendOutput("\n=== Box Info ===");
        enteredLog.displayLog();
        exitedLog.displayLog();
        storedLog.displayLog();
    }
}