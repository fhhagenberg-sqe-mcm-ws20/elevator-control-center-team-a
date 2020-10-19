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
}
