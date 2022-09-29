package ufp.esof.project.dto;

import lombok.Data;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Language;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class ExplainerDto {
    @Max(value = 11)
    private Long Id;
    @NotNull
    @NotEmpty
    @Max(value = 125)
    private String name;
    @NotNull
    private Language language;
    @NotNull
    private Set<Appointment> appointments = new HashSet<>();
    @NotNull
    private Set<Course> courses = new HashSet<>();
    @NotNull
    private Set<Availability> availabilities = new HashSet<>();

}
