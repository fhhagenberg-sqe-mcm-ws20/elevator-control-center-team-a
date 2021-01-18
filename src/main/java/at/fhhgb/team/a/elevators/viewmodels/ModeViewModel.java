package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.ECCMode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ModeViewModel {

    private final ECCMode mode;
    private final BooleanProperty connectionProperty;

    public ModeViewModel(ECCMode model) {
        this.mode = model;
        connectionProperty = new SimpleBooleanProperty(false);
    }

    public void onButtonPressed() {
        mode.modeButtonPressed();
    }

    public boolean isManualModeEnabled() {
        return !mode.isAutomaticModeEnabled();
    }

    public String getButtonText() {
        if (mode.isAutomaticModeEnabled()) {
            return "Auto";

        } else {
            return "Manual";
        }
    }

    public BooleanProperty isConnected() {
        return connectionProperty;
    }

    public void setConnection(boolean connected) {
        connectionProperty.setValue(connected);
    }
}
