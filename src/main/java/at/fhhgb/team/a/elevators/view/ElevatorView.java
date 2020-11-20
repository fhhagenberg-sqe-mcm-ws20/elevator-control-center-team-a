package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.List;

public class ElevatorView extends HBox {
    public ElevatorView(Elevator elevator, List<Floor> floors) {
        super();
        this.getChildren().add(createInformationalColumn(elevator));
        this.getChildren().add(createFloorColumn(elevator, floors));

        this.setSpacing(8);
        this.setPadding(new Insets(16));
        this.setBackground(new Background(new BackgroundFill(Color.rgb(242, 242, 242), new CornerRadii(8), Insets.EMPTY)));
    }

    private VBox createFloorColumn(Elevator elevator, List<Floor> floors) {
        var vbox = new VBox();
        vbox.setSpacing(2);

        floors.sort(Comparator.comparing(Floor::getNumber).reversed());
        for (Floor floor : floors) {
            var floorButton = new Button("1");
            floorButton.setTextFill(Color.WHITE);
            floorButton.setBackground(new Background(new BackgroundFill(Color.rgb(123, 206, 123), new CornerRadii(4), Insets.EMPTY)));
            vbox.getChildren().add(floorButton);
        }
        return vbox;
    }

    private VBox createInformationalColumn(Elevator elevator) {
        var vbox = new VBox();
        vbox.setSpacing(8);

        var titleBox = new HBox();
        titleBox.setSpacing(8);
        var title = new Text("Elevator 5");
        title.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
        titleBox.getChildren().add(title);
        var directionImage = new ImageView("images/down-arrow.png");
        directionImage.setFitHeight(12);
        directionImage.setFitWidth(12);
        titleBox.getChildren().add(directionImage);
        vbox.getChildren().add(titleBox);

        var speed = new Text("10 km/h");
        speed.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(speed);

        var weight = new Text("549 kg");
        weight.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(weight);

        return vbox;
    }
}
