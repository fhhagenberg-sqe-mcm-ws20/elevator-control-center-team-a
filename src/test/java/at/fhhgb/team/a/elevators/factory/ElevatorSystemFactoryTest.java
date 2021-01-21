package at.fhhgb.team.a.elevators.factory;

import at.fhhgb.team.a.elevators.app.ElevatorControlCenter;
import at.fhhgb.team.a.elevators.app.IElevatorSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("For any ElevatorSystemFactory instance")
class ElevatorSystemFactoryTest {

    @Nested
    @DisplayName("assert that getElevatorSystem")
    class testGetElevatorSystem {

        @Test
        @DisplayName("returns a instance of ElevatorControlCenter.")
        void testCorrectIElevatorSystem() {
            ElevatorSystemFactory factory = new ElevatorSystemFactory(null, null);
            IElevatorSystem elevatorSystem = factory.getElevatorSystem("RMI", "URL");

            assertThat(elevatorSystem).isInstanceOf(ElevatorControlCenter.class);
        }

        @Test
        @DisplayName("throws a RuntimeException when requesting an unsupported API.")
        void testUnsupportedElevatorSystem() {
            ElevatorSystemFactory factory = new ElevatorSystemFactory(null, null);

            assertThatThrownBy(() ->
                    factory.getElevatorSystem("REST", "REST_URL")
            )
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Only RMI is currently supported!");
        }
    }
}
