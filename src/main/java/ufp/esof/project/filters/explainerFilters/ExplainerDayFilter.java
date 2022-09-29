package ufp.esof.project.filters.explainerFilters;

import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class ExplainerDayFilter implements ExplainerFilter {

    private DayOfWeek day;

    public ExplainerDayFilter(DayOfWeek day) {
        this.day = day;
    }


    @Override
    public Set<Explainer> filter(Set<Explainer> explainers) {
        if (day == null) return explainers;
        Set<Explainer> dayFilter = new HashSet<>();
        for (Explainer explainer : explainers) {
            for (Appointment appointment : explainer.getAppointments()) {
                if (appointment.getStartTime().getDayOfWeek().equals(day)) {
                    dayFilter.add(explainer);
                    break;

                }

            }

        }
        return dayFilter;
    }

}
