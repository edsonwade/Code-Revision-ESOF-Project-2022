package ufp.esof.project.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.availability.AvailabilityRequestDTO;
import ufp.esof.project.dto.availability.AvailabilityResponseDTO;
import ufp.esof.project.exception.ExplainerNotFoundException;
import ufp.esof.project.exception.availabilityexception.AvailabilityNotFoundException;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.AvailabilityRepository;
import ufp.esof.project.repository.ExplainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ExplainerRepository explainerRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, ExplainerRepository explainerRepository) {
        this.availabilityRepository = availabilityRepository;
        this.explainerRepository = explainerRepository;
    }


    @Override
    public List<AvailabilityResponseDTO> findAllAvailabilities() {
        return this.availabilityRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AvailabilityResponseDTO> findAvailabilityById(Long id) {
        var availability = Optional.of(this.availabilityRepository.findByAvailabilityById(id)
                .orElseThrow(() -> new AvailabilityNotFoundException("Availability not found")));
        return availability.map(this::toResponseDTO);
    }

    @Override
    public AvailabilityResponseDTO createAvailability(AvailabilityRequestDTO availabilityRequestDTO) {
        Availability availability = new Availability();
        availability.setStart(availabilityRequestDTO.getStartTime());
        availability.setEnd(availabilityRequestDTO.getEndTime());

        Optional<Explainer> optionalExplainer = this.explainerRepository.findById(availabilityRequestDTO.getExplainerId());
        if (optionalExplainer.isEmpty()) {
            throw new ExplainerNotFoundException("Explainer not found");
        }
        availability.setExplainer(optionalExplainer.get());

        Availability savedAvailability = this.availabilityRepository.save(availability);
        return this.toResponseDTO(savedAvailability);
    }

    @Override
    public AvailabilityResponseDTO updateAvailability(Long id, AvailabilityRequestDTO availabilityRequestDTO) {
        var availability = Optional.of(this.availabilityRepository.findByAvailabilityById(id)
                .orElseThrow(() -> new AvailabilityNotFoundException("Availability not found")));

        var existingAvailability = availability.get();

        Optional<Explainer> optionalExplainer = this.explainerRepository.findById(availabilityRequestDTO.getExplainerId());
        if (optionalExplainer.isEmpty()) {
            throw new ExplainerNotFoundException("Explainer not found");
        }

        existingAvailability.setExplainer(optionalExplainer.get());
        existingAvailability.setStart(availabilityRequestDTO.getStartTime());
        existingAvailability.setEnd(availabilityRequestDTO.getEndTime());

        Availability savedAvailability = this.availabilityRepository.save(existingAvailability);
        return this.toResponseDTO(savedAvailability);
    }

    @Override
    public boolean deleteByAvailabilityId(Long id) {
        Optional<Availability> optionalAvailability = this.availabilityRepository.findById(id);
        if (optionalAvailability.isPresent()) {
            this.availabilityRepository.deleteById(id);
            return true;
        }
        throw new AvailabilityNotFoundException("Availability with id " + id + " not found");
    }

    private AvailabilityResponseDTO toResponseDTO(Availability availability) {
        return AvailabilityResponseDTO.builder()
                .id(availability.getId())
                .explainerId(availability.getExplainer() != null ? availability.getExplainer().getId() : null)
                .explainerName(availability.getExplainer() != null ? availability.getExplainer().getName() : null)
                .startTime(availability.getStart())
                .endTime(availability.getEnd())
                .createdAt(availability.getCreatedAt())
                .updatedAt(availability.getUpdatedAt())
                .build();
    }
}
