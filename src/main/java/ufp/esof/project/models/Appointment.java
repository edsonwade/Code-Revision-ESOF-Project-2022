package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "appointments")
@JsonPropertyOrder({"id", "student", "explainer", "startTime", "expectedEndTime"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "explainer_id")
    @JsonBackReference(value = "explainer")
    private Explainer explainer;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expectedEndTime;

    public Appointment(LocalDateTime startTime, LocalDateTime expectedEndTime) {
        validateTimeRange(startTime, expectedEndTime);
        this.startTime = startTime;
        this.expectedEndTime = expectedEndTime;
    }

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end times cannot be null");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
    }

    /**
     * Checks if this appointment has a time conflict with another appointment.
     * A conflict occurs when appointments overlap or have identical time slots.
     *
     * @param other the appointment to check for conflicts with
     * @return true if there is a time conflict, false otherwise
     * @throws IllegalArgumentException if other is null
     */
    public boolean hasTimeConflict(Appointment other) {
        return isTimeOverlapping(other) ||
                areTimesEqual(other) ||
                other.isTimeOverlapping(this);
    }

    /**
     * Checks if this appointment's time period overlaps with another appointment.
     *
     * @param other the appointment to check for overlap
     * @return true if the appointments overlap, false otherwise
     */
    private boolean isTimeOverlapping(Appointment other) {
        return isTimeWithinRange(other.startTime) ||
                isTimeWithinRange(other.expectedEndTime);
    }

    /**
     * Checks if a given time falls within this appointment's time range.
     *
     * @param timeToCheck the time to check
     * @return true if the time is within the appointment's range, false otherwise
     */
    private boolean isTimeWithinRange(LocalDateTime timeToCheck) {
        return startTime.isBefore(timeToCheck) &&
                expectedEndTime.isAfter(timeToCheck);
    }

    /**
     * Checks if this appointment has exactly the same start and end times as another appointment.
     *
     * @param other the appointment to compare times with
     * @return true if start and end times are equal, false otherwise
     */
    private boolean areTimesEqual(Appointment other) {
        return startTime.equals(other.startTime) &&
                expectedEndTime.equals(other.expectedEndTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(explainer, that.explainer) && Objects.equals(startTime, that.startTime) && Objects.equals(expectedEndTime, that.expectedEndTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(student);
        result = 31 * result + Objects.hashCode(explainer);
        result = 31 * result + Objects.hashCode(startTime);
        result = 31 * result + Objects.hashCode(expectedEndTime);
        return result;
    }
}