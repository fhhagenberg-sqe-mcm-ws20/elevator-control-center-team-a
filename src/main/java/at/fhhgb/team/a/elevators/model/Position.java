package at.fhhgb.team.a.elevators.model;

public class Position {

    /** The current position of the elevator in feet.
     * Position is measured in feet above ground floor with zero being the bottom.. */
    private float positionFeet = 0.0f;

    /** The current position of the elevator to the closest floor. */
    private Floor closestFloor = new Floor();

    public Position() {

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
