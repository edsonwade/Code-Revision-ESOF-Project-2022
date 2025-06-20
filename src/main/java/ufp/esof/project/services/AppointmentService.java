package ufp.esof.project.services;

import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;

import java.util.Optional;
import java.util.Set;


public interface AppointmentService {

    Set<Appointment> getSetAppointment();


    Iterable<Appointment> findAll();

    Appointment findAppointmentById(Long id);

    Optional<Explainer> findByName(String nameExplainer);

    Optional<Student> findStudentById(Long id);

//    AppointmentDto save(AppointmentDto Appointment);
//
//    ResponseEntity<AppointmentDto> saveAppointment(AppointmentDto Appointment);

    boolean deleteAppointmentById(Long id);

}