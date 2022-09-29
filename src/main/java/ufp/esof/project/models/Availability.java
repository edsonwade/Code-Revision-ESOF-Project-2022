package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "dayOfWeek", "start", "end", "explainer"})
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    private DayOfWeek dayOfWeek;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime start;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime end;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Explainer explainer;

    public Availability(DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
    }

    public boolean contains(Appointment appointment) {
        var dayOfWeeks = appointment.getStartTime().getDayOfWeek();
        if (!Objects.equals(dayOfWeeks, this.dayOfWeek)) return false;
        var appointmentStart = appointment.getStartTime().toLocalTime();
        var appointmentEnd = appointment.getExpectedEndTime().toLocalTime();
        return this.contains(appointmentStart, appointmentEnd);
    }

    private boolean contains(LocalTime start, LocalTime end) {
        return (this.start.isBefore(start) || start.equals(this.start))
                && (this.end.isAfter(end) || end.equals(this.end));
    }
}
