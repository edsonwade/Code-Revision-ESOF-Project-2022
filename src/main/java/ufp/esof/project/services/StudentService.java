package ufp.esof.project.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.exception.DuplicateStudentException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentAppointmentService studentAppointmentService;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentAppointmentService studentAppointmentService) {
        this.studentRepository = studentRepository;
        this.studentAppointmentService = studentAppointmentService;
    }

    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));
    }

    public Student getStudentByName(String name) {
        return studentRepository.findByName(name)
                .orElseThrow(() -> new StudentNotFoundException("Student with NAME " + name + " not found"));
    }

    public Student createStudent(Student studentInput) {
        if (studentRepository.findByName(studentInput.getName()).isPresent()) {
            throw new DuplicateStudentException("Student with name '" + studentInput.getName() + "' already exists");
        }

        Student newStudent = new Student();
        newStudent.setName(studentInput.getName());

        Set<Appointment> appointments = studentAppointmentService.validateAndAttachAppointments(newStudent, studentInput.getAppointments());
        newStudent.setAppointments(appointments);

        return studentRepository.save(newStudent);
    }

    public Student updateStudent(Long id, Student updatedData) {
        Student existingStudent = getStudentById(id);
        if (studentRepository.findByName(updatedData.getName())
                .filter(s -> !s.getId().equals(id))
                .isPresent()) {
            throw new DuplicateStudentException("Another student with name '" + updatedData.getName() + "' already exists");
        }
        existingStudent.setName(updatedData.getName());
        existingStudent.setEmail(updatedData.getEmail());
        Set<Appointment> updatedAppointments = studentAppointmentService.validateAndAttachAppointments(existingStudent, updatedData.getAppointments());
        existingStudent.setAppointments(updatedAppointments);
        return studentRepository.save(existingStudent);
    }

    public boolean deleteStudentById(Long id) {
        Student student = getStudentById(id);
        if (!student.getAppointments().isEmpty()) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }


}



