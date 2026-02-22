package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.exception.appointmentexception.AppointmentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.repository.ExplainerRepository;
import ufp.esof.project.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentServiceImpl Tests")
@SuppressWarnings("all")
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
        var allAppointments = appointmentService.getAllAppointments();
        assertThat(allAppointments).hasSize(1);
    }

    @Test
    @DisplayName("Should find appointment by id successfully")
    void testFindAppointmentByIdSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        var appointmentById = appointmentService.getAppointmentById(1L);
        assertThat(appointmentById).isNotNull();
    }

    @Test
    @DisplayName("Should throw AppointmentNotFoundException when appointment not found")
    void testFindAppointmentByIdNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> appointmentService.getAppointmentById(999L))
                .isInstanceOf(AppointmentNotFoundException.class);
    }

    @Test
    @DisplayName("Should delete appointment by id when exists")
    void testDeleteAppointmentByIdExists() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        boolean deleted = appointmentService.deleteAppointment(1L);
        assertThat(deleted).isTrue();
        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete appointment when id mismatch")
    void testDeleteAppointmentByIdMismatch() {
        var other = new Appointment();
        other.setId(2L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(other));
        boolean deleted = appointmentService.deleteAppointment(1L);
        assertThat(deleted).isFalse();
        verify(appointmentRepository, never()).deleteById(1L);
    }
}

