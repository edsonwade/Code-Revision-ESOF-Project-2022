package ufp.esof.project.services;

import ufp.esof.project.dto.availability.AvailabilityRequestDTO;
import ufp.esof.project.dto.availability.AvailabilityResponseDTO;

import java.util.List;
import java.util.Optional;


public interface AvailabilityService {
    List<AvailabilityResponseDTO> findAllAvailabilities();

    Optional<AvailabilityResponseDTO> findAvailabilityById(Long id);

    boolean deleteByAvailabilityId(Long id);

    AvailabilityResponseDTO createAvailability(AvailabilityRequestDTO availabilityRequestDTO);

    AvailabilityResponseDTO updateAvailability(Long id, AvailabilityRequestDTO availabilityRequestDTO);
}
