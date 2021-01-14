package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.ElevatorFloorViewModel;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.List;

public class ElevatorView extends HBox {

    private ElevatorViewModel viewModel;
    private final List<ElevatorFloorViewModel> elevatorFloorViewModelList;

    public ElevatorView(ElevatorViewModel viewModel, List<ElevatorFloorViewModel> elevatorFloorVM) {
        super();
        this.viewModel = viewModel;
        this.elevatorFloorViewModelList = elevatorFloorVM;

        this.getChildren().add(createInformationalColumn());
        this.getChildren().add(createFloorColumn());

        this.setSpacing(8);
        this.setPadding(new Insets(16));
        this.setBackground(new Background(new BackgroundFill(Color.rgb(242, 242, 242), new CornerRadii(8), Insets.EMPTY)));
    }

    private VBox createFloorColumn() {
        var vbox = new VBox();
        vbox.setSpacing(2);

        elevatorFloorViewModelList.sort(Comparator.comparing(ElevatorFloorViewModel::getNumber).reversed());
        for (ElevatorFloorViewModel floorViewModel : elevatorFloorViewModelList) {
            var floorButton = new Button();
            floorButton.idProperty().bind(floorViewModel.getId());
            floorButton.textProperty().bind(floorViewModel.getTitle());
            floorButton.textFillProperty().bind(floorViewModel.getTitleFill());
            floorButton.backgroundProperty().bind(floorViewModel.getButtonBackground());
            ElevatorButtonClickEvent event = new ElevatorButtonClickEvent(viewModel, floorViewModel);
            floorButton.setOnMouseClicked(event::onClicked);
            vbox.getChildren().add(floorButton);
        }
        return vbox;
    }

    private VBox createInformationalColumn() {
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

        vbox.getChildren().add(createTextWithBinding("speed", viewModel.getSpeed()));
        vbox.getChildren().add(createTextWithBinding("capacity", viewModel.getCapacity()));
        vbox.getChildren().add(createTextWithBinding("weight", viewModel.getWeight()));
        vbox.getChildren().add(createTextWithBinding("doorStatus", viewModel.getDoorStatus()));

        return vbox;
    }

    private Text createTextWithBinding(String id, StringProperty property) {
        var textField = new Text();

        String idString = viewModel.getId().get() + "-" + id;
        textField.idProperty().bind(new SimpleStringProperty(idString));
        textField.textProperty().bind(property);
        textField.setFont(Font.font("Arial", FontWeight.LIGHT, 10));
        return textField;
    }
}

class ElevatorButtonClickEvent extends ActionEvent {
    private final ElevatorViewModel elevatorViewModel;
    private final ElevatorFloorViewModel floorViewModel;

    public ElevatorButtonClickEvent(ElevatorViewModel elevatorViewModel, ElevatorFloorViewModel floorViewModel) {
        this.elevatorViewModel = elevatorViewModel;
        this.floorViewModel = floorViewModel;
    }

    public void onClicked(MouseEvent event) {
        elevatorViewModel.onFloorButtonPressed(floorViewModel);
    }
}
