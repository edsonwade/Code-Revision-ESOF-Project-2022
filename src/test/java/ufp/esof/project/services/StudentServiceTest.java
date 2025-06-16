package ufp.esof.project.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.exception.DuplicateStudentException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repositories.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentAppointmentService studentAppointmentService;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Given students exist, when getAllStudents is called, then return all students")
    void getAllStudents_ReturnsAllStudents() {
        // Given
        List<Student> students = List.of(new Student(), new Student());
        given(studentRepository.findAll()).willReturn(students);

        // When
        Iterable<Student> result = studentService.getAllStudents();

        // Then
        assertThat(result).isNotEmpty().hasSize(2);
        verify(studentRepository).findAll();
    }

    @Test
    @DisplayName("Given student exists, when getStudentById is called, then return the student")
    void getStudentById_ExistingId_ReturnsStudent() {
        // Given
        Student student = new Student();
        student.setId(1L);
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));

        // When
        Student result = studentService.getStudentById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Given student does not exist, when getStudentById is called, then throw StudentNotFoundException")
    void getStudentById_NonExistingId_ThrowsException() {
        // Given
        given(studentRepository.findById(1L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with ID 1 not found");
    }

    @Test
    @DisplayName("Given student exists, when getStudentByName is called, then return the student")
    void getStudentByName_ExistingName_ReturnsStudent() {
        // Given
        Student student = new Student();
        student.setName("John");
        given(studentRepository.findByName("John")).willReturn(Optional.of(student));

        // When
        Student result = studentService.getStudentByName("John");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
    }

    @Test
    @DisplayName("Given student does not exist, when getStudentByName is called, then throw StudentNotFoundException")
    void getStudentByName_NonExistingName_ThrowsException() {
        // Given
        given(studentRepository.findByName("John")).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> studentService.getStudentByName("John"))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with NAME John not found");
    }

    @Test
    @DisplayName("Given unique student, when createStudent is called, then save and return the new student")
    void createStudent_UniqueName_SavesStudent() {
        // Given
        Student input = new Student();
        input.setName("New Student");
        input.setAppointments(Set.of());

        given(studentRepository.findByName("New Student")).willReturn(Optional.empty());
        given(studentAppointmentService.validateAndAttachAppointments(any(), any())).willReturn(Set.of());
        given(studentRepository.save(any(Student.class))).willAnswer(invocation -> invocation.getArgument(0));

        // When
        Student result = studentService.createStudent(input);

        // Then
        assertThat(result.getName()).isEqualTo("New Student");
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    @DisplayName("Given duplicate student name, when createStudent is called, then throw DuplicateStudentException")
    void createStudent_DuplicateName_ThrowsException() {
        // Given
        Student input = new Student();
        input.setName("Existing Student");
        given(studentRepository.findByName("Existing Student")).willReturn(Optional.of(new Student()));

        // When / Then
        assertThatThrownBy(() -> studentService.createStudent(input))
                .isInstanceOf(DuplicateStudentException.class)
                .hasMessageContaining("Student with name 'Existing Student' already exists");
    }

    @Test
    @DisplayName("Given existing student, when updateStudent is called with unique new name, then update and return student")
    void updateStudent_ValidData_UpdatesStudent() {
        // Given
        Student existing = new Student();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setEmail("old@email.com");
        existing.setAppointments(Set.of());

        Student updated = new Student();
        updated.setName("New Name");
        updated.setEmail("new@email.com");
        updated.setAppointments(Set.of());

        given(studentRepository.findById(1L)).willReturn(Optional.of(existing));
        given(studentRepository.findByName("New Name")).willReturn(Optional.empty());
        given(studentAppointmentService.validateAndAttachAppointments(existing, updated.getAppointments())).willReturn(Set.of());
        given(studentRepository.save(existing)).willReturn(existing);

        // When
        Student result = studentService.updateStudent(1L, updated);

        // Then
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getEmail()).isEqualTo("new@email.com");
        verify(studentRepository).save(existing);
    }

    @Test
    @DisplayName("Given another student with same name exists, when updateStudent is called, then throw DuplicateStudentException")
    void updateStudent_DuplicateName_ThrowsException() {
        // Given
        Student updated = new Student();
        updated.setName("Existing Name");

        // Student found by id for update
        Student existingStudent = new Student();
        existingStudent.setId(1L);

        // Another student found with the same name but different ID (to simulate duplication)
        Student duplicateStudent = new Student();
        duplicateStudent.setId(2L);  // Important: different ID from 1L!

        given(studentRepository.findById(1L)).willReturn(Optional.of(existingStudent));
        given(studentRepository.findByName("Existing Name")).willReturn(Optional.of(duplicateStudent));

        // When / Then
        assertThatThrownBy(() -> studentService.updateStudent(1L, updated))
                .isInstanceOf(DuplicateStudentException.class)
                .hasMessageContaining("Another student with name 'Existing Name' already exists");
    }


    @Test
    @DisplayName("Given student with no appointments, when deleteStudentById is called, then delete and return true")
    void deleteStudentById_NoAppointments_DeletesStudent() {
        // Given
        Student student = new Student();
        student.setId(1L);
        student.setAppointments(Collections.emptySet());

        given(studentRepository.findById(1L)).willReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1L);

        // When
        boolean deleted = studentService.deleteStudentById(1L);

        // Then
        assertThat(deleted).isTrue();
        verify(studentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Given student with appointments, when deleteStudentById is called, then do not delete and return false")
    void deleteStudentById_WithAppointments_DoesNotDelete() {
        // Given
        Student student = new Student();
        student.setId(1L);
        student.setAppointments(Set.of(new Appointment()));

        given(studentRepository.findById(1L)).willReturn(Optional.of(student));

        // When
        boolean deleted = studentService.deleteStudentById(1L);

        // Then
        assertThat(deleted).isFalse();
        verify(studentRepository, never()).deleteById(anyLong());
    }
}
