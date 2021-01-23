package at.fhhgb.team.a.elevators.auto;

import at.fhhgb.team.a.elevators.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;


class AutoModeExecutorTest {
    AutoModeExecutor autoModeExecutor;
    Building building;
    Elevator elevator;
    Floor floor1;
    Floor floor2;
    Floor floor3;

    @BeforeEach
    void setUp() {
        autoModeExecutor = new AutoModeExecutor();

        building = Mockito.mock(Building.class);

        elevator = Mockito.mock(Elevator.class);
        Mockito.when(elevator.getDoorStatus()).thenReturn(DoorStatus.open);
        var elevators = new ArrayList<Elevator>();
        elevators.add(elevator);
        Mockito.when(building.getElevators()).thenReturn(elevators);

        var floors = new ArrayList<Floor>();
        Mockito.when(elevator.getFloors()).thenReturn(floors);
        floor1 = Mockito.mock(Floor.class);
        Mockito.when(floor1.getNumber()).thenReturn(1);
        floor2 = Mockito.mock(Floor.class);
        Mockito.when(floor2.getNumber()).thenReturn(2);
        floor3 = Mockito.mock(Floor.class);
        Mockito.when(floor3.getNumber()).thenReturn(3);
        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);
    }

    @Test
    void testExecuteForRidingUpwards() {
        Mockito.when(elevator.getCurrentPosition()).thenReturn(new Position(0,floor1));
        Mockito.when(elevator.getTarget()).thenReturn(floor1);

        autoModeExecutor.execute(building);

        Mockito.verify(elevator, Mockito.times(1)).setTarget(floor2);
    }

    @Test
    void testExecuteForRidingDownwards() {
        Mockito.when(elevator.getCurrentPosition()).thenReturn(new Position(0,floor3));
        Mockito.when(elevator.getTarget()).thenReturn(floor3);

        autoModeExecutor.execute(building);

        Mockito.verify(elevator, Mockito.times(1)).setTarget(floor2);
    }

    @Test
    void testExecuteForRidingDownwardsFromMiddleFloor() {
        Mockito.when(elevator.getCurrentPosition()).thenReturn(new Position(0,floor2));
        Mockito.when(elevator.getCommittedDirection()).thenReturn(Direction.down);
        Mockito.when(elevator.getTarget()).thenReturn(floor2);

        autoModeExecutor.execute(building);

        Mockito.verify(elevator, Mockito.times(1)).setTarget(floor1);
    }
}
