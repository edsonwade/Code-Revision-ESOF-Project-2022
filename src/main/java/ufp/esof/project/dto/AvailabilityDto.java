package ufp.esof.project.dto;

import lombok.Data;
import ufp.esof.project.models.Explainer;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class AvailabilityDto {
    private Long Id;
    private DayOfWeek dayOfWeek;
    private LocalTime start;
    private LocalTime end;
    private Explainer explainer;


}
