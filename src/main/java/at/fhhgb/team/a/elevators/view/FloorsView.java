package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.FloorViewModel;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.List;

public class FloorsView extends VBox {
    public FloorsView(List<FloorViewModel> viewModels) {
        super();
        this.setPadding(new Insets(16,0,0,0));

        var title = new Text("Floors");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        this.getChildren().add(title);
        this.setSpacing(12);

        viewModels.sort(Comparator.comparing(FloorViewModel::getNumber).reversed());
        for (FloorViewModel floorViewModel : viewModels) {
            var hbox = new HBox();
            var floorText = new Text("Floor 1");
            floorText.textProperty().bind(floorViewModel.getTitle());
            floorText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
            hbox.getChildren().add(floorText);

            var upButtonImageView = new ImageView("images/up-chevron.png");
            upButtonImageView.setFitHeight(12);
            upButtonImageView.setFitWidth(12);

            var stackButtonUp = new StackPane(upButtonImageView);
            stackButtonUp.setPadding(new Insets(4));
            stackButtonUp.idProperty().bind(floorViewModel.getUpButtonId());
            stackButtonUp.backgroundProperty().bind(floorViewModel.getUpButtonBackground());
            hbox.getChildren().add(stackButtonUp);

            var downButtonImageView = new ImageView("images/down-chevron.png");
            downButtonImageView.setFitHeight(12);
            downButtonImageView.setFitWidth(12);

            var stackButtonDown = new StackPane(downButtonImageView);
            stackButtonDown.setPadding(new Insets(4));
            stackButtonDown.idProperty().bind(floorViewModel.getDownButtonId());
            stackButtonDown.backgroundProperty().bind(floorViewModel.getDownButtonBackground());
            hbox.getChildren().add(stackButtonDown);

            hbox.setSpacing(8);
            hbox.setPadding(new Insets(8));
            hbox.setBackground(new Background(new BackgroundFill(Color.rgb(242, 242, 242), new CornerRadii(8), Insets.EMPTY)));

            this.getChildren().add(hbox);
        }
    }
}
