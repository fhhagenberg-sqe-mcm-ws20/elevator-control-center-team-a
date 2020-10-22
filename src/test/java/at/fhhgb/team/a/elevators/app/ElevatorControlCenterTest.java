package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.api.IElevator;
import at.fhhgb.team.a.elevators.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("For any ElevatorControlCenter instance")
public class ElevatorControlCenterTest {

    static long CLOCK_TICK = 1L;
    static int FLOOR_HEIGHT = 3;
    static int NUMBER_OF_FLOORS = 3;
    static int NUMBER_OF_ELEVATORS = 2;
    static int TARGET_FLOOR_NUMBER = 0;
    static int CURR_ELEVATOR_FLOOR_0 = 0;
    static int CURR_ELEVATOR_FLOOR_1 = 1;
    static int CURR_ELEVATOR_POS_FEET_0 = 0;
    static int CURR_ELEVATOR_POS_FEET_2 = 2;
    static int CURR_ELEVATOR_SPEED_0 = 0;
    static int CURR_ELEVATOR_SPEED_1 = 1;
    static int CURRENT_ELEVATOR_WEIGHT = 0;
    static int ELEVATOR_ACCELERATION = 0;
    static int ELEVATOR_CAPACITY = 5;

    @Nested
    @DisplayName("assert that pollElevatorApi")
    public class PollElevatorApi {

        IElevator elevatorApi;
        ElevatorControlCenter controlCenter;

        @BeforeEach
        public void setUp() {
            elevatorApi = Mockito.mock(IElevator.class);
            controlCenter = new ElevatorControlCenter(elevatorApi);
        }

        @Test
        @DisplayName("creates a building with the correct number of Floors")
        public void testNumberOfFloors() throws RemoteException {
            stubInitialElevatorState();
            controlCenter.pollElevatorApi();

            Building building = controlCenter.getBuilding();

            assertThat(building.getFloorNum()).isEqualTo(NUMBER_OF_FLOORS);
        }

        @Test
        @DisplayName("creates a building with the correct number of Elevators")
        public void testNumberOfElevators() throws RemoteException {
            stubInitialElevatorState();
            controlCenter.pollElevatorApi();

            Building building = controlCenter.getBuilding();

            assertThat(building.getElevatorNum()).isEqualTo(NUMBER_OF_ELEVATORS);
        }

        @Test
        @DisplayName("creates a building with the correct ground floor")
        public void testIfGroundFloorIsCorrect() throws RemoteException {
            stubInitialElevatorState();
            controlCenter.pollElevatorApi();

            Floor groundFloor = controlCenter.getBuilding().getFloor(0);

            assertThat(groundFloor.getNumber()).isEqualTo(0);
            assertThat(groundFloor.getHeight()).isEqualTo(FLOOR_HEIGHT);
        }

        @Test
        @DisplayName("creates a building with the correct elevators")
        public void testIfElevatorsAreCorrect() throws RemoteException {
            stubInitialElevatorState();
            controlCenter.pollElevatorApi();

            Elevator firstElevator = controlCenter.getBuilding().getElevator(0);

            assertThat(firstElevator.getNumber()).isEqualTo(0);
            assertThat(firstElevator.getCapacity()).isEqualTo(ELEVATOR_CAPACITY);
            assertThat(firstElevator.getSpeed()).isEqualTo(CURR_ELEVATOR_SPEED_0);
            assertThat(firstElevator.getAcceleration()).isEqualTo(ELEVATOR_ACCELERATION);
            assertThat(firstElevator.getDoorStatus()).isEqualTo(DoorStatus.closed);
            assertThat(firstElevator.getCommittedDirection()).isEqualTo(Direction.uncommitted);
            assertThat(firstElevator.getWeight()).isEqualTo(CURRENT_ELEVATOR_WEIGHT);
        }

        @Test
        @DisplayName("creates a building with correct elevator floor service")
        public void testIfElevatorsServiceCorrectFloors() throws RemoteException {
            stubMovingElevatorState();
            controlCenter.pollElevatorApi();

            Elevator firstElevator = controlCenter.getBuilding().getElevator(0);
            Elevator secondElevator = controlCenter.getBuilding().getElevator(1);

            assertThat(firstElevator.servicesFloor(0)).isTrue();
            assertThat(firstElevator.servicesFloor(1)).isFalse();
            assertThat(firstElevator.servicesFloor(2)).isTrue();
            assertThat(secondElevator.servicesFloor(0)).isTrue();
            assertThat(secondElevator.servicesFloor(1)).isTrue();
            assertThat(secondElevator.servicesFloor(2)).isFalse();
        }

        @Test
        @DisplayName("creates a building with correct elevator position")
        public void testForCorrectElevatorPosition() throws RemoteException {
            stubMovingElevatorState();
            controlCenter.pollElevatorApi();

            Elevator firstElevator = controlCenter.getBuilding().getElevator(0);
            Elevator secondElevator = controlCenter.getBuilding().getElevator(1);

            assertThat(firstElevator.getCurrentPosition().getClosestFloor().getNumber()).isEqualTo(CURR_ELEVATOR_FLOOR_0);
            assertThat(secondElevator.getCurrentPosition().getClosestFloor().getNumber()).isEqualTo(CURR_ELEVATOR_FLOOR_1);
            assertThat(firstElevator.getCurrentPosition().getPositionFeet()).isEqualTo(CURR_ELEVATOR_POS_FEET_0);
            assertThat(secondElevator.getCurrentPosition().getPositionFeet()).isEqualTo(CURR_ELEVATOR_POS_FEET_2);
            assertThat(firstElevator.getSpeed()).isEqualTo(CURR_ELEVATOR_SPEED_0);
            assertThat(secondElevator.getSpeed()).isEqualTo(CURR_ELEVATOR_SPEED_1);
        }

        @Test
        @DisplayName("creates a building with correct floor buttons")
        public void testForCorrectFloorButtons() throws RemoteException {
            stubMovingElevatorState();
            controlCenter.pollElevatorApi();

            Floor firstFloor = controlCenter.getBuilding().getFloor(0);
            Floor secondFloor = controlCenter.getBuilding().getFloor(1);
            Floor thirdFloor = controlCenter.getBuilding().getFloor(2);

            assertThat(firstFloor.isDownButtonOn()).isFalse();
            assertThat(firstFloor.isUpButtonOn()).isTrue();
            assertThat(secondFloor.isDownButtonOn()).isTrue();
            assertThat(secondFloor.isUpButtonOn()).isFalse();
            assertThat(thirdFloor.isDownButtonOn()).isFalse();
            assertThat(thirdFloor.isUpButtonOn()).isFalse();
        }

        private void stubInitialElevatorState() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(CLOCK_TICK);
            Mockito.when(elevatorApi.getFloorHeight()).thenReturn(FLOOR_HEIGHT);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(NUMBER_OF_FLOORS);
            Mockito.when(elevatorApi.getFloorButtonDown(anyInt())).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonUp(anyInt())).thenReturn(false);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(NUMBER_OF_ELEVATORS);
            Mockito.when(elevatorApi.getTarget(anyInt())).thenReturn(TARGET_FLOOR_NUMBER);
            Mockito.when(elevatorApi.getElevatorFloor(anyInt())).thenReturn(CURR_ELEVATOR_FLOOR_0);
            Mockito.when(elevatorApi.getCommittedDirection(anyInt())).thenReturn(Direction.uncommitted.number);
            Mockito.when(elevatorApi.getElevatorAccel(anyInt())).thenReturn(ELEVATOR_ACCELERATION);
            Mockito.when(elevatorApi.getElevatorCapacity(anyInt())).thenReturn(ELEVATOR_CAPACITY);
            Mockito.when(elevatorApi.getElevatorDoorStatus(anyInt())).thenReturn(DoorStatus.closed.number);
            Mockito.when(elevatorApi.getElevatorPosition(anyInt())).thenReturn(CURR_ELEVATOR_POS_FEET_0);
            Mockito.when(elevatorApi.getElevatorSpeed(anyInt())).thenReturn(CURR_ELEVATOR_SPEED_0);
            Mockito.when(elevatorApi.getElevatorWeight(anyInt())).thenReturn(CURRENT_ELEVATOR_WEIGHT);
            Mockito.when(elevatorApi.getServicesFloors(anyInt(), anyInt())).thenReturn(true);
        }

        private void stubMovingElevatorState() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(CLOCK_TICK);
            Mockito.when(elevatorApi.getFloorHeight()).thenReturn(FLOOR_HEIGHT);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(NUMBER_OF_FLOORS);
            Mockito.when(elevatorApi.getFloorButtonDown(0)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonDown(1)).thenReturn(true);
            Mockito.when(elevatorApi.getFloorButtonDown(2)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonUp(0)).thenReturn(true);
            Mockito.when(elevatorApi.getFloorButtonUp(1)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonUp(1)).thenReturn(false);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(NUMBER_OF_ELEVATORS);
            Mockito.when(elevatorApi.getTarget(anyInt())).thenReturn(TARGET_FLOOR_NUMBER);
            Mockito.when(elevatorApi.getElevatorFloor(0)).thenReturn(CURR_ELEVATOR_FLOOR_0);
            Mockito.when(elevatorApi.getElevatorFloor(1)).thenReturn(CURR_ELEVATOR_FLOOR_1);
            Mockito.when(elevatorApi.getCommittedDirection(anyInt())).thenReturn(Direction.uncommitted.number);
            Mockito.when(elevatorApi.getElevatorAccel(anyInt())).thenReturn(ELEVATOR_ACCELERATION);
            Mockito.when(elevatorApi.getElevatorCapacity(anyInt())).thenReturn(ELEVATOR_CAPACITY);
            Mockito.when(elevatorApi.getElevatorDoorStatus(anyInt())).thenReturn(DoorStatus.closed.number);
            Mockito.when(elevatorApi.getElevatorPosition(0)).thenReturn(CURR_ELEVATOR_POS_FEET_0);
            Mockito.when(elevatorApi.getElevatorPosition(1)).thenReturn(CURR_ELEVATOR_POS_FEET_2);
            Mockito.when(elevatorApi.getElevatorSpeed(0)).thenReturn(CURR_ELEVATOR_SPEED_0);
            Mockito.when(elevatorApi.getElevatorSpeed(1)).thenReturn(CURR_ELEVATOR_SPEED_1);
            Mockito.when(elevatorApi.getElevatorWeight(anyInt())).thenReturn(CURRENT_ELEVATOR_WEIGHT);
            Mockito.when(elevatorApi.getServicesFloors(0, 0)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(0, 1)).thenReturn(false);
            Mockito.when(elevatorApi.getServicesFloors(0, 2)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 0)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 1)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 2)).thenReturn(false);
        }
    }
}
