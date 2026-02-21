package ufp.esof.project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ufp.esof.project.exception.appointmentexception.InvalidAppointmentException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.services.AppointmentServiceImpl;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
@DisplayName("AppointmentController Tests")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentServiceImpl appointmentService;

    private Appointment testAppointment;

    @BeforeEach
    void setUp() {
        testAppointment = new Appointment();
        testAppointment.setId(1L);
    }

    @Test
    @DisplayName("GET /api/v1/appointment should return all appointments")
    void testGetAllAppointments() throws Exception {
        when(appointmentService.findAll()).thenReturn(List.of(testAppointment));

        mockMvc.perform(get("/api/v1/appointment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

        verify(appointmentService).findAll();
    }

    @Test
    @DisplayName("GET /api/v1/appointment/{id} should return appointment when found")
    void testGetAppointmentByIdSuccess() throws Exception {
        when(appointmentService.findAppointmentById(1L)).thenReturn(testAppointment);

        mockMvc.perform(get("/api/v1/appointment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(appointmentService).findAppointmentById(1L);
    }

    @Test
    @DisplayName("GET /api/v1/appointment/{id} should return 500 when appointment not found")
    void testGetAppointmentByIdNotFound() throws Exception {
        when(appointmentService.findAppointmentById(999L))
                .thenThrow(new InvalidAppointmentException("not found"));

        mockMvc.perform(get("/api/v1/appointment/999"))
                .andExpect(status().isInternalServerError());
    }
}

