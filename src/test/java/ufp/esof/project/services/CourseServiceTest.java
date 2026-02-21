package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Degree;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.CourseRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseService Tests")
class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private DegreeService degreeService;

    @Mock
    private ExplainerService explainerService;

    @InjectMocks
    private CourseService courseService;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        testCourse = new Course("Test Course");
        testCourse.setId(1L);
    }

    @Test
    @DisplayName("Should find all courses")
    void testFindAllCourses() {
        when(courseRepo.findAll()).thenReturn(List.of(testCourse));
        Iterable<Course> all = courseService.findAllCourses();
        assertThat(all).isNotEmpty();
        verify(courseRepo).findAll();
    }

    @Test
    @DisplayName("Should find course by id")
    void testFindById() {
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        Optional<Course> opt = courseService.findById(1L);
        assertThat(opt).isPresent();
    }

    @Test
    @DisplayName("Should delete course by id when exists")
    void testDeleteByIdExists() {
        when(courseRepo.findById(1L)).thenReturn(Optional.of(testCourse));
        boolean deleted = courseService.deleteById(1L);
        assertThat(deleted).isTrue();
        verify(courseRepo).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete course by id when not exists")
    void testDeleteByIdNotExists() {
        when(courseRepo.findById(999L)).thenReturn(Optional.empty());
        boolean deleted = courseService.deleteById(999L);
        assertThat(deleted).isFalse();
    }

    // Additional create/edit tests require mocking degree and explainer logic
}
