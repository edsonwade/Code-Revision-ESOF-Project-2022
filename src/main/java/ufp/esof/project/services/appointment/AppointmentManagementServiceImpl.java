package ufp.esof.project.services.appointment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of appointment management service.
 * Handles all appointment-related operations with proper validation and business logic.
 */
@Slf4j
@Service
@Transactional
public class AppointmentManagementServiceImpl implements AppointmentManagementService {

    private final AppointmentRepository appointmentRepository;
    private final StudentRepository studentRepository;
    private final ExplainerRepository explainerRepository;
    private final CourseRepo courseRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentManagementServiceImpl(
            AppointmentRepository appointmentRepository,
            StudentRepository studentRepository,
            ExplainerRepository explainerRepository,
            CourseRepo courseRepository,
            AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.studentRepository = studentRepository;
        this.explainerRepository = explainerRepository;
        this.courseRepository = courseRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        log.info("Creating new appointment for student: {}, explainer: {}", request.getStudentId(), request.getExplainerId());

        // Validate input
        if (!request.isValid()) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Fetch entities
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + request.getStudentId()));

        Explainer explainer = explainerRepository.findById(request.getExplainerId())
                .orElseThrow(() -> new ExplainerNotFoundException("Explainer not found with ID: " + request.getExplainerId()));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + request.getCourseId()));

        // Check for time conflicts
        if (hasTimeConflict(request.getExplainerId(), request.getStartTime(), request.getEndTime())) {
            throw new IllegalArgumentException("Explainer has a conflicting appointment at the requested time");
        }

        // Create appointment
        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setStudent(student);
        appointment.setExplainer(explainer);
        appointment.setCourse(course);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment created successfully with ID: {}", savedAppointment.getId());

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<AppointmentResponse> getStudentAppointments(Long studentId) {
        log.debug("Fetching appointments for student: {}", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        return student.getAppointments()
                .stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getExplainerAppointments(Long explainerId) {
        log.debug("Fetching appointments for explainer: {}", explainerId);

        Explainer explainer = explainerRepository.findById(explainerId)
                .orElseThrow(() -> new ExplainerNotFoundException("Explainer not found with ID: " + explainerId));

        return explainer.getAppointments()
                .stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse rescheduleAppointment(Long appointmentId, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        log.info("Rescheduling appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        if (!appointment.getStatus().canBeRescheduled()) {
            throw new IllegalStateException("Appointment with status " + appointment.getStatus() + " cannot be rescheduled");
        }

        // Validate new times
        if (newStartTime == null || newEndTime == null || newEndTime.isBefore(newStartTime)) {
            throw new IllegalArgumentException("Invalid time range for rescheduling");
        }

        // Check for conflicts with new time
        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(
                appointment.getExplainer().getId(),
                newStartTime,
                newEndTime,
                appointmentId
        );

        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Explainer has conflicting appointments at the new time");
        }

        // Update appointment
        appointment.setStartTime(newStartTime);
        appointment.setExpectedEndTime(newEndTime);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        Appointment rescheduled = appointmentRepository.save(appointment);
        log.info("Appointment {} rescheduled successfully", appointmentId);

        return appointmentMapper.toResponse(rescheduled);
    }

    @Override
    public boolean cancelAppointment(Long appointmentId) {
        log.info("Cancelling appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        if (!appointment.getStatus().canBeCancelled()) {
            throw new IllegalStateException("Appointment with status " + appointment.getStatus() + " cannot be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        log.info("Appointment {} cancelled successfully", appointmentId);
        return true;
    }

    @Override
    public boolean completeAppointment(Long appointmentId) {
        log.info("Completing appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        log.info("Appointment {} completed successfully", appointmentId);
        return true;
    }

    @Override
    public boolean markAsNoShow(Long appointmentId) {
        log.info("Marking appointment as no-show: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        appointment.setStatus(AppointmentStatus.NO_SHOW);
        appointmentRepository.save(appointment);

        log.info("Appointment {} marked as no-show", appointmentId);
        return true;
    }

    @Override
    public AppointmentResponse updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus) {
        log.info("Updating appointment {} status to {}", appointmentId, newStatus);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        // Validate status transition
        if (appointment.getStatus().isFinalState()) {
            throw new IllegalStateException("Cannot change status of appointment in final state: " + appointment.getStatus());
        }

        appointment.setStatus(newStatus);
        Appointment updated = appointmentRepository.save(appointment);

        log.info("Appointment {} status updated to {}", appointmentId, newStatus);
        return appointmentMapper.toResponse(updated);
    }

    @Override
    public boolean deleteAppointment(Long appointmentId) {
        log.info("Deleting appointment: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));

        appointment.softDelete();
        appointmentRepository.save(appointment);

        log.info("Appointment {} deleted successfully", appointmentId);
        return true;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasTimeConflict(Long explainerId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(explainerId, startTime, endTime, null);
        return !conflicts.isEmpty();
    }
}

