package ufp.esof.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.College;

import java.util.Optional;

@Repository
@SuppressWarnings("all")
public interface CollegeRepository extends CrudRepository<College, Long> {
    Optional<College> findByName(String name);

    Optional<College> findById(Long id);
}
