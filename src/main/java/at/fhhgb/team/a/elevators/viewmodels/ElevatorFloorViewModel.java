package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.view.Colors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ElevatorFloorViewModel {

    private final Floor floor;
    private final Elevator elevator;

    private final BackgroundFill serviceBackgroundFill;
    private final BackgroundFill noServiceBackgroundFill;
    private final BackgroundFill targetBackgroundFill;
    private final BackgroundFill currentBackgroundFill;

    private final StringProperty title;
    private final ObjectProperty<Paint> titleFill;
    private final ObjectProperty<Background> buttonBackground;

    public ElevatorFloorViewModel(Elevator elevator, Floor floor) {
        this.floor = floor;
        this.elevator = elevator;

        CornerRadii cornerRadii = new CornerRadii(4);
        serviceBackgroundFill = new BackgroundFill(Colors.serviceWhite, cornerRadii, Insets.EMPTY);
        noServiceBackgroundFill = new BackgroundFill(Colors.noServiceGray, cornerRadii, Insets.EMPTY);
        targetBackgroundFill = new BackgroundFill(Colors.targetGreen, cornerRadii, Insets.EMPTY);
        currentBackgroundFill = new BackgroundFill(Colors.currentYellow, cornerRadii, Insets.EMPTY);

        String titleString = String.valueOf(floor.getNumber());
        title = new SimpleStringProperty(titleString);

        Paint color = elevator.getElevatorButton(floor.getNumber()) ? Color.GREEN : Color.BLACK;
        titleFill = new SimpleObjectProperty<>(color);

        Background background = getBackgroundFor(elevator, floor);
        buttonBackground = new SimpleObjectProperty<>(background);
    }

    public void onTargetFloorChanged() {
        Background newBackground = getBackgroundFor(elevator, floor);
        buttonBackground.setValue(newBackground);
    }

    public Floor getFloor() {
        return floor;
    }

    public int getNumber() {
        return floor.getNumber();
    }

    public StringProperty getTitle() {
        return title;
    }

    public ObjectProperty<Paint> getTitleFill() {
        return titleFill;
    }

    public ObjectProperty<Background> getButtonBackground() {
        return buttonBackground;
    }

    private Background getBackgroundFor(Elevator elevator, Floor floor) {
        BackgroundFill backgroundFill = elevator.servicesFloor(floor.getNumber()) ? serviceBackgroundFill : noServiceBackgroundFill;

        if (elevator.getTarget() != null) {
            backgroundFill = elevator.getTarget() == floor ? targetBackgroundFill : backgroundFill;
        }

        if (elevator.getCurrentPosition() != null) {
            backgroundFill = elevator.getCurrentPosition().getClosestFloor() == floor ? currentBackgroundFill : backgroundFill;
        }
        return new Background(backgroundFill);
    }
}
