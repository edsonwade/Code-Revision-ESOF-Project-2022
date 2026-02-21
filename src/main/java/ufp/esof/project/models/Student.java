package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ufp.esof.project.models.base.AuditableEntity;
import ufp.esof.project.models.enums.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "students")
@JsonPropertyOrder({"id", "name", "appointments"})
@Where(clause = "deleted_at IS NULL")
public class Student extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;

    @Column(nullable = false)
    private Long organizationId;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();

    public Student(String name) {
        this.name = name;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setStudent(this);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        appointment.setStudent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(name, student.name) &&
                Objects.equals(appointments, student.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, appointments);
    }
}
