package at.fhhgb.team.a.elevators.rmi;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class ConnectionServiceTest {

    @Test
    void testFailedConnection() {
        RMIConnectionListener callbackMock = Mockito.mock(RMIConnectionListener.class);
        ConnectionService serviceUnderTest = new ConnectionService("nonExistentURL", callbackMock);

        serviceUnderTest.run();

        Mockito.verify(callbackMock, Mockito.never()).onRMIConnected(Mockito.any());
    }
}
