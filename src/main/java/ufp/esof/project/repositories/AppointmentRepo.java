package ufp.esof.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Appointment;

import java.util.Optional;

@Repository
public interface AppointmentRepo extends CrudRepository<Appointment, Long> {
    Optional<Appointment> findById(long id);
}

