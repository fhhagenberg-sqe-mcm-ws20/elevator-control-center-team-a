package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.view.BuildingView;
import at.fhhgb.team.a.elevators.view.ElevatorsView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var elevators = new ArrayList<Elevator>();
        elevators.add(new Elevator(1,1,1));
        elevators.add(new Elevator(2,1,1));
        elevators.add(new Elevator(3,1,1));
        elevators.add(new Elevator(4,1,1));
        var floors = new ArrayList<Floor>();
        floors.add(new Floor(1));
        floors.add(new Floor(2));
        floors.add(new Floor(3));
        floors.add(new Floor(4));
        var building = new Building(10, elevators, floors);

        var buildingView = new BuildingView(building);
        var layout = new BorderPane(buildingView);

        var scene = new Scene(layout, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
