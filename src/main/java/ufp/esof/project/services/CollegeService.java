package ufp.esof.project.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.CollegeRequestDTO;
import ufp.esof.project.dto.college.CollegeResponseDTO;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CollegeRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class CollegeService implements CollegeServiceInterface {

    private final CollegeRepository collegeRepository;
    private final DegreeRepository degreeRepository;

    public CollegeService(CollegeRepository collegeRepository, DegreeRepository degreeRepository) {
        this.collegeRepository = collegeRepository;
        this.degreeRepository = degreeRepository;
    }

    @Override
    @Cacheable(value = "colleges", key = "'all'")
    public List<CollegeResponseDTO> getAllColleges() {
        Iterable<College> colleges = collegeRepository.findAll();
        return StreamSupport.stream(colleges.spliterator(), false)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "colleges", key = "#id")
    public Optional<CollegeResponseDTO> getCollegeById(Long id) {
        return collegeRepository.findById(id).map(this::toResponseDTO);
    }

    @Override
    @CacheEvict(value = "colleges", allEntries = true)
    public Optional<CollegeResponseDTO> createCollege(CollegeRequestDTO collegeRequest) {
        if (collegeRepository.findByName(collegeRequest.getName()).isPresent()) {
            return Optional.empty();
        }

        College college = new College();
        college.setName(collegeRequest.getName());
        college.setOrganizationId(1L);

        if (collegeRequest.getDegreeIds() != null && !collegeRequest.getDegreeIds().isEmpty()) {
            validateDegree(collegeRequest, college);
        }

        College saved = collegeRepository.save(college);
        return Optional.of(toResponseDTO(saved));
    }



    @Override
    @CacheEvict(value = "colleges", allEntries = true)
    public Optional<CollegeResponseDTO> updateCollege(Long id, CollegeRequestDTO collegeRequest) {
        Optional<College> existingOpt = collegeRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }

        College college = existingOpt.get();

        Optional<College> byName = collegeRepository.findByName(collegeRequest.getName());
        if (byName.isPresent() && !byName.get().getId().equals(id)) {
            return Optional.empty();
        }
        college.setName(collegeRequest.getName());

        if (collegeRequest.getDegreeIds() != null) {
            validateDegree(collegeRequest, college);
        }

        College saved = collegeRepository.save(college);
        return Optional.of(toResponseDTO(saved));
    }

    @Override
    @CacheEvict(value = "colleges", allEntries = true)
    public boolean deleteCollege(Long id) {
        Optional<College> collegeOpt = collegeRepository.findById(id);
        if (collegeOpt.isEmpty()) {
            return false;
        }
        College college = collegeOpt.get();
        if (!college.getDegrees().isEmpty()) {
            return false;
        }
        collegeRepository.deleteById(id);
        return true;
    }

    private CollegeResponseDTO toResponseDTO(College college) {
        Set<CollegeResponseDTO.DegreeResponseDTO> degreeDTOs = college.getDegrees().stream()
                .map(degree -> CollegeResponseDTO.DegreeResponseDTO.builder()
                        .id(degree.getId())
                        .name(degree.getName())
                        .build())
                .collect(Collectors.toSet());

        return CollegeResponseDTO.builder()
                .id(college.getId())
                .name(college.getName())
                .organizationId(college.getOrganizationId())
                .degrees(degreeDTOs)
                .createdAt(college.getCreatedAt())
                .updatedAt(college.getUpdatedAt())
                .build();
    }
    private void validateDegree(CollegeRequestDTO collegeRequest, College college) {
        Set<Degree> degrees = new HashSet<>();
        for (Long degreeId : collegeRequest.getDegreeIds()) {
            Optional<Degree> degreeOpt = degreeRepository.findById(degreeId);
            degreeOpt.ifPresent(degree -> {
                degree.setCollege(college);
                degrees.add(degree);
            });
        }
        college.setDegrees(degrees);
    }
}
