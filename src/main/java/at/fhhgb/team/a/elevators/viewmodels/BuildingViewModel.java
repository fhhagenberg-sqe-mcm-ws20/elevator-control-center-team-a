package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;

import java.util.ArrayList;
import java.util.List;

public class BuildingViewModel {

    private final Building building;

    public BuildingViewModel(Building building) {
        this.building = building;
    }

    public List<ElevatorViewModel> getElevators() {
        ArrayList<ElevatorViewModel> viewModels = new ArrayList<>();

        for (Elevator elevator: building.getElevators()) {
            ElevatorViewModel viewModel = new ElevatorViewModel(elevator, building.getFloors());
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    public List<FloorViewModel> getFloors() {
        ArrayList<FloorViewModel> viewModels = new ArrayList<>();

        for (Floor floor: building.getFloors()) {
            FloorViewModel viewModel = new FloorViewModel(floor);
            viewModels.add(viewModel);
        }

        return viewModels;
    }
}
