package at.fhhgb.team.a.elevators.factory;

import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.ECCMode;
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

public class ViewModelFactory {

    private final Building building;
    private final ECCMode eccMode;

    public ViewModelFactory(Building building) {
        this.building = building;
        eccMode = new ECCMode();
    }

    public ModeViewModel createModeViewModel() {
        return new ModeViewModel(eccMode);
    }


    public ElevatorViewModel createElevatorViewModel(Elevator elevator) {
        return new ElevatorViewModel(elevator);
    }
    public List<ElevatorViewModel> createAllElevatorViewModels() {
        List<ElevatorViewModel> list = new ArrayList<>();
        building.getElevators().forEach(elevator -> list.add(createElevatorViewModel(elevator)));
        return list;
    }

    public List<FloorViewModel> createAllFloorViewModels() {
        List<FloorViewModel> list = new ArrayList<>();
        building.getFloors().forEach(floor -> list.add(new FloorViewModel(floor)));
        return list;
    }

    public Map<Elevator, Map<Floor, ElevatorFloorViewModel>> createAllElevatorFloorViewModels() {
        Map<Elevator, Map<Floor,ElevatorFloorViewModel>> map = new HashMap<>();
        building.getElevators().forEach(elevator -> map.put(elevator, createElevatorFloorViewModelFor(elevator)));
        return map;
    }

    public Map<Floor, ElevatorFloorViewModel> createElevatorFloorViewModelFor(Elevator elevator) {
        Map<Floor, ElevatorFloorViewModel> map = new HashMap<>();
        elevator.getFloors().forEach(floor -> map.put(floor, new ElevatorFloorViewModel(elevator, floor)));
        return map;
    }
}
