package ufp.esof.project.filters.explainerFilters;

import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ExplainerEndTimeFilter implements ExplainerFilter {


    private LocalTime endTime;

    public ExplainerEndTimeFilter(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override

    public Set<Explainer> filter(Set<Explainer> explainers) {
        if (endTime == null) return explainers;
        Set<Explainer> hourEndFilter = new HashSet<>();
        for (Explainer explainer : explainers) {
            for (Appointment appointment : explainer.getAppointments()) {
                if (appointment.getExpectedEndTime().getHour() <= endTime.getHour()) {
                    hourEndFilter.add(explainer);
                    break;

                }

            }
        }
        return hourEndFilter;

    }
}
