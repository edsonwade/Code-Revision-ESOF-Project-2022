/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:46
 * Version:1
 */

package ufp.esof.project.services;

import java.util.List;
import org.springframework.stereotype.Service;
import ufp.esof.project.dto.ReviewCreateDTO;
import ufp.esof.project.dto.ReviewResponseDTO;
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

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final StudentRepository studentRepository;
    private final ExplainerRepository explainerRepository;


    public ReviewService(ReviewRepository reviewRepository, AppointmentRepository appointmentRepository,
                         StudentRepository studentRepository, ExplainerRepository explainerRepository) {
        this.reviewRepository = reviewRepository;
        this.appointmentRepository = appointmentRepository;
        this.studentRepository = studentRepository;
        this.explainerRepository = explainerRepository;
    }

    /**
     * Cria uma nova review a partir do DTO de criação.
     *
     * @param reviewCreateDTO DTO com os dados da review a ser criada.
     * @return DTO com dados da review criada.
     */
    public ReviewResponseDTO createReview(ReviewCreateDTO reviewCreateDTO) {
        // Buscar as entidades associadas para garantir existência e integridade
        Appointment appointment = appointmentRepository.findById(reviewCreateDTO.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id " + reviewCreateDTO.getAppointmentId()));

        Student student = studentRepository.findById(reviewCreateDTO.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + reviewCreateDTO.getStudentId()));

        Explainer explainer = explainerRepository.findById(reviewCreateDTO.getExplainerId())
                .orElseThrow(() -> new ExplainerNotFoundException("Explainer not found with id " + reviewCreateDTO.getExplainerId()));

        // Criar entidade Review
        Review review = new Review();
        review.setRating(reviewCreateDTO.getRating());
        review.setComment(reviewCreateDTO.getComment());
        review.setAppointment(appointment);
        review.setStudent(student);
        review.setExplainer(explainer);

        // Salvar e retornar DTO de resposta
        Review savedReview = reviewRepository.save(review);

        return mapToResponseDTO(savedReview);
    }

    /**
     * Lista todas as ‘reviews’, convertendo para DTO de resposta.
     *
     * @return Lista de ReviewResponseDTO
     */
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Método privado para conversão de entidade para DTO de resposta
    private ReviewResponseDTO mapToResponseDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getStudent().getName(),
                review.getExplainer().getName(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
