package at.fhhgb.team.a.elevators.provider;

import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorFloorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.FloorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ModeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModelProvider {

    private final Map<Elevator, ElevatorViewModel> elevatorViewModelSet;
    private final Map<Elevator, Map<Floor, ElevatorFloorViewModel>> elevatorFloorViewModelSet;
    private final Map<Floor, FloorViewModel> floorViewModelSet;
    private final ViewModelFactory viewModelFactory;

    private ModeViewModel modeViewModel;

    public ViewModelProvider(ViewModelFactory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
        modeViewModel = null;
        elevatorViewModelSet = new HashMap<>();
        elevatorFloorViewModelSet = new HashMap<>();
        floorViewModelSet = new HashMap<>();
    }

    public ModeViewModel getModeViewModel() {
        if (null == modeViewModel) {
            modeViewModel = viewModelFactory.createModeViewModel();
        }

        return modeViewModel;
    }

    public List<ElevatorViewModel> getElevatorViewModelList() {
        if (elevatorViewModelSet.isEmpty()) {
            viewModelFactory.createAllElevatorViewModels().forEach(viewModel ->
                    elevatorViewModelSet.put(viewModel.getElevator(), viewModel));
        }

        return new ArrayList<>(elevatorViewModelSet.values());
    }

    public List<FloorViewModel> getFloorViewModelList() {
        if (floorViewModelSet.isEmpty()) {
            viewModelFactory.createAllFloorViewModels().forEach(viewModel ->
                    floorViewModelSet.put(viewModel.getFloor(), viewModel));
        }

        return new ArrayList<>(floorViewModelSet.values());
    }

    public ElevatorFloorViewModel getElevatorFloorViewModel(Elevator elevator, Floor floor) {
        if (elevatorFloorViewModelSet.isEmpty()) {
            elevatorFloorViewModelSet.putAll(viewModelFactory.createAllElevatorFloorViewModels());
        }
        return elevatorFloorViewModelSet.get(elevator).get(floor);
    }

    public List<ElevatorFloorViewModel> getElevatorFloorViewModelList(Elevator elevator) {
        if (elevatorFloorViewModelSet.isEmpty()) {
            elevatorFloorViewModelSet.putAll(viewModelFactory.createAllElevatorFloorViewModels());
        }

        return new ArrayList<>(elevatorFloorViewModelSet.get(elevator).values());
    }
}
