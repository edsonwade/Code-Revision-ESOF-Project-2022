package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.services.DegreeService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DegreeController.class)
@DisplayName("DegreeController Tests")
class DegreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DegreeService degreeService;

    @Test
    @DisplayName("GET /api/v1/degree returns bad request when not found")
    void testGetDegreeByIdNotFound() throws Exception {
        when(degreeService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/degree/999"))
                .andExpect(status().isBadRequest());
    }
}

