package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "availabilities")
@JsonPropertyOrder({"id", "dayOfWeek", "start", "end", "explainer"})
@NoArgsConstructor
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime start;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime end;

    @ManyToOne
    @JoinColumn(name = "explainer_id")
    @JsonBackReference
    private Explainer explainer;

    public Availability(DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        validateTimeRange(start, end);
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
    }

    private void validateTimeRange(LocalTime start, LocalTime end) {
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

        LocalTime appointmentStart = appointment.getStartTime().toLocalTime();
        LocalTime appointmentEnd = appointment.getExpectedEndTime().toLocalTime();
        return isWithinTimeRange(appointmentStart, appointmentEnd);
    }

    /**
     * Checks if the given time range falls within this availability's time slot.
     *
     * @param start the start time to check
     * @param end   the end time to check
     * @return true if the time range is within this availability slot, false otherwise
     */
    private boolean isWithinTimeRange(LocalTime start, LocalTime end) {
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
