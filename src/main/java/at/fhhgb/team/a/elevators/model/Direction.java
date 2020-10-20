package at.fhhgb.team.a.elevators.model;

public enum Direction {
    up(0),
    down(1),
    uncommitted(2);

    public final int number;

    Direction(int number) {
        this.number = number;
    }
}
