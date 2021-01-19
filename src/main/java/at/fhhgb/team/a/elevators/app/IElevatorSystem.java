package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.exceptions.ElevatorSystemException;
import at.fhhgb.team.a.elevators.model.Building;

public interface IElevatorSystem extends Runnable {

    void pollElevatorApi() throws ElevatorSystemException;

    Building getBuilding();
}
