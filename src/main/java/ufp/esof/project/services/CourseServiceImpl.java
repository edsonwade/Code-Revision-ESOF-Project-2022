package ufp.esof.project.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.course.CourseRequestDTO;
import ufp.esof.project.dto.course.CourseResponseDTO;
import ufp.esof.project.exception.CourseAlreadyExistsException;
import ufp.esof.project.exception.CourseNotFoundException;
import ufp.esof.project.exception.DegreeNotFoundException;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CourseRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final DegreeRepository degreeRepository;

    public CourseServiceImpl(CourseRepository courseRepository, DegreeRepository degreeRepository) {
        this.courseRepository = courseRepository;
        this.degreeRepository = degreeRepository;
    }

    @Cacheable(value = "courses", key = "'all'")
    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = (List<Course>) this.courseRepository.findAll();
        return courses.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "courses", key = "#id")
    @Override
    public Optional<CourseResponseDTO> getCourseById(Long id) {
        return this.courseRepository.findById(id).map(this::toResponseDTO);
    }

    @CacheEvict(value = "courses", allEntries = true)
    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO) {
        Optional<Course> existingCourse = this.courseRepository.findByName(courseRequestDTO.getName());
        if (existingCourse.isPresent()) {
            throw new CourseAlreadyExistsException(courseRequestDTO.getName());
        }

        Course newCourse = new Course();
        newCourse.setName(courseRequestDTO.getName());

        if (courseRequestDTO.getDegreeId() != null) {
            Optional<Degree> optionalDegree = this.degreeRepository.findById(courseRequestDTO.getDegreeId());
            if (optionalDegree.isPresent()) {
                newCourse.setDegree(optionalDegree.get());
            } else {
                throw new DegreeNotFoundException("Degree with id " + courseRequestDTO.getDegreeId() + " not found");
            }
        }

        Course savedCourse = this.courseRepository.save(newCourse);
        return toResponseDTO(savedCourse);
    }

    @CacheEvict(value = "courses", allEntries = true)
    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Optional<Course> optionalCourse = this.courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(id);
        }

        Course currentCourse = optionalCourse.get();

        Optional<Course> existingCourse = this.courseRepository.findByName(courseRequestDTO.getName());
        if (existingCourse.isPresent() && !existingCourse.get().getId().equals(id)) {
            throw new CourseAlreadyExistsException(courseRequestDTO.getName());
        }

        currentCourse.setName(courseRequestDTO.getName());

        if (courseRequestDTO.getDegreeId() != null) {
            Optional<Degree> optionalDegree = this.degreeRepository.findById(courseRequestDTO.getDegreeId());
            if (optionalDegree.isEmpty()) {
                throw new DegreeNotFoundException("Degree with id " + courseRequestDTO.getDegreeId() + " not found");
            }
            currentCourse.setDegree(optionalDegree.get());
        }

        Course savedCourse = this.courseRepository.save(currentCourse);
        return toResponseDTO(savedCourse);
    }

    @CacheEvict(value = "courses", allEntries = true)
    @Override
    public boolean deleteCourse(Long id) {
        Optional<Course> optionalCourse = this.courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return false;
        }
        this.courseRepository.deleteById(id);
        return true;
    }

    private CourseResponseDTO toResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .degreeId(course.getDegree() != null ? course.getDegree().getId() : null)
                .degreeName(course.getDegree() != null ? course.getDegree().getName() : null)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
