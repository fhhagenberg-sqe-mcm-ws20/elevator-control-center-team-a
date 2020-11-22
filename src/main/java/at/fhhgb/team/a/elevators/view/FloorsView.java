package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.BuildingViewModel;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FloorsView extends VBox {
    public FloorsView(BuildingViewModel viewModel) {
        super();
        this.setPadding(new Insets(16,0,0,0));

        var title = new Text("Floors");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        this.getChildren().add(title);
        this.setSpacing(12);

        /*floors.sort(Comparator.comparing(Floor::getNumber).reversed());
        for (Floor floor : floors) {
            var hbox = new HBox();
            var floorText = new Text("Floor 1");
            floorText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 12));
            hbox.getChildren().add(floorText);

            var buttonImage1 = new ImageView("images/up-chevron.png");
            buttonImage1.setFitHeight(12);
            buttonImage1.setFitWidth(12);
            hbox.getChildren().add(buttonImage1);


            var buttonImage2 = new ImageView("images/down-chevron.png");
            buttonImage2.setFitHeight(12);
            buttonImage2.setFitWidth(12);
            var stackButton2 = new StackPane(buttonImage2);
            stackButton2.setPadding(new Insets(4));
            stackButton2.setBackground(new Background(new BackgroundFill(Color.rgb(123, 206, 123), new CornerRadii(8), Insets.EMPTY)));
            hbox.getChildren().add(stackButton2);

            hbox.setSpacing(8);
            hbox.setPadding(new Insets(8));
            hbox.setBackground(new Background(new BackgroundFill(Color.rgb(242, 242, 242), new CornerRadii(8), Insets.EMPTY)));

            this.getChildren().add(hbox);
        }*/
    }
}
