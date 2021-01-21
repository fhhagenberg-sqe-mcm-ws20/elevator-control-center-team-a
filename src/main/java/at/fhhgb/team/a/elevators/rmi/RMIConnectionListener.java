package at.fhhgb.team.a.elevators.rmi;

import sqelevator.IElevator;

@FunctionalInterface
public interface RMIConnectionListener {
    void onRMIConnected(IElevator elevatorApi);
}
