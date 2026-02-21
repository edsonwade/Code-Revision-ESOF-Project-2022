package ufp.esof.project.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CreateAppointmentRequest DTO Validation Tests")
class CreateAppointmentRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should validate successfully with all required fields present")
    void testValidRequest() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail validation when studentId is null")
    void testMissingStudentId() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(startTime.plusHours(1))
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Student ID is required");
    }

    @Test
    @DisplayName("Should fail validation when explainerId is null")
    void testMissingExplainerId() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(startTime.plusHours(1))
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Explainer ID is required");
    }

    @Test
    @DisplayName("Should fail validation when courseId is null")
    void testMissingCourseId() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .startTime(startTime)
                .endTime(startTime.plusHours(1))
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Course ID is required");
    }

    @Test
    @DisplayName("Should fail validation when startTime is null")
    void testMissingStartTime() {
        LocalDateTime endTime = LocalDateTime.now().plusDays(1).plusHours(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .endTime(endTime)
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Start time is required");
    }

    @Test
    @DisplayName("Should fail validation when endTime is null")
    void testMissingEndTime() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("End time is required");
    }

    @Test
    @DisplayName("Should fail validation when IDs are negative")
    void testNegativeIds() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(-1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(startTime.plusHours(1))
                .build();

        Set<ConstraintViolation<CreateAppointmentRequest>> violations = validator.validate(request);

        assertThat(violations)
                .isNotEmpty()
                .extracting(ConstraintViolation::getMessage)
                .contains("Student ID must be a positive number");
    }

    @Test
    @DisplayName("Should be valid when isValid() method returns true")
    void testIsValidMethod() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.plusHours(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        assertThat(request.isValid()).isTrue();
    }

    @Test
    @DisplayName("Should be invalid when end time is before start time")
    void testInvalidTimeRange() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.minusHours(1);

        CreateAppointmentRequest request = CreateAppointmentRequest.builder()
                .studentId(1L)
                .explainerId(2L)
                .courseId(3L)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        assertThat(request.isValid()).isFalse();
    }
}

