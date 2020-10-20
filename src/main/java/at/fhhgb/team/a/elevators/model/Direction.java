package at.fhhgb.team.a.elevators.model;

public enum Direction {
    up(0),
    down(1),
    uncommitted(2);

    public final int number;

    Direction(int number) {
        this.number = number;
    }

    public static Direction fromNumber(int number) {
        switch (number) {
            case 0:
                return up;
            case 1:
                return down;
            case 2:
                return uncommitted;
        }
        return null;
    }
}
