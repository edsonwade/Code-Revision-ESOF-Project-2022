package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.models.Student;
import ufp.esof.project.services.StudentService;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@DisplayName("StudentController Tests")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    @DisplayName("GET /api/v1/students should return list of students")
    void testGetAllStudents() throws Exception {
        Student s = new Student("Test Student");
        s.setId(1L);
        when(studentService.getAllStudents()).thenReturn(List.of(s));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/students/{id} should return student when found")
    void testGetStudentByIdFound() throws Exception {
        Student s = new Student("Test Student");
        s.setId(1L);
        when(studentService.getStudentById(1L)).thenReturn(s);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/v1/students/{id} should return 200 when deleted")
    void testDeleteStudentSuccess() throws Exception {
        when(studentService.deleteStudentById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deleted")));
    }
}

