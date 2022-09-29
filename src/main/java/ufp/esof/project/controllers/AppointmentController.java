package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.exception.appointmentexception.InvalidAppointmentException;
import ufp.esof.project.models.Appointment;
import ufp.esof.project.services.AppointmentServiceImpl;

@RestController
@RequestMapping(path = "/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentServiceImpl appointmentService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Appointment>> getAllAppointment() {
        return ResponseEntity.ok(this.appointmentService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.appointmentService.findAppointmentById(id));

    }

    // todo : need to fix this error relate to appointment dto class
//    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Appointment> savePaciente(@RequestBody AppointmentDto appointment) {
//        return appointmentService.saveAppointment(appointment); "Appointment not created"
//
//    }
//
//    @PutMapping(value = "/update/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Appointment> updateAppointment(@RequestBody AppointmentDto appointments,
//                                                         @PathVariable("id") Long id) {
//        Optional<Appointment> appointmentOptional = appointmentService.findById(id);
//        if (appointmentOptional.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        appointments.setId(id);
//
//        appointmentService.save(appointments);
//
//        return ResponseEntity.ok().build();
//    "The Appointment with id \"" + id + "\" was not edited"
//    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Long id) {
        var appointmentOptional = appointmentService.findAppointmentById(id);
        if (appointmentOptional.getId().equals(id)) {
            this.appointmentService.deleteById(id);
            return ResponseEntity.ok("Appointment Deleted Successfully");
        }
        throw new InvalidAppointmentException("The appointment with id \"" + id + "\" does not exist");
    }


}

