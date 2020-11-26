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

public class FloorViewModel {

    private Floor floor;

    private StringProperty title;
    private ObjectProperty<Background> upButtonBackground;
    private ObjectProperty<Background> downButtonBackground;

    public FloorViewModel(Floor floor) {
        this.floor = floor;

        String titleString = "Floor " + floor.getNumber();
        title = new SimpleStringProperty(titleString);

        CornerRadii cornerRadii = new CornerRadii(8);
        BackgroundFill pressedBackgroundFill = new BackgroundFill(Color.rgb(123, 206, 123), cornerRadii, Insets.EMPTY);
        BackgroundFill notPressedBackgroundFill = new BackgroundFill(Color.rgb(255, 255, 255), cornerRadii, Insets.EMPTY);

        BackgroundFill backgroundFillUp = floor.isUpButtonOn() ? pressedBackgroundFill : notPressedBackgroundFill;
        BackgroundFill backgroundFillDown = floor.isDownButtonOn() ? pressedBackgroundFill : notPressedBackgroundFill;

        Background backgroundUp = new Background(backgroundFillUp);
        upButtonBackground = new SimpleObjectProperty<>(backgroundUp);

        Background backgroundDown = new Background(backgroundFillDown);
        downButtonBackground = new SimpleObjectProperty<>(backgroundDown);
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

    public ObjectProperty<Background> getUpButtonBackground() {
        return upButtonBackground;
    }

    public ObjectProperty<Background> getDownButtonBackground() {
        return downButtonBackground;
    }
}
