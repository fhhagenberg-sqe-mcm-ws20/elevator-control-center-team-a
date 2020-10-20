package at.fhhgb.team.a.elevators.model;

public class Position {

    /** The current position of the elevator in feet.
     * Position is measured in feet above ground floor with zero being the bottom.. */
    private float positionFeet;

    /** The current position of the elevator to the closest floor. */
    private Floor closestFloor;

    public Position(int feetFromGround, Floor closestFloor) {
        this.closestFloor = closestFloor;
        this.positionFeet = feetFromGround;
    }

    /**
     * Retrieves the current position of the elevator in feet.
     * @return current position in feet
     */
    public float getPositionFeet() {
        return positionFeet;
    }

    /**
     * Retrieves the current position of the elevator to the closest floor.
     * @return current position to the closest floor
     */
    public Floor getClosestFloor() {
        return closestFloor;
    }
}
