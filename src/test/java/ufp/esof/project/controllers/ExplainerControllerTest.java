package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.services.ExplainerServiceImpl;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExplainerController.class)
@DisplayName("ExplainerController Tests")
class ExplainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExplainerServiceImpl explainerServiceImpl;

    @Test
    @DisplayName("GET /api/v1/explainer returns empty set when no explainers")
    void testGetAllExplainersEmpty() throws Exception {
        when(explainerServiceImpl.getFilteredExplainer(any())).thenReturn(Set.of());

        mockMvc.perform(get("/api/v1/explainer"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/explainer/{id} returns 404 when not found")
    void testGetExplainerByIdNotFound() throws Exception {
        when(explainerServiceImpl.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/explainer/999"))
                .andExpect(status().isNotFound());
    }
}

