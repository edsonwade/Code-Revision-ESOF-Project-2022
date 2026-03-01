package ufp.esof.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufp.esof.project.dto.request.CreateAppointmentRequest;
import ufp.esof.project.dto.response.AppointmentResponse;
import ufp.esof.project.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping(path = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointment() {
        return ResponseEntity.ok(this.appointmentService.getAllAppointments());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        return this.appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidAppointmentException("The appointment with id \"" + id + "\" does not exist"));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        return this.appointmentService.createAppointment(request)
                .map(appointment -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(appointment))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        boolean deleted = this.appointmentService.deleteAppointment(id);
        if (deleted) {
            return ResponseEntity.ok("Appointment Deleted Successfully");
        }
        throw new InvalidAppointmentException("The appointment with id \"" + id + "\" does not exist");
    }

    public static class InvalidAppointmentException extends RuntimeException {
        public InvalidAppointmentException(String message) {
            super(message);
        }
    }
}
