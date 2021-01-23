package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.factory.ElevatorSystemFactory;
import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.model.ECCMode;
import at.fhhgb.team.a.elevators.provider.ViewModelProvider;
import at.fhhgb.team.a.elevators.threading.ThreadManager;
import at.fhhgb.team.a.elevators.view.*;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private IElevatorSystem controlCenter;
    private VBox rootLayout;
    private ViewModelProvider viewModelProvider;

    public App() { }

    public App(IElevatorSystem controlCenter) {
        this.controlCenter = controlCenter;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        var progressBar = new ProgressBar();
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        var infoMsg = new Label("Please wait. We are trying to connect to the Elevatorsystem.");

        rootLayout = new VBox();
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().add(progressBar);
        rootLayout.getChildren().add(infoMsg);

        var scene = new Scene(rootLayout, 640, 660);

        stage.setScene(scene);
        stage.setOnCloseRequest(this::onApplicationClose);
        stage.show();

        if (null == controlCenter) {
            ElevatorSystemFactory eccFactory = new ElevatorSystemFactory(this::establishConnection, this::lostConnection);
            controlCenter = eccFactory.getElevatorSystem("RMI", "rmi://localhost/ElevatorSim");
        }
    }

    void initECCView() {
        hideWaitingMessage();

        Building building = controlCenter.getBuilding();
        ECCMode eccMode = new ECCMode();
        controlCenter.setEccMode(eccMode);
        ViewModelFactory viewModelFactory = new ViewModelFactory(building, eccMode);
        viewModelProvider = new ViewModelProvider(viewModelFactory);

        var modeViewModel = viewModelProvider.getModeViewModel();
        var headerView = new HeaderView(modeViewModel);

        var elevatorViews = initElevatorViews(viewModelProvider);
        var elevatorsView = new ElevatorsView(elevatorViews);

        var floorViewModels = viewModelProvider.getFloorViewModelList();
        var floorsView = new FloorsView(floorViewModels);

        var buildingView = new BuildingView(elevatorsView, floorsView);

        VBox.setMargin(headerView, new Insets(8, 16, 8, 16));

        rootLayout.getChildren().add(headerView);
        rootLayout.getChildren().add(buildingView);
    }

    void lostConnection() {
        viewModelProvider.getModeViewModel().setConnection(true);
    }

    void establishConnection() {
        if (null != viewModelProvider) {
            viewModelProvider.getModeViewModel().setConnection(false);
        }

        Platform.runLater(() -> {
            startPolling();
            initECCView();
        });
    }

    private void hideWaitingMessage() {
        rootLayout.getChildren().clear();
    }

    private void startPolling() {
        ThreadManager.getInstance().scheduleRunnable(controlCenter, 1000);
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

    private void onApplicationClose(WindowEvent windowEvent) {
        ThreadManager.getInstance().stopCurrentTasks();
    }
}
