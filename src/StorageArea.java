class StorageAreaException extends Exception {
    public StorageAreaException(String message) {
        super(message);
    }
}

public class StorageArea {
    private Box[][] storagePlace;
    private int rowSA, colSA;

    public StorageArea(int rowSA, int colSA) {
        this.rowSA = rowSA;
        this.colSA = colSA;
        storagePlace = new Box[rowSA][colSA];
    }

    // Find first empty slot
    public FloorPosition findEmptySlot() {
        try {
            for (int r = 0; r < rowSA; r++) {
                for (int c = 0; c < colSA; c++) {
                    if (storagePlace[r][c] == null) {
                        return new FloorPosition(r, c);
                    }
                }
            }
            throw new StorageAreaException("No empty slot available.");
        } catch (StorageAreaException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
            return null; //full
        }
    }

    // Find box position by ID
    public FloorPosition findBoxById(String id) {
        for (int r = 0; r < rowSA; r++) {
            for (int c = 0; c < colSA; c++) {
                if (storagePlace[r][c] != null && storagePlace[r][c].getId().equals(id)) {
                    return new FloorPosition(r, c);
                }
            }
        }
        return null; // not found
    }

    public Box getBoxAt(int row, int col) {
        try {
            if (row >= 0 && col >= 0 && row < storagePlace.length && col < storagePlace[0].length) {
                return storagePlace[row][col];
            }
            throw new StorageAreaException("Invalid position [" + row + "," + col + "]");
        } catch (StorageAreaException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
            return null; //not found
        }
    }

    public void storeBox(Box box) {
        try {
            int row = box.getRow();
            int col = box.getColumn();
            if (row < 0 || col < 0 || row >= rowSA || col >= colSA) {
                throw new StorageAreaException("Invalid storage position " + box.getFloorPosition());
            }
            if (storagePlace[row][col] == null) {
                storagePlace[row][col] = box;
                System.out.println("[STORAGE] Stored Box#" + box.getId() + " at " + box.getFloorPosition());
                MainUI.appendOutput("[STORAGE] Stored Box#" + box.getId() + " at " + box.getFloorPosition());
                WarehouseAutomation.systemLog.log("[STORAGE] Stored Box#" + box.getId() + " at " + box.getFloorPosition());
                WarehouseAutomation.overallLog.log("[STORAGE] Stored Box#" + box.getId() + " at " + box.getFloorPosition());
            } else {
                throw new StorageAreaException("Position " + box.getFloorPosition() + " is occupied!");
            }
        } catch (StorageAreaException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    public Box retrieveBox(int row, int col) {
        try {
            if (row < 0 || col < 0 || row >= rowSA || col >= colSA) {
                throw new StorageAreaException("Invalid retrieve position [" + row + "," + col + "]");
            }
            if (storagePlace[row][col] != null) {
                Box box = storagePlace[row][col];
                storagePlace[row][col] = null;

                System.out.println("[RETRIEVE] Retrieved Box#" + box.getId() + " from [" + row + "," + col + "]");
                MainUI.appendOutput("[RETRIEVE] Retrieved Box#" + box.getId() + " from [" + row + "," + col + "]");
                WarehouseAutomation.systemLog.log("[RETRIEVE] Retrieved Box#" + box.getId() + " from [" + row + "," + col + "]");
                WarehouseAutomation.overallLog.log("[RETRIEVE] Retrieved Box#" + box.getId() + " from [" + row + "," + col + "]");
                return box;
            } else {
                throw new StorageAreaException("No box at [" + row + "," + col + "]");
            }
        } catch (StorageAreaException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
            return null; //not found
        }
    }

    public void displayAllBoxes() {
        for (int i = 0; i < rowSA; i++) {
            for (int j = 0; j < colSA; j++) {
                Box box = storagePlace[i][j];
                if (box != null) {
                    System.out.println("[INFO] Row " + i + ", Col " + j + " : " + box);
                    MainUI.appendOutput("[INFO] Row " + i + ", Col " + j + " : " + box);
                } else {
                    System.out.println("[INFO] Row " + i + ", Col " + j + ": [Empty]");
                    MainUI.appendOutput("[INFO] Row " + i + ", Col " + j + ": [Empty]");
                }
            }
        }
    }
}
