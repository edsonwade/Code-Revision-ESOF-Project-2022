package ufp.esof.project.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.StudentRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentService Tests")

class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentAppointmentService studentAppointmentService;

    @InjectMocks
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student("Test Student");
        testStudent.setId(1L);
        testStudent.setEmail("student@test.com");
    }

    @Test
    @DisplayName("Should retrieve all students successfully when students exist")
    void testGetAllStudentsSuccess() {
        Student student2 = new Student("Another Student");
        student2.setId(2L);
        student2.setEmail("another@test.com");

        when(studentRepository.findAll()).thenReturn(List.of(testStudent, student2));

        Iterable<Student> result = studentService.getAllStudents();

        assertThat(result).isNotNull().hasSize(2);
        verify(studentRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void testGetAllStudentsEmpty() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        Iterable<Student> result = studentService.getAllStudents();

        assertThat(result).isEmpty();
        verify(studentRepository).findAll();
    }

    @Test
    @DisplayName("Should retrieve student by ID successfully")
    void testGetStudentByIdSuccess() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        Student result = studentService.getStudentById(1L);

        assertThat(result)
                .isNotNull()
                .extracting(Student::getId, Student::getName, Student::getEmail)
                .containsExactly(1L, "Test Student", "student@test.com");
        verify(studentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when student ID does not exist")
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(999L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("999");
        verify(studentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should retrieve student by name successfully")
    void testGetStudentByNameSuccess() {
        when(studentRepository.findByName("Test Student")).thenReturn(Optional.of(testStudent));

        Student result = studentService.getStudentByName("Test Student");

        assertThat(result)
                .isNotNull()
                .extracting(Student::getName)
                .isEqualTo("Test Student");
        verify(studentRepository).findByName("Test Student");
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when student name does not exist")
    void testGetStudentByNameNotFound() {
        when(studentRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentByName("NonExistent"))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("NonExistent");
    }

    @Test
    @DisplayName("Should create student successfully with valid data")
    void testCreateStudentSuccess() {
        Student newStudent = new Student("New Student");
        newStudent.setEmail("new@test.com");

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        Student result = studentService.createStudent(newStudent);

        assertThat(result)
                .isNotNull()
                .extracting(Student::getId)
                .isEqualTo(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw DuplicateStudentException when creating student with duplicate email")
    void testCreateStudentDuplicateEmail() {
        Student newStudent = new Student("Another Student");
        newStudent.setEmail("student@test.com");

        when(studentRepository.save(any(Student.class)))
                .thenThrow(new RuntimeException("Duplicate key"));

        assertThatThrownBy(() -> studentService.createStudent(newStudent))
                .isInstanceOf(Exception.class);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should update student successfully with valid data")
    void testUpdateStudentSuccess() {
        Student updatedData = new Student("Updated Student");
        updatedData.setEmail("updated@test.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        Student result = studentService.updateStudent(1L, updatedData);

        assertThat(result).isNotNull();
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when updating non-existent student")
    void testUpdateStudentNotFound() {
        Student updatedData = new Student("Updated");
        updatedData.setEmail("updated@test.com");

        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(999L, updatedData))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("999");
        verify(studentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should delete student successfully when no appointments exist")
    void testDeleteStudentByIdSuccess() {
        testStudent.getAppointments().clear();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        boolean result = studentService.deleteStudentById(1L);

        assertThat(result).isTrue();
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should return false when deleting student with existing appointments")
    void testDeleteStudentWithAppointmentsFails() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        boolean result = studentService.deleteStudentById(1L);

        assertThat(result).isFalse();
        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("Should return false when deleting non-existent student")
    void testDeleteStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        boolean result = studentService.deleteStudentById(999L);

        assertThat(result).isFalse();
        verify(studentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should verify student repository is called for deletion")
    void testDeleteStudentInteraction() {
        testStudent.getAppointments().clear();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        studentService.deleteStudentById(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }


}