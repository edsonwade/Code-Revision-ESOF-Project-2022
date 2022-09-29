package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_explainer")
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "language"})
public class Explainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;


    @OneToMany(mappedBy = "explainer", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "courses")
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Course> courses = new HashSet<>();


    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "availabilities")
    @ToString.Exclude
    @OneToMany(mappedBy = "explainer", cascade = {CascadeType.PERSIST, CascadeType.ALL})
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
        this.courses.add(course);
    }


    // add language. a um professor
    public void addLanguage(Language languages) {
        language.add(languages);
    }


    // add appointment a um professor
    public boolean addAppointmentToExplainer(Appointment appointment) {
        if (work(appointment.getStartTime()) && disponivel(appointment.getStartTime())) {
            appointments.add(appointment);
            appointment.setExplainer(this);
            appointment.setExplainer(this);

            return true;
        } else return false;
    }

    private boolean work(LocalDateTime dataAppointment) {
        return this.getAvailabilities().stream().
                anyMatch(horarioActual -> horarioActual.getDayOfWeek().equals(dataAppointment.getDayOfWeek()) &&
                        horarioActual.getStart().isBefore(dataAppointment.toLocalTime()) &&
                        horarioActual.getEnd().isAfter(dataAppointment.toLocalTime()));
    }


    private boolean disponivel(LocalDateTime dataAppointment) {
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
}