package ufp.esof.project.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.models.enums.AppointmentStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AppointmentMapper Tests")
class AppointmentMapperTest {

    private AppointmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AppointmentMapper();
    }

    @Test
    @DisplayName("Should map CreateAppointmentRequest to Appointment entity successfully")
    void testToEntitySuccess() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        Appointment result = mapper.toEntity(request);

        assertThat(result)
                .isNotNull()
                .extracting(Appointment::getStartTime, Appointment::getExpectedEndTime)
                .containsExactly(startTime, endTime);
    }

    @Test
    @DisplayName("Should return null when CreateAppointmentRequest is null")
    void testToEntityNull() {
        Appointment result = mapper.toEntity(null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map Appointment entity to AppointmentResponse successfully")
    void testToResponseSuccess() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        Student student = new Student("Test Student");
        student.setId(1L);

        Explainer explainer = new Explainer("Test Explainer");
        explainer.setId(2L);

        Course course = new Course("Test Course");
        course.setId(3L);

        Appointment appointment = new Appointment(startTime, endTime);
        appointment.setId(100L);
        appointment.setStudent(student);
        appointment.setExplainer(explainer);
        appointment.setCourse(course);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        AppointmentResponse result = mapper.toResponse(appointment);

        assertThat(result)
                .isNotNull()
                .extracting(
                        AppointmentResponse::getId,
                        AppointmentResponse::getStudentId,
                        AppointmentResponse::getStudentName,
                        AppointmentResponse::getExplainerId,
                        AppointmentResponse::getExplainerName,
                        AppointmentResponse::getCourseId,
                        AppointmentResponse::getCourseName,
                        AppointmentResponse::getStatus
                )
                .containsExactly(
                        100L,
                        1L,
                        "Test Student",
                        2L,
                        "Test Explainer",
                        3L,
                        "Test Course",
                        AppointmentStatus.SCHEDULED
                );
    }

    @Test
    @DisplayName("Should handle null relationships when mapping to response")
    void testToResponseWithNullRelationships() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        Appointment appointment = new Appointment(startTime, endTime);
        appointment.setId(100L);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        AppointmentResponse result = mapper.toResponse(appointment);

        assertThat(result)
                .isNotNull()
                .extracting(AppointmentResponse::getId, AppointmentResponse::getStatus)
                .containsExactly(100L, AppointmentStatus.SCHEDULED);
        assertThat(result.getStudentId()).isNull();
        assertThat(result.getExplainerId()).isNull();
    }

    @Test
    @DisplayName("Should return null when Appointment is null")
    void testToResponseNull() {
        AppointmentResponse result = mapper.toResponse(null);

        assertThat(result).isNull();
    }
}

