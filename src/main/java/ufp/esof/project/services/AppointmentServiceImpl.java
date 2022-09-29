package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufp.esof.project.exception.appointmentexception.InvalidAppointmentException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.AppointmentRepo;
import ufp.esof.project.repositories.AvailabilityRepo;
import ufp.esof.project.repositories.ExplainerRepo;
import ufp.esof.project.repositories.StudentRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepo appointmentRepo;

    private ExplainerRepo explainerRepo;

    private StudentRepo studentRepo;

    private AvailabilityRepo availabilityRepo;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, ExplainerRepo explainerRepo, StudentRepo studentRepo, AvailabilityRepo availabilityRepo) {
        this.appointmentRepo = appointmentRepo;
        this.explainerRepo = explainerRepo;
        this.studentRepo = studentRepo;
        this.availabilityRepo = availabilityRepo;
    }


    @Override
    public Set<Appointment> getSetAppointment() {
        Set<Appointment> appointments = new HashSet<>();
        for (Appointment appointment : this.appointmentRepo.findAll()) {
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public Iterable<Appointment> findAll() {
        return appointmentRepo.findAll();
    }


    @Override
    public Appointment findAppointmentById(Long id) {
        Optional<Appointment> appointment = this.appointmentRepo.findById(id);
        if (appointment.isEmpty()) {
            throw new InvalidAppointmentException(" the id " + id + " was not found");
        }
        return appointment.get();
    }

    @Override
    public Optional<Explainer> findByName(String nameExplainer) {
        return explainerRepo.findByName(nameExplainer);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepo.findById(id);
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
    public boolean deleteById(Long id) {
        Appointment appointmentOptional = this.findAppointmentById(id);
        if (appointmentOptional.getId().equals(id)) {
            this.appointmentRepo.deleteById(id);
            return true;
        }
        return false;
    }

}