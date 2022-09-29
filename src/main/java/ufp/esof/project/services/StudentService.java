package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.AppointmentRepo;
import ufp.esof.project.repositories.StudentRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private StudentRepo studentRepo;

    private AppointmentRepo appointmentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, AppointmentRepo appointmentRepo) {
        this.studentRepo = studentRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public Iterable<Student> findAll() {
        return this.studentRepo.findAll();
    }

    public Optional<Student> findById(Long id) {
        return this.studentRepo.findById(id);
    }

    public boolean deleteById(Long id) {
        Optional<Student> optionalStudent = this.findById(id);
        if (optionalStudent.isPresent()) {
            if (optionalStudent.get().getAppointments().isEmpty()) {
                this.studentRepo.deleteById(id);
                return true;
            }
            return false;
        }
        return false;
    }

    public Optional<Student> createStudent(Student student) {
        Student newStudent = new Student();
        Optional<Student> optionalStudent = this.validateAppointments(student, student);
        if (optionalStudent.isPresent())
            newStudent = optionalStudent.get();

        optionalStudent = this.studentRepo.findByName(student.getName());
        if (optionalStudent.isPresent())
            return Optional.empty();

        return Optional.of(this.studentRepo.save(newStudent));
    }

    public Optional<Student> editStudent(Student currentStudent, Student student, Long id) {
        Student newStudent = new Student();
        Optional<Student> optionalStudent = this.validateAppointments(currentStudent, student);
        if (optionalStudent.isPresent())
            newStudent = optionalStudent.get();

        optionalStudent = this.studentRepo.findByName(student.getName());
        if (optionalStudent.isPresent() && (!optionalStudent.get().getId().equals(id)))
            return Optional.empty();

        newStudent.setName(student.getName());

        return Optional.of(this.studentRepo.save(newStudent));
    }

    public Optional<Student> validateAppointments(Student currentStudent, Student student) {
        Set<Appointment> newAppointments = new HashSet<>();
        for (Appointment appointment : student.getAppointments()) {
            Optional<Appointment> optionalAppointment = this.appointmentRepo.findById(appointment.getId());
            if (optionalAppointment.isEmpty())
                return Optional.empty();
            Appointment foundAppointment = optionalAppointment.get();
            foundAppointment.setStudent(currentStudent);
            newAppointments.add(foundAppointment);
        }

        currentStudent.setAppointments(newAppointments);
        return Optional.of(currentStudent);
    }
}
