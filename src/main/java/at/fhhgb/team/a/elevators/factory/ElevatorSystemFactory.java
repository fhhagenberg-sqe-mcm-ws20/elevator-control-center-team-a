package at.fhhgb.team.a.elevators.factory;

import at.fhhgb.team.a.elevators.app.ConnectedListener;
import at.fhhgb.team.a.elevators.app.DisconnectedListener;
import at.fhhgb.team.a.elevators.app.ElevatorControlCenter;
import at.fhhgb.team.a.elevators.app.IElevatorSystem;

public class ElevatorSystemFactory {

    private final ConnectedListener connectedCallback;
    private final DisconnectedListener disconnectedCallback;

    public ElevatorSystemFactory(ConnectedListener connectedCallback,
                                 DisconnectedListener disconnectedCallback) {
        this.connectedCallback = connectedCallback;
        this.disconnectedCallback = disconnectedCallback;
    }

    public IElevatorSystem getElevatorSystem(String systemType, String connectionURL) {
        if (systemType.equals("RMI")) {
            return new ElevatorControlCenter(connectionURL, connectedCallback, disconnectedCallback);
        } else {
            throw new RuntimeException("Only RMI is currently supported!");
        }
    }
}
