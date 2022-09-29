package ufp.esof.project.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;

import java.util.Optional;
import java.util.Set;

@Service
@Repository

public interface AppointmentService {

    Set<Appointment> getSetAppointment();


    Iterable<Appointment> findAll();

    Optional<Appointment> findById(Long id);

    Optional<Explainer> findByName(String nameExplainer);

    Optional<Student> findStudentById(Long id);

    Appointment save(Appointment Appointment);

    ResponseEntity<Appointment> saveAppointment(Appointment Appointment);

    boolean deleteById(Long id);

}