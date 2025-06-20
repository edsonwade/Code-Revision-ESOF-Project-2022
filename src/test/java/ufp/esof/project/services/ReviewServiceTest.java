package ufp.esof.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ufp.esof.project.dto.ReviewCreateDTO;
import ufp.esof.project.exception.AppointmentNotFoundException;
import ufp.esof.project.exception.ExplainerNotFoundException;
import ufp.esof.project.exception.StudentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.models.Review;
import ufp.esof.project.models.Student;
import ufp.esof.project.repository.AppointmentRepository;
import ufp.esof.project.repository.ExplainerRepository;
import ufp.esof.project.repository.ReviewRepository;
import ufp.esof.project.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ExplainerRepository explainerRepository;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewCreateDTO validReviewCreateDTO;
    private Appointment appointment;
    private Student student;
    private Explainer explainer;
    private Review review;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup dados de exemplo válidos
        validReviewCreateDTO = new ReviewCreateDTO();
        validReviewCreateDTO.setRating(5);
        validReviewCreateDTO.setComment("This is a valid comment with sufficient length.");
        validReviewCreateDTO.setAppointmentId(1L);
        validReviewCreateDTO.setStudentId(1L);
        validReviewCreateDTO.setExplainerId(1L);

        appointment = new Appointment();
        appointment.setId(1L);

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");

        explainer = new Explainer();
        explainer.setId(1L);
        explainer.setName("Jane Smith");

        review = new Review();
        review.setId(10L);
        review.setRating(5);
        review.setComment(validReviewCreateDTO.getComment());
        review.setAppointment(appointment);
        review.setStudent(student);
        review.setExplainer(explainer);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Create review with success")
    void createReview_Success() {
        // given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(explainerRepository.findById(1L)).thenReturn(Optional.of(explainer));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // when
        var responseDTO = reviewService.createReview(validReviewCreateDTO);

        // then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(review.getId());
        assertThat(responseDTO.getRating()).isEqualTo(validReviewCreateDTO.getRating());
        assertThat(responseDTO.getComment()).isEqualTo(validReviewCreateDTO.getComment());
        assertThat(responseDTO.getStudentName()).isEqualTo(student.getName());
        assertThat(responseDTO.getExplainerName()).isEqualTo(explainer.getName());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("Failed to create review  - Appointment not founded")
    void createReview_AppointmentNotFound() {
        // given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> reviewService.createReview(validReviewCreateDTO));

        // then
        assertThat(thrown)
                .isInstanceOf(AppointmentNotFoundException.class)
                .hasMessageContaining("Appointment not found with id 1");
        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("Failed to create review - Student not founded")
    void createReview_StudentNotFound() {
        // given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> reviewService.createReview(validReviewCreateDTO));

        // then
        assertThat(thrown)
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student not found with id 1");
        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("Failed to create review - Explainer not founded")
    void createReview_ExplainerNotFound() {
        // given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(explainerRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> reviewService.createReview(validReviewCreateDTO));

        // then
        assertThat(thrown)
                .isInstanceOf(ExplainerNotFoundException.class)
                .hasMessageContaining("Explainer not found with id 1");
        verify(reviewRepository, never()).save(any());
    }

    @Test
    @DisplayName("List all reviews")
    void getAllReviews_Success() {
        // given
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // when
        var reviews = reviewService.getAllReviews();

        // then
        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(1);
        assertThat(reviews.get(0).getId()).isEqualTo(review.getId());
        assertThat(reviews.get(0).getComment()).isEqualTo(review.getComment());
        verify(reviewRepository, times(1)).findAll();
    }
}
