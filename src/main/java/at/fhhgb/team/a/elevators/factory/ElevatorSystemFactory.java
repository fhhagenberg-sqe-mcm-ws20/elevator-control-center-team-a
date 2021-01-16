package at.fhhgb.team.a.elevators.factory;

import at.fhhgb.team.a.elevators.app.ConnectionCallback;
import at.fhhgb.team.a.elevators.app.ElevatorControlCenter;
import at.fhhgb.team.a.elevators.app.IElevatorSystem;

public class ElevatorSystemFactory {

    public IElevatorSystem getElevatorSystem(String systemType, String connectionURL, ConnectionCallback callback) {
        if (systemType.equals("RMI")) {
            return new ElevatorControlCenter(connectionURL, callback);
        } else {
            throw new RuntimeException("Only RMI is currently supported!");
        }
    }
}
