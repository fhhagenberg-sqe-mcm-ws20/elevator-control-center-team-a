package at.fhhgb.team.a.elevators.viewmodels;

import at.fhhgb.team.a.elevators.model.Floor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.Observable;

public class FloorViewModel {

    private final Floor floor;

    private final BackgroundFill pressedBackgroundFill;
    private final BackgroundFill notPressedBackgroundFill;

    private final StringProperty downButtonId;
    private final StringProperty upButtonId;
    private final StringProperty title;
    private final ObjectProperty<Background> downButtonBackground;
    private final ObjectProperty<Background> upButtonBackground;

    public FloorViewModel(Floor floor) {
        this.floor = floor;

        this.floor.addObserver(this::update);

        String downButtonIdString = "f" + floor.getNumber() + "-down";
        downButtonId = new SimpleStringProperty(downButtonIdString);

        String upButtonIdString = "f" + floor.getNumber() + "-up";
        upButtonId = new SimpleStringProperty(upButtonIdString);

        String titleString = "Floor " + floor.getNumber();
        title = new SimpleStringProperty(titleString);

        CornerRadii cornerRadii = new CornerRadii(8);
        pressedBackgroundFill = new BackgroundFill(Color.rgb(123, 206, 123), cornerRadii, Insets.EMPTY);
        notPressedBackgroundFill = new BackgroundFill(Color.rgb(255, 255, 255), cornerRadii, Insets.EMPTY);

        Background backgroundDown = getBackgroundDownButton();
        downButtonBackground = new SimpleObjectProperty<>(backgroundDown);

        Background backgroundUp = getBackgroundUpButton();
        upButtonBackground = new SimpleObjectProperty<>(backgroundUp);
    }

    public Floor getFloor() {
        return floor;
    }

    public int getNumber() {
        return floor.getNumber();
    }

    public StringProperty getDownButtonId() {
        return downButtonId;
    }

    public StringProperty getUpButtonId() {
        return upButtonId;
    }

    public StringProperty getTitle() {
        return title;
    }

    public ObjectProperty<Background> getUpButtonBackground() {
        return upButtonBackground;
    }

    public ObjectProperty<Background> getDownButtonBackground() {
        return downButtonBackground;
    }

    private Background getBackgroundDownButton() {
        BackgroundFill backgroundFillDown = floor.isDownButtonOn() ? pressedBackgroundFill : notPressedBackgroundFill;
        return new Background(backgroundFillDown);
    }

    private Background getBackgroundUpButton() {
        BackgroundFill backgroundFillUp = floor.isUpButtonOn() ? pressedBackgroundFill : notPressedBackgroundFill;
        return new Background(backgroundFillUp);
    }

    private void update(Observable o, Object arg) {
        Background backgroundDown = getBackgroundDownButton();
        downButtonBackground.setValue(backgroundDown);

        Background backgroundUp = getBackgroundUpButton();
        upButtonBackground.setValue(backgroundUp);
    }
}
