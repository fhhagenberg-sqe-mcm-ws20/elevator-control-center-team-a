package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ViewModelFactoryTest {
    Building building;
    ViewModelFactory viewModelFactory;

    @BeforeEach
    void setUp() {
        building = Mockito.mock(Building.class);
        viewModelFactory = new ViewModelFactory(building);
    }

    @Test
    void testCreateAllFloorViewModelsFromBuilding() {
        var floors = new ArrayList<Floor>();
        floors.add(Mockito.mock(Floor.class));
        floors.add(Mockito.mock(Floor.class));
        Mockito.when(building.getFloors()).thenReturn(floors);
        Mockito.when(building.getFloorNum()).thenReturn(2);

        var list = viewModelFactory.createAllFloorViewModels();
        assertThat(list).hasSize(2);
    }

    @Test
    void testCreateModeViewModel() {
        var modeViewModel = viewModelFactory.createModeViewModel();
        assertThat(modeViewModel.isManualModeEnabled()).isFalse();
    }

    @Test
    void testCreateAllElevatorViewModelsFromBuilding() {
        var elevators = new ArrayList<Elevator>();
        elevators.add(Mockito.mock(Elevator.class));
        elevators.add(Mockito.mock(Elevator.class));
        Mockito.when(building.getElevators()).thenReturn(elevators);
        Mockito.when(building.getElevatorNum()).thenReturn(2);

        var list = viewModelFactory.createAllElevatorViewModels();
        assertThat(list).hasSize(2);
    }

    @Test
    void testCreateAllElevatorFloorViewModelsFromBuilding() {
        var floors = new ArrayList<Floor>();
        floors.add(Mockito.mock(Floor.class));
        floors.add(Mockito.mock(Floor.class));

        var elevator1 = Mockito.mock(Elevator.class);
        var elevator2 = Mockito.mock(Elevator.class);
        Mockito.when(elevator1.getFloors()).thenReturn(floors);
        Mockito.when(elevator2.getFloors()).thenReturn(floors);

        var elevators = new ArrayList<Elevator>();
        elevators.add(elevator1);
        elevators.add(elevator2);

        Mockito.when(building.getElevators()).thenReturn(elevators);
        Mockito.when(building.getElevatorNum()).thenReturn(2);

        var viewModelMap = viewModelFactory.createAllElevatorFloorViewModels();
        assertThat(viewModelMap).hasSize(2);
        assertThat(viewModelMap.get(elevator1)).hasSize(2);
        assertThat(viewModelMap.get(elevator2)).hasSize(2);

        var firstElevatorViewModels = viewModelFactory.createElevatorFloorViewModelFor(elevator1);
        assertThat(firstElevatorViewModels).hasSize(2);

        var secondElevatorViewModels = viewModelFactory.createElevatorFloorViewModelFor(elevator1);
        assertThat(secondElevatorViewModels).hasSize(2);
    }
}
