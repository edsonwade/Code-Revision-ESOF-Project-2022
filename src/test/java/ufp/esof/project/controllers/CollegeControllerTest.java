package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.services.CollegeService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollegeController.class)
@DisplayName("CollegeController Tests")
class CollegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollegeService collegeService;

    @Test
    @DisplayName("GET /api/v1/college/{id} returns not found when not found")
    void testGetCollegeByIdNotFound() throws Exception {
        when(collegeService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/college/999"))
                .andExpect(status().isNotFound());
    }
}

