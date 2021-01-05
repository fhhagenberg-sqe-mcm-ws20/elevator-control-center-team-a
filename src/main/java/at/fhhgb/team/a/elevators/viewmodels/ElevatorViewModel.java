package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.ECCMode;
import at.fhhgb.team.a.elevators.model.Elevator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.Observable;
import java.util.Observer;

public class ElevatorViewModel implements Observer {

    private final Elevator elevator;
    private final ECCMode eccMode;

    private final StringProperty id;
    private final StringProperty title;
    private final ObjectProperty<Image> direction;
    private final StringProperty speed;
    private final StringProperty capacity;
    private final StringProperty weight;
    private final StringProperty doorStatus;

    public ElevatorViewModel(Elevator elevator, ECCMode eccMode) {
        this.elevator = elevator;
        this.eccMode = eccMode;

        this.elevator.addObserver(this);

        String idString = "e" + elevator.getNumber();
        id = new SimpleStringProperty(idString);

        String titleString = "Elevator " + elevator.getNumber();
        title = new SimpleStringProperty(titleString);

        Image image = loadDirectionImage();

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
        if(!eccMode.isAutomaticModeEnabled()) {
            elevator.setTarget(pressedFloorVM.getFloor());
            pressedFloorVM.onTargetFloorChanged();
        }
    }

    public StringProperty getId() {
        return id;
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

    @Override
    public void update(Observable o, Object arg) {
        String idString = "e" + elevator.getNumber();
        id.setValue(idString);

        String titleString = "Elevator " + elevator.getNumber();
        title.setValue(titleString);

        Image image = loadDirectionImage();

        direction.setValue(image);

        String speedString = "speed: " + elevator.getSpeed() + " km/h";
        speed.setValue(speedString);

        String capacityString = "max. capacity: " + elevator.getCapacity() + " people";
        capacity.setValue(capacityString);

        String weightString = "weight: " + elevator.getWeight() + " kg";
        weight.setValue(weightString);

        String doorStatusString = "doors: " + elevator.getDoorStatus();
        doorStatus.setValue(doorStatusString);
    }

    private Image loadDirectionImage() {
        try {
            switch (elevator.getCommittedDirection()) {
                case up:
                    return new Image("images/up-arrow.png");
                case down:
                    return new Image("images/down-arrow.png");
                case uncommitted:
                    return new Image("images/right-arrow.png");
            }
        } catch (RuntimeException e) {
            System.out.println("Cannot load image - JavaFX not initialized");
        }

        return null;
    }
}
