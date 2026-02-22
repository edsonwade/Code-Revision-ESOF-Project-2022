package ufp.esof.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ufp.esof.project.models.Course;
import ufp.esof.project.repository.CourseRepository;
import ufp.esof.project.repository.DegreeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseService Tests")
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private DegreeRepository degreeRepository;

    private CourseServiceImpl courseService;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository, degreeRepository);
        testCourse = new Course("Test Course");
        testCourse.setId(1L);
    }

    @Test
    @DisplayName("Should find all courses")
    void testFindAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(testCourse));
        var findAllCourses = courseService.getAllCourses();
        assertThat(findAllCourses).isNotEmpty();
        assertThat(findAllCourses.get(0).getId()).isEqualTo(1L);
        assertThat(findAllCourses.get(0).getName()).isEqualTo("Test Course");

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find course by id")
    void testFindById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        var courseById = courseService.getCourseById(1L);
        assertThat(courseById).isPresent();
        assertThat(courseById.get().getId()).isEqualTo(1L);
        assertThat(courseById.get().getName()).isEqualTo("Test Course");
        verify(courseRepository, atLeastOnce()).findById(1L);
    }

    @Test
    @DisplayName("Should delete course by id when exists")
    void testDeleteByIdExists() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(testCourse));
        boolean deleted = courseService.deleteCourse(1L);
        assertThat(deleted).isTrue();
        verify(courseRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete course by id when not exists")
    void testDeleteByIdNotExists() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());
        boolean deleted = courseService.deleteCourse(999L);
        assertThat(deleted).isFalse();
    }

    // Additional create/edit tests require mocking degree and explainer logic
}
