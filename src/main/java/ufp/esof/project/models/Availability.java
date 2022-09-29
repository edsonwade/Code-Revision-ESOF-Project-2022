package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        DayOfWeek dayOfWeek = appointment.getStartTime().getDayOfWeek();
        if (dayOfWeek.equals(this.dayOfWeek)) {
            LocalTime appointmentStart = appointment.getStartTime().toLocalTime();
            LocalTime appointmentEnd = appointment.getExpectedEndTime().toLocalTime();
            return this.contains(appointmentStart, appointmentEnd);
        }
        return false;
    }

    private boolean contains(LocalTime start, LocalTime end) {
        return (this.start.isBefore(start) || this.start.equals(start))
                &&
                (this.end.isAfter(end) || this.end.equals(end));
    }
}
