package ufp.esof.project.dto;

import lombok.Data;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private Student student;
    private Explainer explainer;

    private LocalDateTime startTime;

    private LocalDateTime expectedEndTime;
}
