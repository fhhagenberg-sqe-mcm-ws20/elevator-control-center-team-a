package at.fhhgb.team.a.elevators.app;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

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

    @BeforeAll
    public static void config() throws Exception {
        System.getProperties().put("testfx.robot", "glass");
    }

    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    public void testManualModeButton(FxRobot robot) {
        verifyThat(robot.lookup("#modeButton").queryAs(Button.class), isVisible());
        verifyThat(robot.lookup("#modeButton").queryAs(Button.class), LabeledMatchers.hasText("Auto"));

        Button btn = robot.lookup("#modeButton").queryAs(Button.class);
        btn.fire();
//        robot.clickOn(btn);
//        WaitForAsyncUtils.waitForFxEvents();
//        verifyThat(btn, LabeledMatchers.hasText("Auto"));  //this should fail: should be "Manual"
//        robot.clickOn(btn);
//        WaitForAsyncUtils.waitForFxEvents();

        verifyThat(btn, isVisible());
        verifyThat(btn, LabeledMatchers.hasText("Manual"));  //this should fail: should be "Auto"
    }
}
