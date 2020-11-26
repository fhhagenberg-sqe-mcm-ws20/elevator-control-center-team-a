package at.fhhgb.team.a.elevators;

import at.fhhgb.team.a.elevators.app.App;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    private Button button;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    public void start(Stage stage) {
        var app = new App();
        app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void testButtonWithText(FxRobot robot) {
    }
}
