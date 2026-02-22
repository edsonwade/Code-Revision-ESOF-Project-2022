package ufp.esof.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufp.esof.project.dto.course.CourseRequestDTO;
import ufp.esof.project.dto.course.CourseResponseDTO;
import ufp.esof.project.exception.CourseNotFoundException;
import ufp.esof.project.services.CourseService;

import java.util.List;

@RestController
@RequestMapping(path = "/courses")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.getAllCourses());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        return this.courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        this.courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully!");
    }

    @PostMapping("/create")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO createdCourse = this.courseService.createCourse(courseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id,
                                                          @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO updatedCourse = this.courseService.updateCourse(id, courseRequestDTO);
        return ResponseEntity.ok(updatedCourse);
    }
}
