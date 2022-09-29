package ufp.esof.project.filters.explainerFilters;


import ufp.esof.project.models.Explainer;

import java.time.DayOfWeek;

import java.util.Set;
import java.util.stream.Collectors;

public class ExplainerDayFilter implements ExplainerFilter {

    private DayOfWeek day;

    public ExplainerDayFilter(DayOfWeek day) {
        this.day = day;
    }


    @Override
    public Set<Explainer> filter(Set<Explainer> explainers) {
        if (day == null) return explainers;
        return explainers.stream()
                .filter(explainer -> explainer.getAppointments()
                        .stream()
                        .anyMatch(appointment -> appointment.getStartTime().getDayOfWeek().equals(day)))
                .collect(Collectors.toSet());
    }

}
