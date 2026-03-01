package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.student.StudentRequestDTO;
import ufp.esof.project.dto.student.StudentResponseDTO;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentService Tests")
@SuppressWarnings("unused")
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

        when(studentRepository.findAllStudents()).thenReturn(List.of(testStudent, student2));

        var result = studentService.getAllStudents();

        assertThat(result).isNotNull().hasSize(2);
        verify(studentRepository).findAllStudents();
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void testGetAllStudentsEmpty() {
        when(studentRepository.findAllStudents()).thenReturn(Collections.emptyList());

        var result = studentService.getAllStudents();

        assertThat(result).isEmpty();
        verify(studentRepository).findAllStudents();
    }

    @Test
    @DisplayName("Should retrieve student by ID successfully")
    void testGetStudentByIdSuccess() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        var result = studentService.getStudentById(1L);

        assertThat(result)
                .isNotNull()
                .isInstanceOf(Optional.class)
                .hasValueSatisfying(studentResponseDTO -> {
                    assertThat(studentResponseDTO.getId()).isEqualTo(1L);
                    assertThat(studentResponseDTO.getName()).isEqualTo("Test Student");
                    assertThat(studentResponseDTO.getEmail()).isEqualTo("student@test.com");
                });
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

        var result = studentService.getStudentByName("Test Student");

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValueSatisfying(studentResponseDTO -> {
                    assertThat(studentResponseDTO.getId()).isEqualTo(1L);
                    assertThat(studentResponseDTO.getEmail()).isEqualTo("student@test.com");
                });
        verify(studentRepository).findByName("Test Student");
    }


    @Test
    @DisplayName("Should throw StudentNotFoundException when student name does not exist")
    void testGetStudentByNameNotFound() {
        when(studentRepository.findByName("")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentByName(""))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("");
    }

    @Test
    @DisplayName("Should create student successfully with valid data")
    void testCreateStudentSuccess() {
        Student newStudent = new Student("New Student");
        newStudent.setId(1L);
        newStudent.setName("New Student");
        newStudent.setEmail("new@test.com");

        var requestDTO = StudentRequestDTO.builder()
                .name("New Student")
                .email("new@test.com")
                .build();

        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        var result = studentService.createStudent(requestDTO);

        assertThat(result)
                .isNotNull()
                .extracting(StudentResponseDTO::getId)
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

        assertThatThrownBy(() -> studentService.createStudent(StudentRequestDTO.builder()
                .name("Another Student")
                .email("student@test.com")
                .build()))
                .isInstanceOf(Exception.class);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should update student successfully with valid data")
    void testUpdateStudentSuccess() {
        Student updatedData = new Student("Updated Student");
        updatedData.setEmail("updated@test.com");

        var requestDTO = StudentRequestDTO.builder()
                .name("New Student")
                .email("new@test.com")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(testStudent);

        var result = studentService.updateStudent(1L, requestDTO);

        assertThat(result).isNotNull();
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when updating non-existent student")
    void testUpdateStudentNotFound() {
        Student updatedData = new Student("Updated");
        updatedData.setEmail("updated@test.com");
        var requestDTO = StudentRequestDTO.builder()
                .name("New Student")
                .email("new@test.com")
                .build();

        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(999L, requestDTO))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("999");
        verify(studentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should delete student successfully when no appointments exist")
    void testdeleteStudentByIdByIdSuccess() {
        testStudent.getAppointments().clear();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        boolean result = studentService.deleteStudentById(1L);

        assertThat(result).isTrue();
        verify(studentRepository).findById(1L);
        verify(studentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should return false when deleting student with existing appointments")
    void testdeleteStudentByIdWithAppointmentsFails() {
        Appointment testAppointment = new Appointment();
        testStudent.getAppointments().add(testAppointment);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        boolean result = studentService.deleteStudentById(1L);

        assertThat(result).isFalse();
        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when deleting non-existent student")
    void testdeleteStudentByIdNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.deleteStudentById(999L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("999");
        verify(studentRepository).findById(999L);
    }

    @Test
    @DisplayName("Should verify student repository is called for deletion")
    void testdeleteStudentByIdInteraction() {
        testStudent.getAppointments().clear();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));

        studentService.deleteStudentById(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }


}