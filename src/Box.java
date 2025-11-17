class BoxException extends Exception {
    public BoxException(String message) {
        super(message);
    }
}

public class Box extends Resource {
    private String weight;
    private String content;

    public Box(String id, String weight, String content) {
        this.id = id;
        this.weight = weight;
        this.content = content;
        this.floorPosition  = new FloorPosition (-5, -5);
    }

    public void setPosition(int row, int col) {
        try {
            if (floorPosition  == null) {
                throw new BoxException("Position object is null!");
            }
            floorPosition.setRow(row);
            floorPosition.setCol(col);

            System.out.println("[INFO] Box#" + id + " moved to position (" + row + ", " + col + ")");
            MainUI.appendOutput("[INFO] Box#" + id + " moved to position (" + row + ", " + col + ")");
            WarehouseAutomation.systemLog.log("[INFO] Box#" + id + " moved to position (" + row + ", " + col + ")");
            WarehouseAutomation.overallLog.log("[INFO] Box#" + id + " moved to position (" + row + ", " + col + ")");
        } catch (BoxException e) {
            System.err.println("[ERROR] " + e.getMessage());
            MainUI.appendOutput("[ERROR] " + e.getMessage());
            WarehouseAutomation.systemLog.log("[ERROR] " + e.getMessage());
            WarehouseAutomation.overallLog.log("[ERROR] " + e.getMessage());
        }
    }

    public int getRow() {
        return floorPosition.getRow();
    }

    public int getColumn() {
        return floorPosition.getCol();
    }

    @Override
    public String toString() {
        return "Box ID: " + id + ", Weight: " + weight + " kg, Content:" + content;
    }

    @Override
    protected void displayInfo() {
        System.out.println("[INFO] Box#" + id + " | " + content + " | Storage Pos: " + floorPosition );
        MainUI.appendOutput("[INFO] Box#" + id + " | " + content + " | Storage Pos: " + floorPosition );
        WarehouseAutomation.systemLog.log("[INFO] Box#" + id + " | " + content + " | Storage Pos: " + floorPosition );
        WarehouseAutomation.overallLog.log("[INFO] Box#" + id + " | " + content + " | Storage Pos: " + floorPosition );
    }
}

