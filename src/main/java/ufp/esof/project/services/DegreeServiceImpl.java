package ufp.esof.project.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.degree.DegreeRequestDTO;
import ufp.esof.project.dto.degree.DegreeResponseDTO;
import ufp.esof.project.exception.DegreeAlreadyExistsException;
import ufp.esof.project.exception.DegreeNotFoundException;
import ufp.esof.project.exception.college_exception.CollegeNotFoundException;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CollegeRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DegreeServiceImpl implements DegreeService {

    private final DegreeRepository degreeRepository;
    private final CollegeRepository CollegeRepository;


    public DegreeServiceImpl(DegreeRepository degreeRepository, CollegeRepository collegeRepository) {
        this.degreeRepository = degreeRepository;
        CollegeRepository = collegeRepository;
    }

    @Override
    public List<DegreeResponseDTO> getAllDegrees() {
        List<Degree> degrees = (List<Degree>) this.degreeRepository.findAll();
        return degrees.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DegreeResponseDTO> getDegreeById(Long id) {
        return this.degreeRepository.findById(id).map(this::toResponseDTO);
    }

    @Override
    public Optional<DegreeResponseDTO> getDegreeByName(String name) {
        Optional<Degree> degree = this.degreeRepository.findByName(name);
        if (degree.isEmpty()) {
            throw new DegreeNotFoundException("Degree with name " + name + " not found");
        }
        return this.degreeRepository.findByName(name)
                .map(this::toResponseDTO);
    }

    @Override
    public DegreeResponseDTO createDegree(DegreeRequestDTO degreeRequestDTO) {
        // Check if degree with same name already exists
        Optional<Degree> existingDegree = this.degreeRepository.findByName(degreeRequestDTO.getName());
        if (existingDegree.isPresent()) {
            throw new DegreeAlreadyExistsException(degreeRequestDTO.getName());
        }

        Degree newDegree = new Degree();
        newDegree.setName(degreeRequestDTO.getName());

        // Handle college association if provided
        if (degreeRequestDTO.getCollegeId() != null) {
            Optional<College> optionalCollege = this.CollegeRepository.findById(degreeRequestDTO.getCollegeId());
            if (optionalCollege.isPresent()) {
                newDegree.setCollege(optionalCollege.get());
            } else {
                throw new CollegeNotFoundException("College with id " + degreeRequestDTO.getCollegeId() + " not found");
            }
        }

        Degree savedDegree = this.degreeRepository.save(newDegree);
        return toResponseDTO(savedDegree);
    }

    @Override
    public DegreeResponseDTO updateDegree(Long id, DegreeRequestDTO degreeRequestDTO) {
        Optional<Degree> optionalDegree = this.degreeRepository.findById(id);
        if (optionalDegree.isEmpty()) {
            throw new DegreeNotFoundException(id);
        }

        Degree currentDegree = optionalDegree.get();

        // Check if another degree with the same name already exists
        Optional<Degree> existingDegree = this.degreeRepository.findByName(degreeRequestDTO.getName());
        if (existingDegree.isPresent() && !existingDegree.get().getId().equals(id)) {
            throw new DegreeAlreadyExistsException(degreeRequestDTO.getName());
        }

        currentDegree.setName(degreeRequestDTO.getName());

        if (degreeRequestDTO.getCollegeId() != null) {
            Optional<College> optionalCollege = this.CollegeRepository.findById(degreeRequestDTO.getCollegeId());
            if (optionalCollege.isEmpty()) {
                throw new CollegeNotFoundException("College with id " + degreeRequestDTO.getCollegeId() + " not found");
            }
            currentDegree.setCollege(optionalCollege.get());
        }

        Degree savedDegree = this.degreeRepository.save(currentDegree);
        return toResponseDTO(savedDegree);
    }

    @Override
    public boolean deleteDegree(Long id) {
        Optional<Degree> optionalDegree = this.degreeRepository.findById(id);
        if (optionalDegree.isPresent()) {
            this.degreeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Degree> findById(Long id) {
        return this.degreeRepository.findById(id);
    }

    public Optional<Degree> findByName(String name) {
        return this.degreeRepository.findByName(name);
    }

    private DegreeResponseDTO toResponseDTO(Degree degree) {
        Set<DegreeResponseDTO.CourseResponseDTO> courseDTOs = degree.getCourses().stream()
                .map(course -> DegreeResponseDTO.CourseResponseDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .build())
                .collect(Collectors.toSet());

        return DegreeResponseDTO.builder()
                .id(degree.getId())
                .name(degree.getName())
                .collegeId(degree.getCollege() != null ? degree.getCollege().getId() : null)
                .collegeName(degree.getCollege() != null ? degree.getCollege().getName() : null)
                .courses(courseDTOs)
                .createdAt(degree.getCreatedAt())
                .updatedAt(degree.getUpdatedAt())
                .build();
    }
}
