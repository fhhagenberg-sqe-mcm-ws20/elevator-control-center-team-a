package at.fhhgb.team.a.elevators.model;

import at.fhhgb.team.a.elevators.auto.AutoModeExecutor;
import at.fhhgb.team.a.elevators.auto.EccModeExecutor;
import at.fhhgb.team.a.elevators.auto.ManualModeExecutor;

/**
 * Represents the ECC Mode.
 * The ECC can either be in Automatic Mode or in Manual Mode.
 */
public class ECCMode {

    private EccModeExecutor executor;

    private boolean automaticModeEnabled;

    public ECCMode() {
        automaticModeEnabled = true;
        executor = new AutoModeExecutor();
    }

    public boolean isAutomaticModeEnabled() {
        return automaticModeEnabled;
    }

    public void modeButtonPressed() {
        if (automaticModeEnabled) {
            automaticModeEnabled = false;
            executor = new ManualModeExecutor();
        } else {
            automaticModeEnabled = true;
            executor = new AutoModeExecutor();
        }
    }

    public EccModeExecutor getEccModeExecutor() {
        return executor;
    }
}
