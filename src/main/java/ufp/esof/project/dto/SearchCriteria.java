/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:26
 * Version:1
 */

package ufp.esof.project.dto;

import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class SearchCriteria {
    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private Duration duration;

    // Getters e setters

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public LocalTime getEndTime() {
        return startTime.plus(duration);
    }
}
