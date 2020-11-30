package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.provider.ViewModelProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class ElevatorViewModel {

    private final Elevator elevator;

    private final StringProperty title;
    private final ObjectProperty<Image> direction;
    private final StringProperty speed;
    private final StringProperty capacity;
    private final StringProperty weight;
    private final StringProperty doorStatus;

    public ElevatorViewModel(Elevator elevator) {
        this.elevator = elevator;

        String titleString = "Elevator " + elevator.getNumber();
        title = new SimpleStringProperty(titleString);

        Image image = null;
        try {
            image = new Image("images/up-arrow.png");

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
        } catch (RuntimeException e) {
            System.out.println("Cannot load image - JavaFX not initialized");
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

    public void onFloorButtonPressed(ElevatorFloorViewModel pressedFloorVM) {
        ModeViewModel modeViewModel = ViewModelProvider.getInstance().getModeViewModel();
        if(modeViewModel.isManualModeEnabled()) {
            var oldFloorVM =
                    ViewModelProvider.getInstance().getElevatorFloorViewModel(elevator, elevator.getTarget());
            elevator.setTarget(pressedFloorVM.getFloor());
            pressedFloorVM.onTargetFloorChanged();
            oldFloorVM.onTargetFloorChanged();
        }
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
    public Elevator getElevator() {
        return elevator;
    }

    public int getElevatorNumber() {
        return elevator.getNumber();
    }
}
