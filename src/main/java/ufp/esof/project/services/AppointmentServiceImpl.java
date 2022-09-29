package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.*;

import java.util.Collections;
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
    public Optional<Appointment> findById(Long id) {
        return this.appointmentRepo.findById(id);
    }

    @Override
    public Optional<Explainer> findByName(String nameExplainer) {
        return explainerRepo.findByName(nameExplainer);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepo.findById(id);
    }

    @Override
    public Appointment save(Appointment Appointment) {
        return this.appointmentRepo.save(Appointment);
    }

    @Override
    public ResponseEntity<Appointment> saveAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
        return ResponseEntity.notFound().build();

    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Appointment> appointmentOptional = this.findById(id);
        if (appointmentOptional.isPresent()) {
            this.appointmentRepo.deleteById(id);
            return true;
        }
        return false;
    }

}