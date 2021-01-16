package at.fhhgb.team.a.elevators.app;

import sqelevator.IElevator;

@FunctionalInterface
public interface ConnectionCallback {
    void connectionEstablished(IElevator elevatorApi);
}
