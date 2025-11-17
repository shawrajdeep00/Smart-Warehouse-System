class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }
}
public abstract class StorageSystem {
    protected static int totalBoxes = 0;
    protected static int enteredCount = 0;
    protected static int exitedCount = 0;

    protected abstract void recordEvent(Box box) throws StorageException;

    protected abstract void displayLog();
}

class isBoxEntered extends StorageSystem {
    @Override
    protected void recordEvent(Box box) throws StorageException {
        try {
            if (box == null) throw new StorageException("Cannot enter a null box!");
            enteredCount++;
            totalBoxes++;

            System.out.println("[EVENT] Box#" + box.getId() + " entered");
            MainUI.appendOutput("[EVENT] Box#" + box.getId() + " entered");
            WarehouseAutomation.systemLog.log("[EVENT] Box#" + box.getId() + " entered");
            WarehouseAutomation.overallLog.log("[EVENT] Box#" + box.getId() + " entered");
        } catch (StorageException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    @Override
    protected void displayLog() {
        System.out.println("[INFO] Total boxes entered: " + enteredCount);
        MainUI.appendOutput("[INFO] Total boxes entered: " + enteredCount);
        WarehouseAutomation.systemLog.log("[INFO] Total boxes entered: " + enteredCount);
        WarehouseAutomation.overallLog.log("[INFO] Total boxes entered: " + enteredCount);
    }
}

class isBoxStored extends StorageSystem {
    @Override
    protected void recordEvent(Box box) throws StorageException {
        try {
            if (box == null) throw new StorageException("Cannot store a null box!");
            System.out.println("[EVENT] Box#" + box.getId() + " stored");
            MainUI.appendOutput("[EVENT] Box#" + box.getId() + " stored");
            WarehouseAutomation.systemLog.log("[EVENT] Box#" + box.getId() + " stored");
            WarehouseAutomation.overallLog.log("[EVENT] Box#" + box.getId() + " stored");
        } catch (StorageException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    @Override
    protected void displayLog() {
        System.out.println("[INFO] Total boxes stored: " + totalBoxes);
        MainUI.appendOutput("[INFO] Total boxes stored: " + totalBoxes);
        WarehouseAutomation.systemLog.log("[INFO] Total boxes stored: " + totalBoxes);
        WarehouseAutomation.overallLog.log("[INFO] Total boxes stored: " + totalBoxes);
    }
}

class isBoxExited extends StorageSystem {
    @Override
    protected void recordEvent(Box box) throws StorageException {
        try {
            if (box == null) throw new StorageException("Cannot exit a null box!");
            exitedCount++;
            totalBoxes--;

            System.out.println("[EVENT] Box#" + box.getId() + " exited");
            MainUI.appendOutput("[EVENT] Box#" + box.getId() + " exited");
            WarehouseAutomation.systemLog.log("[EVENT] Box#" + box.getId() + " exited");
            WarehouseAutomation.overallLog.log("[EVENT] Box#" + box.getId() + " exited");
        } catch (StorageException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    @Override
    protected void displayLog() {
        System.out.println("[INFO] Total boxes exited: " + exitedCount);
        MainUI.appendOutput("[INFO] Total boxes exited: " + exitedCount);
        WarehouseAutomation.systemLog.log("[INFO] Total boxes exited: " + exitedCount);
        WarehouseAutomation.overallLog.log("[INFO] Total boxes exited: " + exitedCount);
    }
}