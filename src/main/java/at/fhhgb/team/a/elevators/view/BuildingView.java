package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.BuildingViewModel;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class BuildingView extends FlowPane {
    public BuildingView(BuildingViewModel viewModel) {
        super();
        initializeView(viewModel);
    }

    private void initializeView(BuildingViewModel viewModel) {
        this.setWidth(640);
        this.setPadding(new Insets(16));
        var elevatorsView = new ElevatorsView(viewModel);
        this.getChildren().add(elevatorsView);
        var floorsView = new FloorsView(viewModel);
        this.getChildren().add(floorsView);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), new CornerRadii(8), Insets.EMPTY)));
    }
}
