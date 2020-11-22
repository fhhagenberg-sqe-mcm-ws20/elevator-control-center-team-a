package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.BuildingViewModel;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ElevatorsView extends VBox {
    public ElevatorsView(BuildingViewModel viewModel) {
        super();
        initializeView(viewModel);
    }

    private void initializeView(BuildingViewModel viewModel) {
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
        viewModel.getElevators().forEach((elevatorViewModel) -> elevatorsPane.getChildren().add(new ElevatorView(elevatorViewModel)));
    }
}
