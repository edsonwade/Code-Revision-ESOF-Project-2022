package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.exception.AppointmentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.services.StudentAppointmentService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StudentAppointmentService Tests")
class StudentAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private StudentAppointmentService studentAppointmentService;

    private Student student;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        student = new Student("Test");
        student.setId(1L);

        appointment = new Appointment();
        appointment.setId(10L);
    }

    @Test
    @DisplayName("Should validate and attach appointments successfully")
    void testValidateAndAttachAppointmentsSuccess() {
        Set<Appointment> input = new HashSet<>();
        input.add(appointment);

        Appointment found = new Appointment();
        found.setId(10L);

        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(found));

        Set<Appointment> result = studentAppointmentService.validateAndAttachAppointments(student, input);

        assertThat(result).isNotEmpty();
        assertThat(result.iterator().next().getStudent()).isEqualTo(student);
    }

    @Test
    @DisplayName("Should throw AppointmentNotFoundException when appointment not found")
    void testValidateAndAttachAppointmentsNotFound() {
        Set<Appointment> input = new HashSet<>();
        input.add(appointment);

        when(appointmentRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentAppointmentService.validateAndAttachAppointments(student, input))
                .isInstanceOf(AppointmentNotFoundException.class)
                .hasMessageContaining("10");
    }
}

