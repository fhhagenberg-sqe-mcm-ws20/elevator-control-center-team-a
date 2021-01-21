package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.ModeViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderView extends BorderPane {

    private static final String HEADING = "Elevator Control Center";
    private static final String WARNING = "    Houston, we have a problem!";

    private final ModeViewModel modeViewModel;

    public HeaderView(ModeViewModel modeViewModel) {
        super();
        this.modeViewModel = modeViewModel;
        initializeView();
    }

    private void initializeView() {
        Button modeButton = new Button();
        modeButton.setId("modeButton");
        modeButton.setText(modeViewModel.getButtonText());
        modeButton.setOnMouseClicked(this::onMouseClickEvent);

        Label heading = new Label(HEADING);
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        Label warningMessage = new Label(WARNING);
        ImageView warningSign = new ImageView("images/warning.png");
        warningSign.setFitHeight(16);
        warningSign.setFitWidth(16);

        HBox warningLayout = new HBox();
        warningLayout.visibleProperty().bind(modeViewModel.isConnected());
        warningLayout.setId("warning_message");
        warningLayout.getChildren().add(warningSign);
        warningLayout.getChildren().add(warningMessage);

        VBox titleLayout = new VBox();
        titleLayout.getChildren().add(warningLayout);
        titleLayout.getChildren().add(heading);

        this.setTop(titleLayout);
        this.setBottom(modeButton);
    }

    private void onMouseClickEvent(MouseEvent event) {
        modeViewModel.onButtonPressed();
        ((Button) event.getSource()).setText(modeViewModel.getButtonText());
    }
}
