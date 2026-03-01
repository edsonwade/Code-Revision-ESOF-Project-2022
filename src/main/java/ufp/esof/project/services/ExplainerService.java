package ufp.esof.project.services;

import ufp.esof.project.dto.explainer.ExplainerRequestDTO;
import ufp.esof.project.dto.explainer.ExplainerResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ExplainerService {

    List<ExplainerResponseDTO> getAllExplainers();

    Optional<ExplainerResponseDTO> getExplainerById(Long id);

   ExplainerResponseDTO createExplainer(ExplainerRequestDTO explainerRequestDTO);

    ExplainerResponseDTO updateExplainer(Long id, ExplainerRequestDTO explainerRequestDTO);

    boolean deleteExplainer(Long id);
}
