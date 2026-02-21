package ufp.esof.project.services.appointment;

import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.enums.AppointmentStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for appointment management operations.
 * Provides comprehensive appointment lifecycle management including creation,
 * rescheduling, cancellation, and status tracking.
 */
public interface AppointmentManagementService {

    /**
     * Create a new appointment.
     * Validates availability conflicts and business logic before creation.
     *
     * @param request the create appointment request
     * @return the created appointment response
     * @throws IllegalArgumentException if validation fails
     */
    AppointmentResponse createAppointment(CreateAppointmentRequest request);

    /**
     * Get appointment by ID.
     *
     * @param id the appointment ID
     * @return Optional containing the appointment if found
     */
    Optional<Appointment> getAppointmentById(Long id);

    /**
     * Get all appointments for a student.
     *
     * @param studentId the student ID
     * @return list of student's appointments
     */
    List<AppointmentResponse> getStudentAppointments(Long studentId);

    /**
     * Get all appointments for an explainer.
     *
     * @param explainerId the explainer ID
     * @return list of explainer's appointments
     */
    List<AppointmentResponse> getExplainerAppointments(Long explainerId);

    /**
     * Reschedule an existing appointment to a new time.
     * Only SCHEDULED and RESCHEDULED appointments can be rescheduled.
     *
     * @param appointmentId the appointment ID to reschedule
     * @param newStartTime the new start time
     * @param newEndTime the new end time
     * @return the rescheduled appointment response
     * @throws IllegalStateException if appointment cannot be rescheduled
     */
    AppointmentResponse rescheduleAppointment(Long appointmentId, java.time.LocalDateTime newStartTime, java.time.LocalDateTime newEndTime);

    /**
     * Cancel an appointment.
     * Only SCHEDULED and RESCHEDULED appointments can be cancelled.
     *
     * @param appointmentId the appointment ID to cancel
     * @return true if cancelled successfully, false otherwise
     * @throws IllegalStateException if appointment cannot be cancelled
     */
    boolean cancelAppointment(Long appointmentId);

    /**
     * Mark appointment as completed.
     *
     * @param appointmentId the appointment ID
     * @return true if marked successfully, false otherwise
     */
    boolean completeAppointment(Long appointmentId);

    /**
     * Mark appointment as no-show.
     *
     * @param appointmentId the appointment ID
     * @return true if marked successfully, false otherwise
     */
    boolean markAsNoShow(Long appointmentId);

    /**
     * Update appointment status.
     *
     * @param appointmentId the appointment ID
     * @param newStatus the new status
     * @return the updated appointment response
     */
    AppointmentResponse updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus);

    /**
     * Delete an appointment (soft delete).
     *
     * @param appointmentId the appointment ID
     * @return true if deleted successfully
     */
    boolean deleteAppointment(Long appointmentId);

    /**
     * Get all appointments (active, non-deleted).
     *
     * @return list of all active appointments
     */
    List<AppointmentResponse> getAllAppointments();

    /**
     * Check if there are time conflicts for a new appointment.
     *
     * @param explainerId the explainer ID
     * @param startTime the start time
     * @param endTime the end time
     * @return true if conflicts exist, false otherwise
     */
    boolean hasTimeConflict(Long explainerId, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime);
}

