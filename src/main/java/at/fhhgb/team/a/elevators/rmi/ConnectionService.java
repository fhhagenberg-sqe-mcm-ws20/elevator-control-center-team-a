package at.fhhgb.team.a.elevators.rmi;

import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ConnectionService implements Runnable {

    private final String connectionURL;
    private final RMIConnectionListener callback;

    public ConnectionService(String connectionURL, RMIConnectionListener callback) {
        this.connectionURL = connectionURL;
        this.callback = callback;
    }

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        IElevator elevatorApi;
        try {
            elevatorApi = (IElevator) Naming.lookup(connectionURL);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            // The connection to the system failed
            return;
        }
        callback.onRMIConnected(elevatorApi);
    }
}
