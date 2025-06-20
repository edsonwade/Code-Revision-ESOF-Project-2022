package ufp.esof.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Explainer;

import java.util.Optional;

@Repository
public interface ExplainerRepository extends JpaRepository<Explainer, Long> {
    Optional<Explainer> findByName(String name);
}
