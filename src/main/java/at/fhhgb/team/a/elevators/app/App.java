package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.model.Position;
import at.fhhgb.team.a.elevators.provider.ViewModelProvider;
import at.fhhgb.team.a.elevators.view.*;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var elevators = new ArrayList<Elevator>();
        elevators.add(new Elevator(1, 10, 100));
        elevators.add(new Elevator(2, 20, 200));
        elevators.add(new Elevator(3, 5, 50));
        elevators.add(new Elevator(4, 10, 1));
        var floors = new ArrayList<Floor>();
        floors.add(new Floor(0));
        floors.add(new Floor(1));
        floors.add(new Floor(2));
        floors.add(new Floor(3));

        var currentPosition = new Position(0, floors.get(0));

        elevators.get(0).setCurrentPosition(currentPosition);
        elevators.get(0).setTarget(floors.get(3));
        elevators.get(0).addFloorService(floors.get(0), false);
        elevators.get(0).addFloorService(floors.get(1), false);
        elevators.get(0).addFloorService(floors.get(2), true);
        elevators.get(0).addFloorService(floors.get(3), true);

        elevators.get(1).setCurrentPosition(currentPosition);
        elevators.get(1).setTarget(floors.get(2));
        elevators.get(1).addFloorService(floors.get(0), false);
        elevators.get(1).addFloorService(floors.get(2), false);
        elevators.get(1).addFloorService(floors.get(3), false);

        elevators.get(2).setCurrentPosition(currentPosition);
        elevators.get(2).setTarget(floors.get(1));
        elevators.get(2).addFloorService(floors.get(0), false);
        elevators.get(2).addFloorService(floors.get(1), false);
        elevators.get(2).addFloorService(floors.get(2), false);
        elevators.get(2).addFloorService(floors.get(3), false);

        elevators.get(3).setCurrentPosition(currentPosition);
        elevators.get(3).setTarget(floors.get(0));
        elevators.get(3).addFloorService(floors.get(0), false);
        elevators.get(3).addFloorService(floors.get(3), false);

        floors.get(0).pressDownButton();
        floors.get(0).pressUpButton();
        floors.get(1).pressDownButton();
        floors.get(2).pressUpButton();

        var building = new Building(10, elevators, floors);

        ViewModelFactory viewModelFactory = new ViewModelFactory(building);
        ViewModelProvider viewModelProvider = new ViewModelProvider(viewModelFactory);

        var modeViewModel = viewModelProvider.getModeViewModel();
        var headerView = new HeaderView(modeViewModel);

        var elevatorViews = initElevatorViews(viewModelProvider);
        var elevatorsView = new ElevatorsView(elevatorViews);

        var floorViewModels = viewModelProvider.getFloorViewModelList();
        var floorsView = new FloorsView(floorViewModels);

        var buildingView = new BuildingView(elevatorsView, floorsView);

        var layout = new VBox();
        VBox.setMargin(headerView, new Insets(8, 16, 8, 16));
        layout.getChildren().add(headerView);
        layout.getChildren().add(buildingView);

        var scene = new Scene(layout, 640, 660);

        stage.setScene(scene);
        stage.show();
    }

    private List<ElevatorView> initElevatorViews(ViewModelProvider viewModelProvider) {
        List<ElevatorView> elevatorViews = new ArrayList<>();
        var elevatorVMs = viewModelProvider.getElevatorViewModelList();
        elevatorVMs.sort(Comparator.comparing(ElevatorViewModel::getElevatorNumber));
        elevatorVMs.forEach(elevatorViewModel -> {
            var elevatorFloorVM =
                    viewModelProvider.getElevatorFloorViewModelList(elevatorViewModel.getElevator());
            elevatorViews.add(new ElevatorView(elevatorViewModel, elevatorFloorVM));
        });
        return elevatorViews;
    }

    public static void main(String[] args) throws Exception {
        IElevator elevatorApi = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
        ElevatorControlCenter controlCenter = new ElevatorControlCenter(elevatorApi);
        launch();
    }

}
