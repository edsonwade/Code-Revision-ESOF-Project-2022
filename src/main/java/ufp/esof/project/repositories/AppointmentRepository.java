package ufp.esof.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

}

