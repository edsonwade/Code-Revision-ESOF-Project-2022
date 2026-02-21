package ufp.esof.project.services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.exception.DuplicateStudentException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentAppointmentService studentAppointmentService;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentAppointmentService studentAppointmentService) {
        this.studentRepository = studentRepository;
        this.studentAppointmentService = studentAppointmentService;
    }

    @Cacheable(value = "students", key = "'all'")
    public Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Cacheable(value = "students", key = "#id")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));
    }

    @Cacheable(value = "students", key = "#name")
    public Student getStudentByName(String name) {
        return studentRepository.findByName(name)
                .orElseThrow(() -> new StudentNotFoundException("Student with NAME " + name + " not found"));
    }

    @CacheEvict(value = "students", allEntries = true)
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

    @CacheEvict(value = "students", allEntries = true)
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

    @CacheEvict(value = "students", allEntries = true)
    public boolean deleteStudentById(Long id) {
        Student student = getStudentById(id);
        if (!student.getAppointments().isEmpty()) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }


}



