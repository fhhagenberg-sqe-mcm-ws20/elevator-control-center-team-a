package at.fhhgb.team.a.elevators.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class BuildingView extends FlowPane {
    public BuildingView(ElevatorsView elevatorsView, FloorsView floorsView) {
        super();
        initializeView(elevatorsView, floorsView);
    }

    private void initializeView(ElevatorsView elevatorsView, FloorsView floorsView) {
        this.setWidth(640);
        this.setPadding(new Insets(16));
        this.getChildren().add(elevatorsView);
        this.getChildren().add(floorsView);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), new CornerRadii(8), Insets.EMPTY)));
    }
}
