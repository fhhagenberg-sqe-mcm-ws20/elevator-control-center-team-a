package at.fhhgb.team.a.elevators.model;

public class Button {

    /* The status of the button.
    * True means, the button is on. */
    private boolean isOn = false;

    public Button() {}

    /**
     * Provides the status of the button (on/off).
     * @return boolean to indicate if button is active (true) or not (false)
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * Sets the status of the button to on (pressed).
     */
    public void press() {
        isOn = true;
    }

    /**
     * Resets the status of the button to off (not pressed).
     */
    public void clear() {
        isOn = false;
    }
}

