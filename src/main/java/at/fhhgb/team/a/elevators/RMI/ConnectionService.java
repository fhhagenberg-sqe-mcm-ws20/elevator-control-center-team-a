package at.fhhgb.team.a.elevators.RMI;

import at.fhhgb.team.a.elevators.app.ConnectionCallback;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ConnectionService implements Runnable {

    private final String connectionURL;
    private final ConnectionCallback callback;

    private IElevator elevatorApi;

    public ConnectionService(String connectionURL, ConnectionCallback callback) {
        this.connectionURL = connectionURL;
        this.callback = callback;
    }

    @Override
    public void run() {
        connect();
        callback.connectionEstablished(elevatorApi);
    }

    private void connect() {
        try {
            elevatorApi = (IElevator) Naming.lookup(connectionURL);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            connect();
        }
    }
}
