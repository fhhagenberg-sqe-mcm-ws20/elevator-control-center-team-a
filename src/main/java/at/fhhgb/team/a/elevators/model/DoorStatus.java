package at.fhhgb.team.a.elevators.model;

public enum DoorStatus {
    open(1),
    closed(2),
    opening(3),
    closing(4);

    public final int number;

    DoorStatus(int number) {
        this.number = number;
    }

    /**
     * Provides the current status of the doors of an elevator (open/closed).
     * @param number the flag number of the door status
     * @return the corresponding door status
     */
    public static DoorStatus fromNumber(int number) {
        switch (number) {
            case 1:
                return open;
            case 2:
                return closed;
            case 3:
                return opening;
            case 4:
                return closing;
        }
        return null;
    }
}
