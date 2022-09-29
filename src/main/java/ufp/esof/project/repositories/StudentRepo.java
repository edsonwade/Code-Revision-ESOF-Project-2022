package ufp.esof.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Student;

import java.util.Optional;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {
    Optional<Student> findById(Long id);

    Optional<Student> findByName(String name);
}
