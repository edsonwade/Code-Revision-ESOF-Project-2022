package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufp.esof.project.exception.appointmentexception.InvalidAppointmentException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.AppointmentRepository;
import ufp.esof.project.repositories.AvailabilityRepository;
import ufp.esof.project.repositories.ExplainerRepository;
import ufp.esof.project.repositories.StudentRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final ExplainerRepository explainerRepository;

    private final StudentRepository studentRepository;

    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ExplainerRepository explainerRepository, StudentRepository studentRepository, AvailabilityRepository availabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.explainerRepository = explainerRepository;
        this.studentRepository = studentRepository;
        this.availabilityRepository = availabilityRepository;
    }


    @Override
    public Set<Appointment> getSetAppointment() {
        Set<Appointment> appointments = new HashSet<>();
        for (Appointment appointment : this.appointmentRepository.findAll()) {
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public Iterable<Appointment> findAll() {
        return appointmentRepository.findAll();
    }


    @Override
    public Appointment findAppointmentById(Long id) {
        Optional<Appointment> appointment = this.appointmentRepository.findById(id);
        if (appointment.isEmpty()) {
            throw new InvalidAppointmentException(" the id " + id + " was not found");
        }
        return appointment.get();
    }

    @Override
    public Optional<Explainer> findByName(String nameExplainer) {
        return explainerRepository.findByName(nameExplainer);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // todo : fixed this error
//    @Override
//    public Appointment save(Appointment Appointment) {
//        return this.appointmentRepo.save(Appointment);
//    }
//
//    @Override
//    public ResponseEntity<Appointment> saveAppointment(Appointment appointment) {
//        appointmentRepo.save(appointment);
//        return ResponseEntity.notFound().build();
//
//    }

    @Override
    public boolean deleteAppointmentById(Long id) {
        Appointment appointment = this.findAppointmentById(id);
        if (appointment.getId().equals(id)) {
            this.appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}