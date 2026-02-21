package ufp.esof.project.services.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.exception.AppointmentNotFoundException;
import ufp.esof.project.exception.ExplainerNotFoundException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.mapper.AppointmentMapper;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.models.enums.AppointmentStatus;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.repository.CourseRepo;
import ufp.esof.project.repository.ExplainerRepository;
import ufp.esof.project.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AppointmentManagementService Tests")
class AppointmentManagementServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ExplainerRepository explainerRepository;

    @Mock
    private CourseRepo courseRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentManagementServiceImpl appointmentService;

    private Student testStudent;
    private Explainer testExplainer;
    private Course testCourse;
    private Appointment testAppointment;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0).withSecond(0).withNano(0);
        endTime = startTime.plusHours(1);

        testStudent = new Student("Test Student");
        testStudent.setId(1L);
        testStudent.setEmail("student@test.com");

        testExplainer = new Explainer("Test Explainer");
        testExplainer.setId(2L);
        testExplainer.setEmail("explainer@test.com");

        testCourse = new Course("Test Course");
        testCourse.setId(3L);

        testAppointment = new Appointment(startTime, endTime);
        testAppointment.setId(1L);
        testAppointment.setStudent(testStudent);
        testAppointment.setExplainer(testExplainer);
        testAppointment.setCourse(testCourse);
        testAppointment.setStatus(AppointmentStatus.SCHEDULED);
    }

    @Test
    @DisplayName("Should create appointment successfully when all entities exist and no conflicts")
    void testCreateAppointmentSuccess() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(explainerRepository.findById(2L)).thenReturn(Optional.of(testExplainer));
        when(courseRepository.findById(3L)).thenReturn(Optional.of(testCourse));
        when(appointmentRepository.findConflictingAppointments(anyLong(), any(), any(), isNull()))
                .thenReturn(Collections.emptyList());
        when(appointmentMapper.toEntity(request)).thenReturn(testAppointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .studentId(1L)
                        .studentName("Test Student")
                        .explainerId(2L)
                        .explainerName("Test Explainer")
                        .courseId(3L)
                        .courseName("Test Course")
                        .status(AppointmentStatus.SCHEDULED)
                        .build());

        AppointmentResponse response = appointmentService.createAppointment(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw StudentNotFoundException when student does not exist")
    void testCreateAppointmentStudentNotFound() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(999L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.createAppointment(request))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("Should throw ExplainerNotFoundException when explainer does not exist")
    void testCreateAppointmentExplainerNotFound() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(999L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(explainerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.createAppointment(request))
                .isInstanceOf(ExplainerNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    @DisplayName("Should throw exception when time range is invalid (end before start)")
    void testCreateAppointmentInvalidTimeRange() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(endTime)
                .endTime(startTime)
                .build();

        assertThatThrownBy(() -> appointmentService.createAppointment(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("End time must be after start time");
    }

    @Test
    @DisplayName("Should throw exception when appointment has conflict")
    void testCreateAppointmentWithConflict() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(explainerRepository.findById(2L)).thenReturn(Optional.of(testExplainer));
        when(courseRepository.findById(3L)).thenReturn(Optional.of(testCourse));
        when(appointmentRepository.findConflictingAppointments(2L, startTime, endTime, null))
                .thenReturn(List.of(testAppointment));

        assertThatThrownBy(() -> appointmentService.createAppointment(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("conflicting");
    }

    @Test
    @DisplayName("Should retrieve appointment by ID successfully")
    void testGetAppointmentByIdSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        Optional<Appointment> result = appointmentService.getAppointmentById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(appointmentRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when appointment not found by ID")
    void testGetAppointmentByIdNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Appointment> result = appointmentService.getAppointmentById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should retrieve student appointments successfully")
    void testGetStudentAppointmentsSuccess() {
        testStudent.getAppointments().add(testAppointment);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(testStudent));
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .studentId(1L)
                        .studentName("Test Student")
                        .build());

        List<AppointmentResponse> responses = appointmentService.getStudentAppointments(1L);

        assertThat(responses).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should throw exception when student not found for appointments retrieval")
    void testGetStudentAppointmentsStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.getStudentAppointments(999L))
                .isInstanceOf(StudentNotFoundException.class);
    }

    @Test
    @DisplayName("Should reschedule appointment successfully to new time")
    void testRescheduleAppointmentSuccess() {
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusHours(1);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.findConflictingAppointments(2L, newStartTime, newEndTime, 1L))
                .thenReturn(Collections.emptyList());
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .status(AppointmentStatus.RESCHEDULED)
                        .build());

        AppointmentResponse response = appointmentService.rescheduleAppointment(1L, newStartTime, newEndTime);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(AppointmentStatus.RESCHEDULED);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw exception when rescheduling non-existent appointment")
    void testRescheduleAppointmentNotFound() {
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusHours(1);

        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.rescheduleAppointment(999L, newStartTime, newEndTime))
                .isInstanceOf(AppointmentNotFoundException.class);
    }

    @Test
    @DisplayName("Should throw exception when rescheduling completed appointment")
    void testRescheduleCompletedAppointmentFails() {
        LocalDateTime newStartTime = startTime.plusDays(1);
        LocalDateTime newEndTime = newStartTime.plusHours(1);

        testAppointment.setStatus(AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        assertThatThrownBy(() -> appointmentService.rescheduleAppointment(1L, newStartTime, newEndTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot be rescheduled");
    }

    @Test
    @DisplayName("Should cancel appointment successfully")
    void testCancelAppointmentSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        boolean result = appointmentService.cancelAppointment(1L);

        assertThat(result).isTrue();
        assertThat(testAppointment.getStatus()).isEqualTo(AppointmentStatus.CANCELLED);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw exception when cancelling completed appointment")
    void testCancelCompletedAppointmentFails() {
        testAppointment.setStatus(AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        assertThatThrownBy(() -> appointmentService.cancelAppointment(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cannot be cancelled");
    }

    @Test
    @DisplayName("Should mark appointment as completed successfully")
    void testCompleteAppointmentSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        boolean result = appointmentService.completeAppointment(1L);

        assertThat(result).isTrue();
        assertThat(testAppointment.getStatus()).isEqualTo(AppointmentStatus.COMPLETED);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should mark appointment as no-show successfully")
    void testMarkAsNoShowSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        boolean result = appointmentService.markAsNoShow(1L);

        assertThat(result).isTrue();
        assertThat(testAppointment.getStatus()).isEqualTo(AppointmentStatus.NO_SHOW);
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should update appointment status successfully")
    void testUpdateAppointmentStatusSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .status(AppointmentStatus.RESCHEDULED)
                        .build());

        AppointmentResponse response = appointmentService.updateAppointmentStatus(1L, AppointmentStatus.RESCHEDULED);

        assertThat(response).isNotNull();
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw exception when updating status of completed appointment")
    void testUpdateStatusOfCompletedAppointmentFails() {
        testAppointment.setStatus(AppointmentStatus.COMPLETED);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));

        assertThatThrownBy(() -> appointmentService.updateAppointmentStatus(1L, AppointmentStatus.CANCELLED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("final state");
    }

    @Test
    @DisplayName("Should soft delete appointment successfully")
    void testDeleteAppointmentSuccess() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(testAppointment);

        boolean result = appointmentService.deleteAppointment(1L);

        assertThat(result).isTrue();
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent appointment")
    void testDeleteAppointmentNotFound() {
        when(appointmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.deleteAppointment(999L))
                .isInstanceOf(AppointmentNotFoundException.class);
    }

    @Test
    @DisplayName("Should retrieve all appointments successfully")
    void testGetAllAppointmentsSuccess() {
        when(appointmentRepository.findAll()).thenReturn(List.of(testAppointment));
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .build());

        List<AppointmentResponse> responses = appointmentService.getAllAppointments();

        assertThat(responses).isNotEmpty().hasSize(1);
        verify(appointmentRepository).findAll();
    }

    @Test
    @DisplayName("Should detect time conflict correctly")
    void testHasTimeConflictTrue() {
        when(appointmentRepository.findConflictingAppointments(2L, startTime, endTime, null))
                .thenReturn(List.of(testAppointment));

        boolean hasConflict = appointmentService.hasTimeConflict(2L, startTime, endTime);

        assertThat(hasConflict).isTrue();
    }

    @Test
    @DisplayName("Should return false when no time conflict exists")
    void testHasTimeConflictFalse() {
        when(appointmentRepository.findConflictingAppointments(2L, startTime, endTime, null))
                .thenReturn(Collections.emptyList());

        boolean hasConflict = appointmentService.hasTimeConflict(2L, startTime, endTime);

        assertThat(hasConflict).isFalse();
    }

    @Test
    @DisplayName("Should get explainer appointments successfully")
    void testGetExplainerAppointmentsSuccess() {
        testExplainer.getAppointments().add(testAppointment);
        when(explainerRepository.findById(2L)).thenReturn(Optional.of(testExplainer));
        when(appointmentMapper.toResponse(testAppointment))
                .thenReturn(AppointmentResponse.builder()
                        .id(1L)
                        .explainerId(2L)
                        .build());

        List<AppointmentResponse> responses = appointmentService.getExplainerAppointments(2L);

        assertThat(responses).isNotEmpty().hasSize(1);
    }

    @Test
    @DisplayName("Should throw exception when explainer not found for appointments retrieval")
    void testGetExplainerAppointmentsNotFound() {
        when(explainerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.getExplainerAppointments(999L))
                .isInstanceOf(ExplainerNotFoundException.class);
    }
}

