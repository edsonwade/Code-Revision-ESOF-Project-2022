package ufp.esof.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.services.AvailabilityService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvailabilityController.class)
@DisplayName("AvailabilityController Tests")
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    @DisplayName("GET /api/v1/availability returns bad request when not found")
    void testGetAvailabilityByIdNotFound() throws Exception {
        when(availabilityService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/availability/999"))
                .andExpect(status().isBadRequest());
    }
}

