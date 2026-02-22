package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.security.JwtAuthenticationFilter;
import ufp.esof.project.security.JwtTokenProvider;
import ufp.esof.project.security.RateLimitFilter;
import ufp.esof.project.services.CollegeService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollegeController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CollegeController Tests")
@SuppressWarnings("all")
class CollegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollegeService collegeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("GET /colleges/{id} returns not found when not found")
    void testGetCollegeByIdNotFound() throws Exception {
        when(collegeService.getCollegeById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/colleges/999"))
                .andExpect(status().isNotFound());
    }
}

