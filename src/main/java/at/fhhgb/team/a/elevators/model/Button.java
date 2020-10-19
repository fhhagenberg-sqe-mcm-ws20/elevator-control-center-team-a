package at.fhhgb.team.a.elevators.model;

public class Button {

    /** The type of the button.
     * Can be up or down */
    private ButtonType type = ButtonType.up;

    /* The status of the button.
    * True means, the button is on. */
    private boolean isOn = false;

    public Button(ButtonType type) {
        this.type = type;
    }

    /**
     * Provides the status of the button (on/off).
     * @return boolean to indicate if button is active (true) or not (false)
     */
    public boolean isOn() {
        return isOn;
    }
}

