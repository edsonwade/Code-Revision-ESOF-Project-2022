package ufp.esof.project.filters.explainerFilters;


import ufp.esof.project.filters.FilterI;
import ufp.esof.project.models.Explainer;

import java.util.Set;

public interface ExplainerFilter extends FilterI<Explainer> {

    Set<Explainer> filter(Set<Explainer> entities);
}
