package at.fhhgb.team.a.elevators.model;

public class Floor {

    /** The number of the floor.
     *  Floor numbering starts at 0 for floor 1. */
    private int number = 0;

    /** The height of the floor in feet.
     * It is assumed that each floor is the same height. */
    private float height = 0.0f;

    /** The down button of the floor.
     * For the purposes of passengers calling the elevator. */
    private Button downButton = new Button(ButtonType.down);

    /** The up button of the floor.
     * For the purposes of passengers calling the elevator. */
    private Button upButton = new Button(ButtonType.up);

    public Floor() {

    }

    /**
     * Retrieves the height of the floors in the building.
     * @return floor height (ft)
     */
    public float getHeight() {
        return height;
    }

    /**
     * Provides the down button of the floor.
     * @return the down button of the floor
     */
    public Button getDownButton() {
        return downButton;
    }

    /**
     * Provides the up button of the floor.
     * @return the up button of the floor
     */
    public Button getUpButton() {
        return upButton;
    }
}
