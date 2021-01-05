package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.Building;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    IElevator elevatorApi;
    ElevatorControlCenter controlCenter;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) throws RemoteException, MalformedURLException, NotBoundException {
        elevatorApi = Mockito.mock(IElevator.class);
        controlCenter = new ElevatorControlCenter(elevatorApi);

        var app = new App();
        app.setControlCenter(controlCenter);
        app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    public void testManualModeButtonClicked(FxRobot robot) {
        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Auto"));

        robot.clickOn("#modeButton");

        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Manual"));
    }

    @Test
    public void testElevatorFloorButtonClicked(FxRobot robot) {
        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Auto"));

        robot.clickOn("#modeButton");

        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Manual"));
    }
}
