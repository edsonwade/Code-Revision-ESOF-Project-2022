package ufp.esof.project.services;


import ufp.esof.project.dto.CollegeRequestDTO;
import ufp.esof.project.dto.college.CollegeResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CollegeServiceInterface {

    List<CollegeResponseDTO> getAllColleges();

    Optional<CollegeResponseDTO> getCollegeById(Long id);

    Optional<CollegeResponseDTO> createCollege(CollegeRequestDTO collegeRequest);

    Optional<CollegeResponseDTO> updateCollege(Long id, CollegeRequestDTO collegeRequest);

    boolean deleteCollege(Long id);
}
