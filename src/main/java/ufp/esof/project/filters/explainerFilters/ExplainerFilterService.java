package ufp.esof.project.filters.explainerFilters;

import org.springframework.stereotype.Service;
import ufp.esof.project.filters.AndFilter;
import ufp.esof.project.filters.FilterI;
import ufp.esof.project.filters.FilterObject;
import ufp.esof.project.models.Explainer;

import java.util.Set;

@Service
public class ExplainerFilterService {
    public Set<Explainer> filterExplainer(Set<Explainer> explainers, FilterObject filterObject) {

        FilterI<Explainer> hourStartFilter = new ExplainerStartTimeFilter(filterObject.getStartTime());
        FilterI<Explainer> hourEndFilter = new ExplainerEndTimeFilter(filterObject.getEndTime());
        FilterI<Explainer> hourEndAndHourStartFilter = new AndFilter<>(hourStartFilter, hourEndFilter);

        FilterI<Explainer> languageExplainerFilter = new ExplainerLanguageFilter(filterObject.getLang());
        FilterI<Explainer> languageAndhourStartAndhourEndAndDay = new AndFilter<>(hourEndAndHourStartFilter, languageExplainerFilter);
        return languageAndhourStartAndhourEndAndDay.filter(explainers);
    }
}

