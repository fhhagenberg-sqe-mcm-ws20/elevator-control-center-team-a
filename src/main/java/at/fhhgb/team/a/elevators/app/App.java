package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.factory.ElevatorSystemFactory;
import at.fhhgb.team.a.elevators.factory.ViewModelFactory;
import at.fhhgb.team.a.elevators.model.Building;
import at.fhhgb.team.a.elevators.provider.ViewModelProvider;
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
import sqelevator.IElevator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class App extends Application {

    private IElevatorSystem controlCenter;
    private final ScheduledExecutorService executorService;
    private VBox rootLayout;

    public App() {
        executorService = Executors.newScheduledThreadPool(1);
    }

    public App(IElevatorSystem controlCenter) {
        this();
        this.controlCenter = controlCenter;
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
            ElevatorSystemFactory eccFactory = new ElevatorSystemFactory();
            controlCenter = eccFactory.getElevatorSystem("RMI", "rmi://localhost/ElevatorSim", this::establishConnection);
        }
    }

    private void establishConnection(IElevator ignore) {
        Platform.runLater(() -> {
            hideWaitingMessage();
            startPolling();
            initECCView();
        });
    }

    void hideWaitingMessage() {
        rootLayout.getChildren().clear();
    }

    void initECCView() {
        hideWaitingMessage(); //TODO mb delete this call or other call

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

        VBox.setMargin(headerView, new Insets(8, 16, 8, 16));

        rootLayout.getChildren().add(headerView);
        rootLayout.getChildren().add(buildingView);
    }

    private void startPolling() {
        executorService.scheduleAtFixedRate(controlCenter, 0, 1, TimeUnit.SECONDS);
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
        if (null != executorService) {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
