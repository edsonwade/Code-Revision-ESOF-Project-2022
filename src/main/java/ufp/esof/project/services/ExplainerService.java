package ufp.esof.project.services;

import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ufp.esof.project.dto.ExplainerDto;
import ufp.esof.project.models.Explainer;

@Service
@Repository
public interface ExplainerService {

    Optional<Explainer> getById(long id);

    Set<Explainer> getFilteredExplainer(Object filterObject);

    Optional<Explainer> findExplainerByName(String name);

    Set<Explainer> findAllExplainers();

    Optional<Explainer> saveExplainer(ExplainerDto explainer);

    Optional<Explainer> editExplainer(Explainer currentExplainer, ExplainerDto explainer, Long id);

    boolean deleteById(Long id);
}
