package ufp.esof.project.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.exception.appointmentexception.AppointmentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Student;
import ufp.esof.project.models.enums.AppointmentStatus;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.repository.CourseRepository;
import ufp.esof.project.repository.ExplainerRepository;
import ufp.esof.project.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ExplainerRepository explainerRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  ExplainerRepository explainerRepository,
                                  StudentRepository studentRepository,
                                  CourseRepository courseRepository) {
        this.appointmentRepository = appointmentRepository;
        this.explainerRepository = explainerRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppointmentResponse> getAppointmentById(Long id) {
        var appointment = Optional.of(appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id)));
        return Optional.of(toResponseDTO(appointment.get()));
    }

    @Override
    public Optional<AppointmentResponse> createAppointment(CreateAppointmentRequest request) {
        Optional<Student> student = studentRepository.findById(request.getStudentId());
        Optional<Explainer> explainer = explainerRepository.findById(request.getExplainerId());
        Optional<Course> course = courseRepository.findById(request.getCourseId());

        if (student.isEmpty() || explainer.isEmpty() || course.isEmpty()) {
            return Optional.empty();
        }

        Appointment appointment = new Appointment();
        appointment.setStudent(student.get());
        appointment.setExplainer(explainer.get());
        appointment.setCourse(course.get());
        appointment.setStartTime(request.getStartTime());
        appointment.setExpectedEndTime(request.getEndTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = appointmentRepository.save(appointment);
        return Optional.of(toResponseDTO(saved));
    }

    @Override
    public boolean deleteAppointment(Long id) {
        var appointment = appointmentRepository.findById(id);
        if (appointment.isPresent() && appointment.get().getId().equals(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private AppointmentResponse toResponseDTO(Appointment appointment) {
        return getAppointmentResponse(appointment);
    }

    public static AppointmentResponse getAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .studentId(appointment.getStudent() != null ? appointment.getStudent().getId() : null)
                .studentName(appointment.getStudent() != null ? appointment.getStudent().getName() : null)
                .explainerId(appointment.getExplainer() != null ? appointment.getExplainer().getId() : null)
                .explainerName(appointment.getExplainer() != null ? appointment.getExplainer().getName() : null)
                .courseId(appointment.getCourse() != null ? appointment.getCourse().getId() : null)
                .courseName(appointment.getCourse() != null ? appointment.getCourse().getName() : null)
                .startTime(appointment.getStartTime())
                .endTime(appointment.getExpectedEndTime())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }
}
