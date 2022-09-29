package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "appointments"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    private Set<Appointment> appointments = new HashSet<>();

    public Student(String name) {
        this.setName(name);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setStudent(this);
    }
}
