package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.provider.ViewModelProvider;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorFloorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.FloorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ModeViewModel;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ViewModelProviderTest {
    ViewModelFactory viewModelFactory;

    @BeforeEach
    void setUp() {
        viewModelFactory = Mockito.mock(ViewModelFactory.class);
        ViewModelProvider.getInstance().init(viewModelFactory);
    }

    @Test
    void testGetFloorViewModels() {
        var floors = new ArrayList<FloorViewModel>();
        floors.add(Mockito.mock(FloorViewModel.class));
        Mockito.when(viewModelFactory.createAllFloorViewModels()).thenReturn(floors);
        var providedViewModels = ViewModelProvider.getInstance().getFloorViewModelList();
        assertThat(providedViewModels.size()).isEqualTo(floors.size());
    }

    @Test
    void testGetElevatorViewModels() {
        var elevators = new ArrayList<ElevatorViewModel>();
        elevators.add(Mockito.mock(ElevatorViewModel.class));
        Mockito.when(viewModelFactory.createAllElevatorViewModels()).thenReturn(elevators);
        var providedViewModels = ViewModelProvider.getInstance().getElevatorViewModelList();
        assertThat(providedViewModels.size()).isEqualTo(elevators.size());
    }

    @Test
    void testGetElevatorFloorViewModels() {
        var floor = Mockito.mock(Floor.class);
        var elevator = Mockito.mock(Elevator.class);
        Map<Elevator, Map<Floor,ElevatorFloorViewModel>> elevatorMap = new HashMap<>();
        Map<Floor, ElevatorFloorViewModel> floorMap = new HashMap<>();
        floorMap.put(floor, Mockito.mock(ElevatorFloorViewModel.class));
        elevatorMap.put(elevator, floorMap);
        Mockito.when(viewModelFactory.createAllElevatorFloorViewModels()).thenReturn(elevatorMap);

        var providedViewModelsForElevator = ViewModelProvider.getInstance().getElevatorFloorViewModelList(elevator);
        assertThat(providedViewModelsForElevator.size()).isEqualTo(1);

        var providedViewModelsForFloorAndElevator = ViewModelProvider.getInstance().getElevatorFloorViewModel(elevator, floor);
        assertThat(providedViewModelsForFloorAndElevator).isNotNull();
    }

    @Test
    void testGetModeViewModel() {
        var mode = Mockito.mock(ModeViewModel.class);
        Mockito.when(mode.isManualModeEnabled()).thenReturn(true);
        Mockito.when(viewModelFactory.createModeViewModel()).thenReturn(mode);
        var providedViewModel = ViewModelProvider.getInstance().getModeViewModel();
        assertThat(providedViewModel.isManualModeEnabled()).isEqualTo(mode.isManualModeEnabled());
    }
}
