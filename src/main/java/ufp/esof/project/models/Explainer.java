package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
public class Explainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;


    @OneToMany(mappedBy = "explainer", cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "courses")
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Course> courses = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "explainer")
    private Set<Language> language = new HashSet<>();


    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "availabilities")
    @ToString.Exclude
    @OneToMany(mappedBy = "explainer", cascade = {CascadeType.PERSIST, CascadeType.ALL})
    private Set<Availability> availabilities = new HashSet<>();

    public Explainer(String name) {
        this.setName(name);
    }

    public Explainer(String name, String languages) {
        {
            this.setName(name);
            this.setLanguage(language);
        }
        this.language = language;
    }

    public Explainer(String name, Set<Course> courses) {
        this.setName(name);
        this.setCourses(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public Language getLanguageExpaliner() {
        for (Language language : this.getLanguage()) {
            return language;
        }
        return null;
    }


    // add language. a um professor
    public void addLanguage(Language languages) {
        language.add(languages);
        languages.setExplainer(this);
    }


    // add appointment a um professor
    public boolean addAppointmentToExplainer(Appointment appointment) {
        if (work(appointment.getStartTime()) && disponivel(appointment.getStartTime())) {
            appointments.add(appointment);


            appointment.setExplainer(this);
            appointment.setExplainer(this);

            return true;
        } else {
            return false;
        }
    }

    private boolean work(LocalDateTime dataAppointment) {
        for (Availability horarioActual : this.getAvailabilities()) {
            if (horarioActual.getDayOfWeek().equals(dataAppointment.getDayOfWeek()) && horarioActual.getStart().isBefore(dataAppointment.toLocalTime()) && horarioActual.getEnd().isAfter(dataAppointment.toLocalTime())) {
                return true;
            }
        }
        return false;
    }


    private boolean disponivel(LocalDateTime dataAppointment) {
        for (Appointment appointmentExplainer : this.appointments) {
            if (appointmentExplainer.getStartTime().getDayOfWeek().equals(dataAppointment.getDayOfWeek())) {
                if (appointmentExplainer.getStartTime().equals(dataAppointment)) {
                    return false;
                }
                if (appointmentExplainer.getExpectedEndTime().toLocalTime().isAfter(dataAppointment.toLocalTime())) {
                    return false;
                }
                if (appointmentExplainer.getStartTime().toLocalTime().isBefore(dataAppointment.toLocalTime().plusMinutes(30))) {
                    return false;
                }
            }
        }
        return true;
    }

}