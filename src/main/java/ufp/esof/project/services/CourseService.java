package ufp.esof.project.services;

import ufp.esof.project.dto.course.CourseRequestDTO;
import ufp.esof.project.dto.course.CourseResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseResponseDTO> getAllCourses();
    Optional<CourseResponseDTO> getCourseById(Long id);
    CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO);
    CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO);
    boolean deleteCourse(Long id);
}
