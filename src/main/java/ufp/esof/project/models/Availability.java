package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import ufp.esof.project.models.base.AuditableEntity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "availabilities")
@JsonPropertyOrder({"id", "dayOfWeek", "start", "end", "explainer"})
@NoArgsConstructor
@SuppressWarnings("all")
public class Availability extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime start;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "explainer_id")
    @JsonBackReference
    private Explainer explainer;

    public Availability(DayOfWeek dayOfWeek, LocalDateTime start, LocalDateTime end) {
        validateTimeRange(start, end);
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
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
     * Checks if this availability time slot can accommodate the given appointment.
     *
     * @param appointment the appointment to check
     * @return true if the appointment fits within this availability slot, false otherwise
     * @throws IllegalArgumentException if appointment is null
     */
    public boolean containsAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }

        DayOfWeek appointmentDay = appointment.getStartTime().getDayOfWeek();
        if (!appointmentDay.equals(this.dayOfWeek)) {
            return false;
        }

        LocalDateTime appointmentStart = appointment.getStartTime();
        LocalDateTime appointmentEnd = appointment.getExpectedEndTime();
        return isWithinTimeRange(appointmentStart, appointmentEnd);
    }

    /**
     * Checks if the given time range falls within this availability's time slot.
     *
     * @param start the start time to check
     * @param end   the end time to check
     * @return true if the time range is within this availability slot, false otherwise
     */
    private boolean isWithinTimeRange(LocalDateTime start, LocalDateTime end) {
        return (this.start.equals(start) || this.start.isBefore(start)) &&
                (this.end.equals(end) || this.end.isAfter(end));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Availability that = (Availability) o;
        return Objects.equals(id, that.id) && dayOfWeek == that.dayOfWeek && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(explainer, that.explainer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(dayOfWeek);
        result = 31 * result + Objects.hashCode(start);
        result = 31 * result + Objects.hashCode(end);
        result = 31 * result + Objects.hashCode(explainer);
        return result;
    }
}
