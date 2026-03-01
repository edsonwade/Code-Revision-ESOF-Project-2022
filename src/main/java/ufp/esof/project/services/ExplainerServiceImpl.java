package ufp.esof.project.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.explainer.ExplainerRequestDTO;
import ufp.esof.project.dto.explainer.ExplainerResponseDTO;
import ufp.esof.project.exception.ExplainerAlreadyExistsException;
import ufp.esof.project.exception.ExplainerNotFoundException;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.ExplainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unused")
public class ExplainerServiceImpl implements ExplainerService {

    private final ExplainerRepository explainerRepository;

    public ExplainerServiceImpl(ExplainerRepository explainerRepository) {
        this.explainerRepository = explainerRepository;
    }

    @Cacheable(value = "explainers", key = "'all'")
    @Override
    public List<ExplainerResponseDTO> getAllExplainers() {
        return explainerRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "explainers", key = "#id")
    @Override
    public Optional<ExplainerResponseDTO> getExplainerById(Long id) {
        return explainerRepository.findById(id).map(this::toResponseDTO);
    }

    @CacheEvict(value = "explainers", allEntries = true)
    @Override
    public ExplainerResponseDTO createExplainer(ExplainerRequestDTO explainerRequestDTO) {
        Optional<Explainer> existingByName = explainerRepository.findByName(explainerRequestDTO.getName());
        if (existingByName.isPresent()) {
            throw new ExplainerAlreadyExistsException("Explainer with name '" + explainerRequestDTO.getName() + "' already exists");
        }
        Optional<Explainer> existingByEmail = explainerRepository.findExplainerByEmail(explainerRequestDTO.getEmail());
        if (existingByEmail.isPresent()) {
            throw new ExplainerAlreadyExistsException("Explainer with email '" + explainerRequestDTO.getEmail() + "' already exists");
        }

        Explainer newExplainer = new Explainer();
        newExplainer.setName(explainerRequestDTO.getName());
        newExplainer.setEmail(explainerRequestDTO.getEmail());
        Explainer saved = explainerRepository.save(newExplainer);
        return toResponseDTO(saved);
    }

    @CacheEvict(value = "explainers", allEntries = true)
    @Override
    public ExplainerResponseDTO updateExplainer(Long id, ExplainerRequestDTO explainerRequestDTO) {
        Optional<Explainer> optionalExplainer = Optional.of(explainerRepository.findById(id)
                .orElseThrow(() -> new ExplainerNotFoundException("Explainer not found with id " + id)));
        Explainer explainer = optionalExplainer.get();
        explainer.setName(explainerRequestDTO.getName());
        explainer.setEmail(explainerRequestDTO.getEmail());
        Explainer saved = explainerRepository.save(explainer);
        return toResponseDTO(saved);
    }


    @CacheEvict(value = "explainers", allEntries = true)
    @Override
    public boolean deleteExplainer(Long id) {
        Optional<Explainer> optionalExplainer = explainerRepository.findById(id);
        if (optionalExplainer.isPresent()) {
            explainerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Explainer> getById(long id) {
        return explainerRepository.findById(id);
    }

    public Optional<Explainer> findExplainerByName(String name) {
        return Optional.of(explainerRepository.findByName(name)
                .orElseThrow(() -> new ExplainerNotFoundException("Explainer not found with name " + name)));
    }

    private ExplainerResponseDTO toResponseDTO(Explainer explainer) {
        return ExplainerResponseDTO.builder()
                .id(explainer.getId())
                .name(explainer.getName())
                .email(explainer.getEmail())
                .createdAt(explainer.getCreatedAt())
                .updatedAt(explainer.getUpdatedAt())
                .build();
    }
}
