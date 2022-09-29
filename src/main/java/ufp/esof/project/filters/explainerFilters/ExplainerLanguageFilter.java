package ufp.esof.project.filters.explainerFilters;

import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Language;

import java.util.Set;
import java.util.stream.Collectors;

public class ExplainerLanguageFilter implements ExplainerFilter {

    private Language languages;

    public ExplainerLanguageFilter(Language languages) {
        this.languages = languages;
    }


    @Override
    public Set<Explainer> filter(Set<Explainer> explainers) {
        if (languages == null) return explainers;


        return explainers.stream().filter(explainer -> explainer.getLanguage() == this.languages).collect(Collectors.toSet());


    }
}


//explainer -> explainer.getLanguages() == this.languages).collect(Collectors.toSet());