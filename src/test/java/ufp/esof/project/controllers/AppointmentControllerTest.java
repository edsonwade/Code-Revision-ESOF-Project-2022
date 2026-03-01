package ufp.esof.project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.exception.appointmentexception.AppointmentNotFoundException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.security.JwtAuthenticationFilter;
import ufp.esof.project.security.JwtTokenProvider;
import ufp.esof.project.security.RateLimitFilter;
import ufp.esof.project.services.AppointmentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ufp.esof.project.services.AppointmentServiceImpl.getAppointmentResponse;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AppointmentController Tests")
@SuppressWarnings("all")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentServiceImpl appointmentService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    private Appointment testAppointment;

    @BeforeEach
    void setUp() {
        testAppointment = new Appointment();
        testAppointment.setId(1L);
    }

    @Test
    @DisplayName("GET /appointments should return all appointments")
    void testGetAllAppointments() throws Exception {;
        List<AppointmentResponse> responses = new ArrayList<>();
        responses.add(toResponseDTO(testAppointment));

        when(appointmentService.getAllAppointments()).thenReturn(responses);

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(appointmentService).getAllAppointments();
    }

    @Test
    @DisplayName("GET /appointments/{id} should return appointment when found")
    void testGetAppointmentByIdSuccess() throws Exception {
        when(appointmentService.getAppointmentById(1L)).thenReturn(Optional.of(toResponseDTO(testAppointment)));

        mockMvc.perform(get("/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(appointmentService).getAppointmentById(1L);
    }

    @Test
    @DisplayName("GET /appointments/{id} should return 404 when appointment not found")
    void testGetAppointmentByIdNotFound() throws Exception {
        when(appointmentService.getAppointmentById(999L))
                .thenThrow(new AppointmentNotFoundException("not found"));
        mockMvc.perform(get("/appointments/999"))
                .andExpect(status().isNotFound());
    }

    private AppointmentResponse toResponseDTO(Appointment appointment) {
        return getAppointmentResponse(appointment);
    }
}

