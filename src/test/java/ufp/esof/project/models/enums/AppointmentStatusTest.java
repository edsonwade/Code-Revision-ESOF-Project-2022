package ufp.esof.project.models.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AppointmentStatus Enum Tests")
class AppointmentStatusTest {

    @Test
    @DisplayName("Should have 5 status values defined")
    void testEnumValues() {
        assertThat(AppointmentStatus.values()).hasSize(5);
    }

    @Test
    @DisplayName("SCHEDULED appointment can be rescheduled")
    void testScheduledCanBeRescheduled() {
        assertThat(AppointmentStatus.SCHEDULED.canBeRescheduled()).isTrue();
    }

    @Test
    @DisplayName("SCHEDULED appointment can be cancelled")
    void testScheduledCanBeCancelled() {
        assertThat(AppointmentStatus.SCHEDULED.canBeCancelled()).isTrue();
    }

    @Test
    @DisplayName("RESCHEDULED appointment can be rescheduled")
    void testRescheduledCanBeRescheduled() {
        assertThat(AppointmentStatus.RESCHEDULED.canBeRescheduled()).isTrue();
    }

    @Test
    @DisplayName("RESCHEDULED appointment can be cancelled")
    void testRescheduledCanBeCancelled() {
        assertThat(AppointmentStatus.RESCHEDULED.canBeCancelled()).isTrue();
    }

    @Test
    @DisplayName("COMPLETED appointment cannot be rescheduled")
    void testCompletedCannotBeRescheduled() {
        assertThat(AppointmentStatus.COMPLETED.canBeRescheduled()).isFalse();
    }

    @Test
    @DisplayName("COMPLETED appointment is a final state")
    void testCompletedIsFinalState() {
        assertThat(AppointmentStatus.COMPLETED.isFinalState()).isTrue();
    }

    @Test
    @DisplayName("CANCELLED appointment cannot be rescheduled")
    void testCancelledCannotBeRescheduled() {
        assertThat(AppointmentStatus.CANCELLED.canBeRescheduled()).isFalse();
    }

    @Test
    @DisplayName("CANCELLED appointment is a final state")
    void testCancelledIsFinalState() {
        assertThat(AppointmentStatus.CANCELLED.isFinalState()).isTrue();
    }

    @Test
    @DisplayName("NO_SHOW appointment is a final state")
    void testNoShowIsFinalState() {
        assertThat(AppointmentStatus.NO_SHOW.isFinalState()).isTrue();
    }

    @Test
    @DisplayName("SCHEDULED is not a final state")
    void testScheduledIsNotFinalState() {
        assertThat(AppointmentStatus.SCHEDULED.isFinalState()).isFalse();
    }

    @Test
    @DisplayName("All status enums have descriptions")
    void testDescriptionsExist() {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            assertThat(status.getDescription()).isNotBlank();
        }
    }
}

