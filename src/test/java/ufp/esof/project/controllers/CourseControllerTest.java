package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.services.CourseService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@DisplayName("CourseController Tests")
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    @DisplayName("GET /api/v1/course should return empty list")
    void testGetAllCourses() throws Exception {
        when(courseService.findAllCourses()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/course"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/course/{id} returns 404 when not found")
    void testGetCourseByIdNotFound() throws Exception {
        when(courseService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/course/999"))
                .andExpect(status().isNotFound());
    }
}
