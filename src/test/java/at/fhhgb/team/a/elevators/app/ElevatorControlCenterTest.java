package at.fhhgb.team.a.elevators.app;

import sqelevator.IElevator;
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

    @Nested
    @DisplayName("assert that pollElevatorApi")
    public class PollElevatorApi {

        IElevator elevatorApi;
        ElevatorControlCenter controlCenter;

        @BeforeEach
        void setUp() {
            elevatorApi = Mockito.mock(IElevator.class);
            controlCenter = new ElevatorControlCenter(elevatorApi);
        }

        @Test
        @DisplayName("creates a building with the correct number of Floors")
        void testNumberOfFloors() throws RemoteException {
            int numberOfFloors = 2;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(numberOfFloors);

            controlCenter.pollElevatorApi();

            Building building = controlCenter.getBuilding();

            assertThat(building.getFloorNum()).isEqualTo(numberOfFloors);
        }

        @Test
        @DisplayName("creates a building with the correct number of Elevators")
        void testNumberOfElevators() throws RemoteException {
            int numberOfElevators = 2;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(numberOfElevators);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1);

            controlCenter.pollElevatorApi();

            Building building = controlCenter.getBuilding();

            assertThat(building.getElevatorNum()).isEqualTo(numberOfElevators);
        }

        @Test
        @DisplayName("creates a building with the correct ground floor")
        void testGroundFloorIsCorrect() throws RemoteException {
            int floorHeight = 100;
            int groundFloorNum = 0;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorHeight()).thenReturn(floorHeight);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1);
            controlCenter.pollElevatorApi();

            Floor groundFloor = controlCenter.getBuilding().getFloor(groundFloorNum);

            assertThat(groundFloor.getNumber()).isEqualTo(groundFloorNum);
            assertThat(groundFloor.getHeight()).isEqualTo(floorHeight);
        }

        @Test
        @DisplayName("creates a building with the correct elevators")
        void testElevatorsAreCorrect() throws RemoteException {
            int capacity = 100;
            int speed = 10;
            int acceleration = 5;
            DoorStatus doorStatus = DoorStatus.closing;
            Direction direction = Direction.down;
            int weight = 5;
            int elevatorNumber = 0;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(1);
            Mockito.when(elevatorApi.getCommittedDirection(anyInt())).thenReturn(direction.number);
            Mockito.when(elevatorApi.getElevatorAccel(anyInt())).thenReturn(acceleration);
            Mockito.when(elevatorApi.getElevatorCapacity(anyInt())).thenReturn(capacity);
            Mockito.when(elevatorApi.getElevatorDoorStatus(anyInt())).thenReturn(doorStatus.number);
            Mockito.when(elevatorApi.getElevatorSpeed(anyInt())).thenReturn(speed);
            Mockito.when(elevatorApi.getElevatorWeight(anyInt())).thenReturn(weight);
            Mockito.when(elevatorApi.getServicesFloors(anyInt(), anyInt())).thenReturn(true);

            controlCenter.pollElevatorApi();

            Elevator firstElevator = controlCenter.getBuilding().getElevator(elevatorNumber);

            assertThat(firstElevator.getNumber()).isEqualTo(elevatorNumber);
            assertThat(firstElevator.getCapacity()).isEqualTo(capacity);
            assertThat(firstElevator.getSpeed()).isEqualTo(speed);
            assertThat(firstElevator.getAcceleration()).isEqualTo(acceleration);
            assertThat(firstElevator.getDoorStatus()).isEqualTo(doorStatus);
            assertThat(firstElevator.getCommittedDirection()).isEqualTo(direction);
            assertThat(firstElevator.getWeight()).isEqualTo(weight);
        }

        @Test
        @DisplayName("creates a building with correct elevator floor service")
        void testElevatorsServiceCorrectFloors() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(3);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(2);
            Mockito.when(elevatorApi.getServicesFloors(0, 0)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(0, 1)).thenReturn(false);
            Mockito.when(elevatorApi.getServicesFloors(0, 2)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 0)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 1)).thenReturn(true);
            Mockito.when(elevatorApi.getServicesFloors(1, 2)).thenReturn(false);

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
        void testForCorrectElevatorPosition() throws RemoteException {
            int positionInFeet0 = 0;
            int positionInFeet1 = 50;
            int currentFloorElevator0 = 0;
            int currentFloorElevator1 = 1;
            int speedElevator0 = 10;
            int speedElevator1 = 20;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorHeight()).thenReturn(100);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(2);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(2);
            Mockito.when(elevatorApi.getElevatorFloor(0)).thenReturn(currentFloorElevator0);
            Mockito.when(elevatorApi.getElevatorFloor(1)).thenReturn(currentFloorElevator1);
            Mockito.when(elevatorApi.getElevatorPosition(0)).thenReturn(positionInFeet0);
            Mockito.when(elevatorApi.getElevatorPosition(1)).thenReturn(positionInFeet1);
            Mockito.when(elevatorApi.getElevatorSpeed(0)).thenReturn(speedElevator0);
            Mockito.when(elevatorApi.getElevatorSpeed(1)).thenReturn(speedElevator1);

            controlCenter.pollElevatorApi();

            Elevator firstElevator = controlCenter.getBuilding().getElevator(0);
            Elevator secondElevator = controlCenter.getBuilding().getElevator(1);

            assertThat(firstElevator.getCurrentPosition().getClosestFloor().getNumber()).isEqualTo(currentFloorElevator0);
            assertThat(secondElevator.getCurrentPosition().getClosestFloor().getNumber()).isEqualTo(currentFloorElevator1);
            assertThat(firstElevator.getCurrentPosition().getPositionFeet()).isEqualTo(positionInFeet0);
            assertThat(secondElevator.getCurrentPosition().getPositionFeet()).isEqualTo(positionInFeet1);
            assertThat(firstElevator.getSpeed()).isEqualTo(speedElevator0);
            assertThat(secondElevator.getSpeed()).isEqualTo(speedElevator1);
        }

        @Test
        @DisplayName("updates a building on the next clock tick")
        void testForUpdatedElevatorWithNewTick() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1);
            controlCenter.pollElevatorApi();

            Floor firstFloor = controlCenter.getBuilding().getFloor(0);
            assertThat(firstFloor.getNumber()).isZero();
            Floor secondFloor = controlCenter.getBuilding().getFloor(1);
            assertThat(secondFloor).isNull();

            Mockito.when(elevatorApi.getClockTick()).thenReturn(2L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(2);
            controlCenter.pollElevatorApi();

            secondFloor = controlCenter.getBuilding().getFloor(1);
            assertThat(secondFloor.getNumber()).isEqualTo(1);
        }

        @Test
        @DisplayName("skips update on same clock tick")
        void testForSkippedUpdateWhenTickDidNotChange() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1);
            controlCenter.pollElevatorApi();

            Floor secondFloor = controlCenter.getBuilding().getFloor(1);
            assertThat(secondFloor).isNull();

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(2);
            controlCenter.pollElevatorApi();

            secondFloor = controlCenter.getBuilding().getFloor(1);
            assertThat(secondFloor).isNull();
        }

        @Test
        @DisplayName("retries update when clock tick changes in between processing")
        void testForUpdatedElevatorWithChangingTickBetweenUpdate() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L).thenReturn(2L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(1).thenReturn(2);
            controlCenter.pollElevatorApi();

            Floor secondFloor = controlCenter.getBuilding().getFloor(1);
            assertThat(secondFloor.getNumber()).isEqualTo(1);
        }

        @Test
        @DisplayName("creates a building with correct floor buttons")
        void testForCorrectFloorButtons() throws RemoteException {
            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(3);
            Mockito.when(elevatorApi.getFloorButtonDown(0)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonDown(1)).thenReturn(true);
            Mockito.when(elevatorApi.getFloorButtonDown(2)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonUp(0)).thenReturn(true);
            Mockito.when(elevatorApi.getFloorButtonUp(1)).thenReturn(false);
            Mockito.when(elevatorApi.getFloorButtonUp(2)).thenReturn(false);

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

        @Test
        @DisplayName("creates a building with a targeted elevator")
        void testForCorrectTarget() throws RemoteException {
            int targetFloorNumber = 0;
            int elevatorNumber = 1;
            boolean downButtonPressedOnTargetFloor = true;

            Mockito.when(elevatorApi.getClockTick()).thenReturn(1L);
            Mockito.when(elevatorApi.getFloorNum()).thenReturn(2);
            Mockito.when(elevatorApi.getElevatorNum()).thenReturn(2);
            Mockito.when(elevatorApi.getServicesFloors(elevatorNumber, targetFloorNumber)).thenReturn(true);
            Mockito.when(elevatorApi.getElevatorButton(elevatorNumber, targetFloorNumber)).thenReturn(downButtonPressedOnTargetFloor);
            Mockito.when(elevatorApi.getTarget(elevatorNumber)).thenReturn(targetFloorNumber);

            controlCenter.pollElevatorApi();

            Elevator elevator = controlCenter.getBuilding().getElevator(elevatorNumber);
            Floor target = elevator.getTarget();
            boolean downButtonPressed = elevator.getElevatorButton(target.getNumber());

            assertThat(target.getNumber()).isEqualTo(targetFloorNumber);
            assertThat(downButtonPressed).isEqualTo(downButtonPressedOnTargetFloor);
        }
    }
}
