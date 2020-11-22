package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.model.Position;
import at.fhhgb.team.a.elevators.view.BuildingView;
import at.fhhgb.team.a.elevators.view.ElevatorsView;
import at.fhhgb.team.a.elevators.viewmodels.BuildingViewModel;
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
        elevators.add(new Elevator(1,10,100));
        elevators.add(new Elevator(2,20,200));
        elevators.add(new Elevator(3,5,50));
        elevators.add(new Elevator(4,10,1));
        var floors = new ArrayList<Floor>();
        floors.add(new Floor(0));
        floors.add(new Floor(1));
        floors.add(new Floor(2));
        floors.add(new Floor(3));

        var currentPosition = new Position(0, floors.get(0));

        elevators.get(0).setCurrentPosition(currentPosition);
        elevators.get(0).setTarget(floors.get(3));

        elevators.get(1).setCurrentPosition(currentPosition);
        elevators.get(1).setTarget(floors.get(2));

        elevators.get(2).setCurrentPosition(currentPosition);
        elevators.get(2).setTarget(floors.get(1));

        elevators.get(3).setCurrentPosition(currentPosition);
        elevators.get(3).setTarget(floors.get(0));

        var building = new Building(10, elevators, floors);

        var viewModel = new BuildingViewModel(building);
        var buildingView = new BuildingView(viewModel);
        var layout = new BorderPane(buildingView);

        var scene = new Scene(layout, 640, 480);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
