package ufp.esof.project.models.enums;

/**
 * Enumeration representing the possible states of an appointment.
 *
 * Possible states:
 * - SCHEDULED: Appointment is booked and pending
 * - COMPLETED: Appointment has been completed
 * - CANCELLED: Appointment has been cancelled
 * - RESCHEDULED: Appointment has been rescheduled to a different time
 * - NO_SHOW: Student did not attend the scheduled appointment
 */
public enum AppointmentStatus {
    SCHEDULED("Scheduled"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    RESCHEDULED("Rescheduled"),
    NO_SHOW("No Show");

    private final String description;

    AppointmentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if this status allows the appointment to be rescheduled.
     * Only SCHEDULED appointments can be rescheduled.
     *
     * @return true if appointment can be rescheduled, false otherwise
     */
    public boolean canBeRescheduled() {
        return this == SCHEDULED;
    }

    /**
     * Check if this status allows the appointment to be cancelled.
     * Only SCHEDULED appointments can be cancelled.
     *
     * @return true if appointment can be cancelled, false otherwise
     */
    public boolean canBeCancelled() {
        return this == SCHEDULED || this == RESCHEDULED;
    }

    /**
     * Check if this status represents a final state.
     * Final states cannot be changed.
     *
     * @return true if this is a final state, false otherwise
     */
    public boolean isFinalState() {
        return this == COMPLETED || this == CANCELLED || this == NO_SHOW;
    }
}

