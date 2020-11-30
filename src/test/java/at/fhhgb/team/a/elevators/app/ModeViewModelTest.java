package at.fhhgb.team.a.elevators.app;

import at.fhhgb.team.a.elevators.model.ECCMode;
import at.fhhgb.team.a.elevators.viewmodels.ModeViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModeViewModelTest {
    ModeViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new ModeViewModel(new ECCMode());
    }

    @Test
    void testChangeToManualFromInitialAutoMode() {
        assertThat(viewModel.isManualModeEnabled()).isFalse();
        assertThat(viewModel.getButtonText()).isEqualTo("Auto");

        viewModel.onButtonPressed();
        assertThat(viewModel.isManualModeEnabled()).isTrue();
        assertThat(viewModel.getButtonText()).isEqualTo("Manual");
    }
}
