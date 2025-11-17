abstract class Resource {
    protected String id;
    protected FloorPosition floorPosition;

    public String getId() {
        return id;
    }

    public FloorPosition getFloorPosition() {
        return floorPosition;
    }

    protected abstract void displayInfo();
}


