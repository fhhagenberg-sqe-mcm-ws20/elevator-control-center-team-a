package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.Elevator;
import at.fhhgb.team.a.elevators.model.Floor;
import at.fhhgb.team.a.elevators.view.Colors;
import at.fhhgb.team.a.elevators.viewmodels.ElevatorFloorViewModel;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("For any ElevatorFloorViewModel instance")
public class ElevatorFloorViewModelTest {
    Elevator elevator;
    Floor floor;
    ElevatorFloorViewModel viewModel;

    @BeforeEach
    void setUp() {
        elevator = Mockito.mock(Elevator.class);
        floor = Mockito.mock(Floor.class);
        Mockito.when(floor.getNumber()).thenReturn(1);
        viewModel = new ElevatorFloorViewModel(elevator, floor);
    }

    @Test
    @DisplayName("test getting target floor color after changing target floor")
    void testTargetFloorColoring() {
        Mockito.when(elevator.getTarget()).thenReturn(null);
        Mockito.when(elevator.servicesFloor(1)).thenReturn(true);

        var background = viewModel.getButtonBackground();
        assertThat(background.getValue().getFills().stream().map(b -> (Color)b.getFill()).anyMatch(f -> f == Colors.serviceWhite));

        Mockito.when(elevator.getTarget()).thenReturn(floor);

        var changedBackground = viewModel.getButtonBackground();
        assertThat(changedBackground.getValue().getFills().stream().map(b -> (Color)b.getFill()).anyMatch(f -> f == Colors.targetGreen));
    }

    @Test
    @DisplayName("test getting current floor color after changing to current serviced floor")
    void testCurrentFloorColoring() {
        Mockito.when(elevator.getTarget()).thenReturn(null);
        Mockito.when(elevator.servicesFloor(1)).thenReturn(false);

        var background = viewModel.getButtonBackground();
        assertThat(background.getValue().getFills().stream().map(b -> (Color)b.getFill()).anyMatch(f -> f == Colors.noServiceGray));

        Mockito.when(elevator.servicesFloor(1)).thenReturn(true);
        Mockito.when(elevator.getTarget()).thenReturn(floor);

        var changedBackground = viewModel.getButtonBackground();
        assertThat(changedBackground.getValue().getFills().stream().map(b -> (Color)b.getFill()).anyMatch(f -> f == Colors.currentYellow));
    }
}
