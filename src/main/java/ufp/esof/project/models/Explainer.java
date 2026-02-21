package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Where;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ufp.esof.project.models.base.AuditableEntity;
import ufp.esof.project.models.enums.Role;

@Entity
@Table(name = "explainers")
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "language"})
@Getter
@Setter
@Where(clause = "deleted_at IS NULL")
public class Explainer extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.EXPLAINER;

    @Column(nullable = false)
    private Long organizationId;
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;


    @OneToMany(mappedBy = "explainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "explainer_courses",
            joinColumns = @JoinColumn(name = "explainer_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();



    @OneToMany(mappedBy = "explainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Availability> availabilities = new HashSet<>();


    public Explainer(String name) {
        this.setName(name);
    }

    public Explainer(String name, Language language) {
        this.name = name;
        this.language = language;
    }

    public Explainer(String name, Set<Course> courses) {
        this.setName(name);
        this.setCourses(courses);
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.getExplainers().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getExplainers().remove(this);
    }


    // add appointment a um professor
    public boolean addAppointment(Appointment appointment) {
        if (isAvailable(appointment.getStartTime())) {
            appointments.add(appointment);
            appointment.setExplainer(this);
            return true;
        }
        return false;
    }

    private boolean isAvailable(LocalDateTime appointmentTime) {
        return hasWorkingHours(appointmentTime) && hasNoConflictingAppointments(appointmentTime);
    }

    private boolean hasWorkingHours(LocalDateTime dataAppointment) {
        return this.getAvailabilities().stream().
                anyMatch(horarioActual -> horarioActual.getDayOfWeek().equals(dataAppointment.getDayOfWeek()) &&
                        horarioActual.getStart().isBefore(dataAppointment.toLocalTime()) &&
                        horarioActual.getEnd().isAfter(dataAppointment.toLocalTime()));
    }


    private boolean hasNoConflictingAppointments(LocalDateTime dataAppointment) {
        for (Appointment appointmentExplainer : this.appointments) {
            if (appointmentExplainer.getStartTime().getDayOfWeek().equals(dataAppointment.getDayOfWeek())) {
                if (appointmentExplainer.getStartTime().equals(dataAppointment)) {
                    isAfterDateAppointment(appointmentExplainer, dataAppointment);
                }
                return false;
            }
            isBeforeDateAppointment(appointmentExplainer, dataAppointment);
        }
        return true;
    }


    private boolean isBeforeDateAppointment(Appointment appointmentExplainer, LocalDateTime dataAppointment) {
        return !appointmentExplainer.getStartTime().toLocalTime()
                .isBefore(dataAppointment.toLocalTime().plusMinutes(30));
    }

    private boolean isAfterDateAppointment(Appointment appointmentExplainer, LocalDateTime dataAppointment) {
        return !appointmentExplainer.getExpectedEndTime().toLocalTime().isAfter(dataAppointment.toLocalTime());

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Explainer explainer = (Explainer) o;
        return Objects.equals(Id, explainer.Id) && Objects.equals(name, explainer.name) && language == explainer.language && Objects.equals(appointments, explainer.appointments) && Objects.equals(courses, explainer.courses) && Objects.equals(availabilities, explainer.availabilities);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(Id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(language);
        result = 31 * result + Objects.hashCode(appointments);
        result = 31 * result + Objects.hashCode(courses);
        result = 31 * result + Objects.hashCode(availabilities);
        return result;
    }
}