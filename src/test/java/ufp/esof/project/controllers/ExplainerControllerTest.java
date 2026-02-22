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
import ufp.esof.project.services.ExplainerServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExplainerController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("ExplainerController Tests")
@SuppressWarnings("all")
class ExplainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExplainerServiceImpl explainerServiceImpl;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("GET /explainers/{id} returns empty when not found")
    void testGetExplainerByIdNotFound() throws Exception {
        when(explainerServiceImpl.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/explainers/999"))
                .andExpect(status().isOk());
    }
}

