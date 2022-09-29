package ufp.esof.project.filters.explainerFilters;


import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ExplainerStartTimeFilter implements ExplainerFilter {


    private LocalTime startTime;

    public ExplainerStartTimeFilter(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override

    public Set<Explainer> filter(Set<Explainer> explainers) {
        if (startTime == null) return explainers;
        Set<Explainer> hourStartFilter = new HashSet<>();
        for (Explainer explainer : explainers) {
            for (Appointment appointment : explainer.getAppointments()) {
                if (appointment.getStartTime().getHour() <= startTime.getHour()) {
                    hourStartFilter.add(explainer);
                    break;

                }

            }
        }
        return hourStartFilter;

    }
}

