package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "student", "explainer", "startTime", "expectedEndTime"})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "student")
    private Student student;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "explainer")
    private Explainer explainer;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expectedEndTime;

    public Appointment(LocalDateTime startTime, LocalDateTime end) {
        this.startTime = startTime;
        this.expectedEndTime = end;
    }


    public Appointment(Long id) {
        this.setId(id);
    }

    public Appointment(Explainer explainer) {
        this.setExplainer(explainer);
    }


    public boolean overlaps(Appointment other) {
        return this.isBetween(other) || other.isBetween(this) ||
                (this.startTime.equals(other.startTime) && this.expectedEndTime.equals(other.expectedEndTime));
    }

    private boolean isBetween(Appointment other) {
        var appointmentStartTime = other.getStartTime();
        var appointmentEndTime = other.getExpectedEndTime();
        return this.isBetweenDate(appointmentStartTime) || this.isBetweenDate(appointmentEndTime);
    }

    private boolean isBetweenDate(LocalDateTime timeToCheck) {
        return this.startTime.isBefore(timeToCheck) && this.expectedEndTime.isAfter(timeToCheck);
    }
}
