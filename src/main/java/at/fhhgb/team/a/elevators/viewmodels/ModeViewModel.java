package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.ECCMode;

public class ModeViewModel {

    ECCMode mode;

    public ModeViewModel(ECCMode model) {
        this.mode = model;
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
}
