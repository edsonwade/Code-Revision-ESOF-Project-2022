package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.dto.student.StudentResponseDTO;
import ufp.esof.project.models.Student;
import ufp.esof.project.security.JwtAuthenticationFilter;
import ufp.esof.project.security.JwtTokenProvider;
import ufp.esof.project.security.RateLimitFilter;
import ufp.esof.project.services.StudentService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("StudentController Tests")
@SuppressWarnings("all")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;


    @Test
    @DisplayName("GET /students should return students when found")
    void testGetAllStudentsFound() throws Exception {
        List<Student> students = Collections.singletonList(new Student("John"));
        students.get(0).setId(1L);
        students.get(0).setName("John");
        students.get(0).setEmail("john.doe@example.com");
        students.get(0).setCreatedAt(LocalDateTime.now());
        students.get(0).setUpdatedAt(LocalDateTime.now());

        List<StudentResponseDTO> studentResponseDTOS = students.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

        when(studentService.getAllStudents()).thenReturn(studentResponseDTOS);


        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    @DisplayName("GET /students should return empty list when no students")
    void testGetAllStudentsNotFound() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of());

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /students/{id} should return error when student not found")
    void testGetStudentByIdNotFound() throws Exception {
        when(studentService.getStudentById(999L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /students/{id} should return student when found")
    void testGetStudentByIdFound() throws Exception {
        Student s = new Student("Test Student");
        s.setId(1L);
        s.setName("Test Student");
        s.setEmail("test.student@example.com");

        var response = toResponseDTO(s);

        when(studentService.getStudentById(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /students/{id} should return 200 when deleted")
    void testDeleteStudentSuccess() throws Exception {
        when(studentService.deleteStudentById(1L)).thenReturn(true);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("deleted")));
    }


    private StudentResponseDTO toResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}

