package ufp.esof.project.services;

import ufp.esof.project.dto.degree.DegreeRequestDTO;
import ufp.esof.project.dto.degree.DegreeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface DegreeService {
    List<DegreeResponseDTO> getAllDegrees();

    Optional<DegreeResponseDTO> getDegreeById(Long id);

    DegreeResponseDTO createDegree(DegreeRequestDTO degreeRequestDTO);

    DegreeResponseDTO updateDegree(Long id, DegreeRequestDTO degreeRequestDTO);

    boolean deleteDegree(Long id);

    Optional<DegreeResponseDTO> getDegreeByName(String degreeName);
}
