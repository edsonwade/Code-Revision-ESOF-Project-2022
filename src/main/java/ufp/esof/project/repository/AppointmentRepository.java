package ufp.esof.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Appointment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Appointment entities.
 * Provides database access and custom query methods for appointment management.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Find conflicting appointments for an explainer within a time range.
     * Excludes a specific appointment ID from the search (useful for updates).
     *
     * @param explainerId the explainer ID
     * @param startTime the start of the time range
     * @param endTime the end of the time range
     * @param excludeAppointmentId the appointment ID to exclude from search (null to include all)
     * @return list of conflicting appointments
     */
    @Query("SELECT a FROM Appointment a WHERE a.explainer.id = :explainerId " +
            "AND a.deletedAt IS NULL " +
            "AND a.status NOT IN ('CANCELLED', 'NO_SHOW') " +
            "AND (a.startTime < :endTime AND a.expectedEndTime > :startTime) " +
            "AND (:excludeId IS NULL OR a.id != :excludeId)")
    List<Appointment> findConflictingAppointments(
            @Param("explainerId") Long explainerId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludeId") Long excludeAppointmentId);

    /**
     * Find all appointments for a specific student.
     *
     * @param studentId the student ID
     * @return list of student's appointments
     */
    @Query("SELECT a FROM Appointment a WHERE a.student.id = :studentId AND a.deletedAt IS NULL")
    List<Appointment> findByStudentId(@Param("studentId") Long studentId);

    /**
     * Find all appointments for a specific explainer.
     *
     * @param explainerId the explainer ID
     * @return list of explainer's appointments
     */
    @Query("SELECT a FROM Appointment a WHERE a.explainer.id = :explainerId AND a.deletedAt IS NULL")
    List<Appointment> findByExplainerId(@Param("explainerId") Long explainerId);

    /**
     * Find appointments within a specific date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of appointments in the date range
     */
    @Query("SELECT a FROM Appointment a WHERE a.startTime >= :startDate AND a.startTime <= :endDate AND a.deletedAt IS NULL")
    List<Appointment> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}



