package ufp.esof.project.filters;

import lombok.Data;
import ufp.esof.project.models.Language;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class FilterObject {

    private Language lang;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek day;
}
