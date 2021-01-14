package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.Direction;
import at.fhhgb.team.a.elevators.model.DoorStatus;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
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

        setupMockup();

        var app = new App(controlCenter);
        app.start(stage);
    }

    private void setupMockup() throws RemoteException {
        Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
        Mockito.when(elevatorApi.getFloorNum()).thenReturn(4);
        Mockito.when(elevatorApi.getElevatorNum()).thenReturn(2);
        Mockito.when(elevatorApi.getCommittedDirection(anyInt())).thenReturn(Direction.down.number);
        Mockito.when(elevatorApi.getElevatorAccel(anyInt())).thenReturn(50);
        Mockito.when(elevatorApi.getElevatorCapacity(anyInt())).thenReturn(100);
        Mockito.when(elevatorApi.getElevatorDoorStatus(anyInt())).thenReturn(DoorStatus.closing.number);
        Mockito.when(elevatorApi.getElevatorSpeed(anyInt())).thenReturn(10);
        Mockito.when(elevatorApi.getElevatorWeight(anyInt())).thenReturn(500);
        Mockito.when(elevatorApi.getServicesFloors(anyInt(), anyInt())).thenReturn(true);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    void testManualModeButtonClicked(FxRobot robot) {
        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Auto"));

        robot.clickOn("#modeButton");

        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Manual"));
    }

    @Test
    void testElevatorFloorButtonClick(FxRobot robot) throws RemoteException {
        Elevator elevator = controlCenter.getBuilding().getElevator(0);
        
        // Assert that the default target number is set
        Floor defaultTarget = elevator.getTarget();
        assertThat(defaultTarget.getNumber()).isEqualTo(0);

        // Go into manual mode
        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Auto"));

        robot.clickOn("#modeButton");

        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Manual"));

        // Click on elevator floor button
        verifyThat("#e0-f2", isVisible());
        verifyThat("#e0-f2", hasText("2"));

        robot.clickOn("#e0-f2");

        // Assert that the API call has been executed
        controlCenter.pollElevatorApi();
        elevator = controlCenter.getBuilding().getElevator(0);

        Floor target = elevator.getTarget();
        assertThat(target.getNumber()).isEqualTo(2);
    }

    @Test
    void testFloorButtonsPressed(FxRobot robot) throws RemoteException {
        Floor floor = controlCenter.getBuilding().getFloor(1);
        assertThat(floor.isUpButtonOn()).isFalse();

        // Go into manual mode
        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Auto"));

        robot.clickOn("#modeButton");

        verifyThat("#modeButton", isVisible());
        verifyThat("#modeButton", hasText("Manual"));

        // Click on floor buttons
        verifyThat("#f1-up", isVisible());
        robot.clickOn("#f1-up");

        verifyThat("#f1-down", isVisible());
        robot.clickOn("#f1-down");

        // Assert that the API call has been executed
        controlCenter.pollElevatorApi();
        floor = controlCenter.getBuilding().getFloor(1);

        assertThat(floor.isUpButtonOn()).isTrue();
        assertThat(floor.isDownButtonOn()).isTrue();
    }


    @Test
    void testElevatorSpeedChange(FxRobot robot) throws RemoteException {
        // Assert that the GUI shows the default values
        verifyThat("#e0-speed", isVisible());
        verifyThat("#e0-speed", hasText("speed: 10.0 km/h"));

        // Get updates
        Mockito.when(elevatorApi.getClockTick()).thenReturn(2L);
        Mockito.when(elevatorApi.getElevatorSpeed(0)).thenReturn(12);
        controlCenter.pollElevatorApi();

        // Assert that the GUI shows the updated values
        verifyThat("#e0-speed", isVisible());
        verifyThat("#e0-speed", hasText("speed: 12.0 km/h"));
    }
}
