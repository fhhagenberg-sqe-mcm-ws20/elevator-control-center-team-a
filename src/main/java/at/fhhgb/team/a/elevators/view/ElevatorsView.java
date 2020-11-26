package at.fhhgb.team.a.elevators.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class ElevatorsView extends VBox {
    public ElevatorsView(List<ElevatorView> elevatorViews) {
        super();
        initializeView(elevatorViews);
    }

    private void initializeView(List<ElevatorView> elevatorViews) {
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

        elevatorViews.forEach((elevatorView) -> elevatorsPane.getChildren().add(elevatorView));
    }
}
