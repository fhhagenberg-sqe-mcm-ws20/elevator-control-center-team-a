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

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws RemoteException, NotBoundException, MalformedURLException {
        IElevator elevatorApi = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");

        ElevatorControlCenter controlCenter = new ElevatorControlCenter(elevatorApi);
        controlCenter.startPolling();

        Building building = controlCenter.getBuilding();
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

    public static void main(String[] args) {
        launch();
    }

}
