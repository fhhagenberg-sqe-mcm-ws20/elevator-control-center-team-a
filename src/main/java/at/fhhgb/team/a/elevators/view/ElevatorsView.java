package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ElevatorsView extends VBox {
    public ElevatorsView(Building building) {
        super();
        initializeView(building);
    }

    private void initializeView(Building building) {
        this.setWidth(640);

        var title = new Text("Elevators");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        this.getChildren().add(title);

        var elevatorsPane = new FlowPane();
        elevatorsPane.setPrefWidth(640);
        elevatorsPane.setPadding(new Insets(16,0,0,0));
        elevatorsPane.setHgap(16);
        elevatorsPane.setVgap(16);
        this.getChildren().add(elevatorsPane);
        building.getElevators().forEach((elevator) -> elevatorsPane.getChildren().add(new ElevatorView(elevator, building.getFloors())));
    }
}
