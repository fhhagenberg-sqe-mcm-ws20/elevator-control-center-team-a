package at.fhhgb.team.a.elevators.model;

/**
 * Represents the ECC Mode.
 * The ECC can either be in Automatic Mode or in Manual Mode.
 */
public class ECCMode {

    private boolean automaticModeEnabled;

    public ECCMode() {
        automaticModeEnabled = true;
    }

    public boolean isAutomaticModeEnabled() {
        return automaticModeEnabled;
    }

    public void modeButtonPressed() {
        automaticModeEnabled = !automaticModeEnabled;
    }
}
