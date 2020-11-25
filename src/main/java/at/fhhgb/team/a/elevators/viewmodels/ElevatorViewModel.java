package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ElevatorViewModel {

    private Elevator elevator;
    private List<Floor> floors;

    private StringProperty title;
    private ObjectProperty<Image> direction;
    private StringProperty speed;
    private StringProperty capacity;
    private StringProperty weight;
    private StringProperty doorStatus;

    public ElevatorViewModel(Elevator elevator, List<Floor> floors) {
        this.elevator = elevator;
        this.floors = floors;

        String titleString = "Elevator " + elevator.getNumber();
        title = new SimpleStringProperty(titleString);

        var image = new Image("images/up-arrow.png");

        switch (elevator.getCommittedDirection()) {
            case up:
                image = new Image("images/up-arrow.png");
                break;
            case down:
                image = new Image("images/down-arrow.png");
                break;
            case uncommitted:
                image = new Image("images/right-arrow.png");
                break;
        }

        direction = new SimpleObjectProperty<>(image);

        String speedString = "speed: " + elevator.getSpeed() + " km/h";
        speed = new SimpleStringProperty(speedString);

        String capacityString = "max. capacity: " + elevator.getCapacity() + " people";
        capacity = new SimpleStringProperty(capacityString);

        String weightString = "weight: " + elevator.getWeight() + " kg";
        weight = new SimpleStringProperty(weightString);

        String doorStatusString = "doors: " + elevator.getDoorStatus();
        doorStatus = new SimpleStringProperty(doorStatusString);
    }

    public List<ElevatorFloorViewModel> getFloors() {
        ArrayList<ElevatorFloorViewModel> viewModels = new ArrayList<>();

        for (Floor floor: floors) {
            ElevatorFloorViewModel viewModel = new ElevatorFloorViewModel(elevator, floor);
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    public void onFloorButtonPressed(ElevatorFloorViewModel floorViewModel) {
        elevator.setTarget(floorViewModel.getFloor());
        floorViewModel.onTargetFloorChanged();
    }

    public StringProperty getTitle() {
        return title;
    }

    public ObjectProperty<Image> getDirection() {
        return direction;
    }

    public StringProperty getSpeed() {
        return speed;
    }

    public StringProperty getCapacity() {
        return capacity;
    }

    public StringProperty getWeight() {
        return weight;
    }

    public StringProperty getDoorStatus() {
        return doorStatus;
    }
}
