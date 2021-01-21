package at.fhhgb.team.a.elevators.model;


import java.util.Observable;

public class Floor extends Observable {

    /** The number of the floor.
     *  Floor numbering starts at 0 for floor 1. */
    private int number = 0;

    /** The down button of the floor.
     * For the purposes of passengers calling the elevator. */
    private Button downButton = new Button();

    /** The up button of the floor.
     * For the purposes of passengers calling the elevator. */
    private Button upButton = new Button();

    public Floor(int number) {
        this.number = number;
    }

    /**
     * Retrieves the distinct number of the floor.
     * @return distinct number of the floor
     */
    public int getNumber() {
        return number;
    }

    /**
     * Retrieves the height of the floors in the building.
     * @return floor height (ft)
     */
    public float getHeight() {
        return Building.FLOOR_HEIGHT;
    }

    /**
     * Provides the status of the Down button on the floor (on/off).
     * @return boolean to indicate if button is active (true) or not (false)
     */
    public boolean isDownButtonOn() {
        return downButton.isOn();
    }

    /**
     * Provides the status of the Up button on the floor (on/off).
     * @return boolean to indicate if button is active (true) or not (false)
     */
    public boolean isUpButtonOn() {
        return upButton.isOn();
    }

    /**
     * Sets the status of the Down button on the floor to on.
     */
    public void pressDownButton() {
        downButton.press();
        setChanged();
        notifyObservers();
    }

    /**
     * Sets the status of the Up button on the floor to on.
     */
    public void pressUpButton() {
        upButton.press();
        setChanged();
        notifyObservers();
    }

    /**
     * Clears the state of the Down & Up buttons on the floor
     */
    public void clearButtonState() {
        downButton.clear();
        upButton.clear();
        setChanged();
        notifyObservers();
    }
}
