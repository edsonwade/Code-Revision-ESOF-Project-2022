package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.exception.appointmentexception.InvalidAppointmentException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.repository.ExplainerRepository;
import ufp.esof.project.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentServiceImpl Tests")
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ExplainerRepository explainerRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
    }

    @Test
    @DisplayName("Should get set of appointments")
    void testGetSetAppointment() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));
        Set<Appointment> set = appointmentService.getSetAppointment();
        assertThat(set).hasSize(1);
    }

    @Test
    @DisplayName("Should find appointment by id successfully")
    void testFindAppointmentByIdSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        Appointment found = appointmentService.findAppointmentById(1L);
        assertThat(found).isNotNull();
    }

    @Test
    @DisplayName("Should throw InvalidAppointmentException when appointment not found")
    void testFindAppointmentByIdNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> appointmentService.findAppointmentById(999L))
                .isInstanceOf(InvalidAppointmentException.class);
    }

    @Test
    @DisplayName("Should delete appointment by id when exists")
    void testDeleteAppointmentByIdExists() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        boolean deleted = appointmentService.deleteAppointmentById(1L);
        assertThat(deleted).isTrue();
        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete appointment when id mismatch")
    void testDeleteAppointmentByIdMismatch() {
        Appointment other = new Appointment();
        other.setId(2L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(other));
        boolean deleted = appointmentService.deleteAppointmentById(1L);
        assertThat(deleted).isFalse();
        verify(appointmentRepository, never()).deleteById(1L);
    }
}

