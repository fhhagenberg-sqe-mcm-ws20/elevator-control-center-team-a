package at.fhhgb.team.a.elevators.view;

import at.fhhgb.team.a.elevators.viewmodels.ModeViewModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderView extends BorderPane {

    private static final String HEADING = "Elevator Control Center";

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

        this.setTop(heading);
        this.setBottom(modeButton);
    }

    private void onMouseClickEvent(MouseEvent event) {
        System.out.println("Button pressed: " + ((Button) event.getSource()).getText() + " == " + modeViewModel.getButtonText());
        modeViewModel.onButtonPressed();
        System.out.println("Button Text is now: " + modeViewModel.getButtonText());

        ((Button) event.getSource()).setText(modeViewModel.getButtonText());
    }
}
