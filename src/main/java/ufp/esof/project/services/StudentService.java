/**
 * Author: vanilson muhongo
 * Date:22/02/2026
 * Time:17:17
 * Version:1
 */

package ufp.esof.project.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.student.StudentRequestDTO;
import ufp.esof.project.dto.student.StudentResponseDTO;
import ufp.esof.project.exception.DuplicateStudentException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unused")
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, StudentAppointmentService studentAppointmentService) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "students", key = "'all'")
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAllStudents()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "students", key = "#id")
    public Optional<StudentResponseDTO> getStudentById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("student not found with id " + id));
        return Optional.of(toResponseDTO(student));
    }

    @Cacheable(value = "students", key = "#name")
    public Optional<StudentResponseDTO> getStudentByName(String name) {
        var getStudentByName = studentRepository.findByName(name)
                .orElseThrow(() -> new StudentNotFoundException("student not found with name " + name));
        return Optional.of(toResponseDTO(getStudentByName));

    }

    @CacheEvict(value = "students", allEntries = true)
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        if (studentRequestDTO == null || studentRequestDTO.getName() == null || studentRequestDTO.getEmail() == null) {
            throw new IllegalArgumentException("Student fields are mandatory");
        }
        var student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setEmail(studentRequestDTO.getEmail());
        student = studentRepository.save(student);
        return toResponseDTO(student);
    }

    @CacheEvict(value = "students", allEntries = true)

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));

        if (studentRepository.findByName(studentRequestDTO.getName())
                .filter(s -> !s.getId().equals(id))
                .isPresent()) {
            throw new DuplicateStudentException("Another student with name '" + studentRequestDTO.getName() + "' already exists");
        }

        existingStudent.setName(studentRequestDTO.getName());
        existingStudent.setEmail(studentRequestDTO.getEmail());

        Student savedStudent = studentRepository.save(existingStudent);
        return toResponseDTO(savedStudent);
    }

    @CacheEvict(value = "students", allEntries = true)

    public boolean deleteStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found"));

        if (!student.getAppointments().isEmpty()) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    private StudentResponseDTO toResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
