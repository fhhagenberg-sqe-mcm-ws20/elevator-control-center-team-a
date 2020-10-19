package at.fhhgb.team.a.elevators.model;

public class Position {

    /** The current position of the elevator in feet.
     * Position is measured in feet above ground floor with zero being the bottom.. */
    private float positionFeet = 0.0f;

    /** The current position of the elevator to the closest floor. */
    private Floor closestFloor = new Floor();
}
