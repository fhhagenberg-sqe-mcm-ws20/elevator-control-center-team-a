package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorFloorViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;

public class ElevatorView extends HBox {
    public ElevatorView(ElevatorViewModel viewModel) {
        super();
        this.getChildren().add(createInformationalColumn(viewModel));
        this.getChildren().add(createFloorColumn(viewModel));

        this.setSpacing(8);
        this.setPadding(new Insets(16));
        this.setBackground(new Background(new BackgroundFill(Color.rgb(242, 242, 242), new CornerRadii(8), Insets.EMPTY)));
    }

    private VBox createFloorColumn(ElevatorViewModel viewModel) {
        var vbox = new VBox();
        vbox.setSpacing(2);

        var floors = viewModel.getFloors();
        floors.sort(Comparator.comparing(ElevatorFloorViewModel::getNumber).reversed());
        for (ElevatorFloorViewModel floorViewModel : floors) {
            var floorButton = new Button("1");
            floorButton.textProperty().bind(floorViewModel.getTitle());
            floorButton.textFillProperty().bind(floorViewModel.getTitleFill());
            floorButton.backgroundProperty().bind(floorViewModel.getButtonBackground());
            vbox.getChildren().add(floorButton);
        }
        return vbox;
    }

    private VBox createInformationalColumn(ElevatorViewModel viewModel) {
        var vbox = new VBox();
        vbox.setSpacing(8);
        vbox.setAlignment(Pos.CENTER_LEFT);

        var titleBox = new HBox();
        titleBox.setSpacing(8);

        var title = new Text();
        title.textProperty().bind(viewModel.getTitle());
        title.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
        titleBox.getChildren().add(title);

        var directionImage = new ImageView();
        directionImage.imageProperty().bind(viewModel.getDirection());
        directionImage.setFitHeight(12);
        directionImage.setFitWidth(12);
        titleBox.getChildren().add(directionImage);

        vbox.getChildren().add(titleBox);

        var speed = new Text();
        speed.textProperty().bind(viewModel.getSpeed());
        speed.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(speed);

        var capacity = new Text();
        capacity.textProperty().bind(viewModel.getCapacity());
        capacity.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(capacity);

        var weight = new Text();
        weight.textProperty().bind(viewModel.getWeight());
        weight.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(weight);

        var doorStatus = new Text();
        doorStatus.textProperty().bind(viewModel.getDoorStatus());
        doorStatus.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        vbox.getChildren().add(doorStatus);

        return vbox;
    }
}
