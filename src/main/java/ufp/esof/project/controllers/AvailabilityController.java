package ufp.esof.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.models.Availability;
import ufp.esof.project.services.AvailabilityService;

import java.util.Optional;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Availability>> getAllAvailabilities() {
        return ResponseEntity.ok(this.availabilityService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable("id") Long id) {
        Optional<Availability> optionalAvailability = this.availabilityService.findById(id);
        if (optionalAvailability.isPresent())
            return ResponseEntity.ok(optionalAvailability.get());
        throw new InvalidAvailabilityException(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAvailability(@PathVariable("id") Long id) {
        Optional<Availability> optionalAvailability = this.availabilityService.findById(id);
        if (optionalAvailability.isEmpty())
            throw new InvalidAvailabilityException(id);

        if (this.availabilityService.deleteById(id))
            return ResponseEntity.ok("Availability deleted successfully!");

        throw new AvailabilityNotDeletedException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> createAvailability(@RequestBody Availability availability) {
        Optional<Availability> optionalAvailability = this.availabilityService.createAvailability(availability);
        if (optionalAvailability.isPresent())
            return ResponseEntity.ok(optionalAvailability.get());
        throw new AvailabilityNotCreatedException(availability.getId());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> editAvailability(@PathVariable("id") Long id, @RequestBody Availability availability) {
        Optional<Availability> optionalAvailability = this.availabilityService.findById(id);
        if (optionalAvailability.isEmpty())
            throw new InvalidAvailabilityException(id);

        optionalAvailability = this.availabilityService.editAvailability(optionalAvailability.get(), availability, id);
        if (optionalAvailability.isPresent())
            return ResponseEntity.ok(optionalAvailability.get());

        throw new AvailabilityNotEditedException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Availability")
    public static class InvalidAvailabilityException extends RuntimeException {
        public InvalidAvailabilityException(Long id) {
            super("The availability with id " + id + " does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Availability not deleted")
    public static class AvailabilityNotDeletedException extends RuntimeException {
        public AvailabilityNotDeletedException(Long id) {
            super("The availability with id \"" + id + "\" was not deleted");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Availability not created")
    public static class AvailabilityNotCreatedException extends RuntimeException {
        public AvailabilityNotCreatedException(Long id) {
            super("The availability with id \"" + id + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Availability not edited")
    public static class AvailabilityNotEditedException extends RuntimeException {
        public AvailabilityNotEditedException(Long id) {
            super("The availability with id \"" + id + "\" was not edited");
        }
    }
}
