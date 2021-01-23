package at.fhhgb.team.a.elevators.auto;

import at.fhhgb.team.a.elevators.model.Building;

public class ManualModeExecutor implements EccModeExecutor {
    
    @Override
    public void execute(Building building) {
        // no control assistance during manual mode
    }
}
