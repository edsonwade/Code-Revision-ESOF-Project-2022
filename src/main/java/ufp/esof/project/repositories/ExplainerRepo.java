package ufp.esof.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Explainer;

import java.util.Optional;

@Repository
public interface ExplainerRepo extends CrudRepository<Explainer, Long> {
    Optional<Explainer> findByName(String name);
}
