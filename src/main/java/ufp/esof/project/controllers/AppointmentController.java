package ufp.esof.project.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.models.Appointment;

import ufp.esof.project.models.Availability;
import ufp.esof.project.services.AppointmentService;

import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Appointment>> getAllAppointment() {
        return ResponseEntity.ok(this.appointmentService.findAll());
    }

    // mapeando o id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Appointment getById(@PathVariable("id") Long id) {
        Optional<Appointment> appointmentOptional = appointmentService.findById(id);


        return appointmentOptional.orElse(null);
    }


    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> savePaciente(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);

    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment, @PathVariable("id") Long id) {
        Optional<Appointment> appointmentOptional = appointmentService.findById(id);
        if (!appointmentOptional.isEmpty()) return ResponseEntity.notFound().build();
        appointment.setId(id);

        appointmentService.save(appointment);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Long id) {
        boolean res = this.appointmentService.deleteById(id);
        Optional<Appointment> appointmentOptional = this.appointmentService.findById(id);
        if (appointmentOptional.isPresent())


            if (res)
                return ResponseEntity.ok("Appointment Deleted Successfully");
        throw new InvalidAppointmentException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Appointment not created")
    public static class AppointmentNotCreatedException extends RuntimeException {
        public AppointmentNotCreatedException(Long name) {
            super("The appointment  with explainer name \"" + name + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Appointment")
    public static class InvalidAppointmentException extends RuntimeException {
        public InvalidAppointmentException(Long id) {
            super("The appointment with id \"" + id + "\" does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Appointment not edited")
    public static class AppointmentNotEditedException extends RuntimeException {
        public AppointmentNotEditedException(Long id) {
            super("The Appointment with id \"" + id + "\" was not edited");
        }
    }
}

