package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.dto.AvailabilityDto;
import ufp.esof.project.exception.availabilityexception.AvailabilityNotCreatedException;
import ufp.esof.project.exception.availabilityexception.AvailabilityNotDeletedException;
import ufp.esof.project.exception.availabilityexception.AvailabilityNotEditedException;
import ufp.esof.project.models.Availability;
import ufp.esof.project.services.AvailabilityService;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;


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

        throw new AvailabilityNotDeletedException("Availability not edited" + id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> createAvailability(@RequestBody AvailabilityDto availability) {
        Optional<Availability> optionalAvailability = this.availabilityService.createAvailability(availability);
        if (optionalAvailability.isPresent())
            return ResponseEntity.ok(optionalAvailability.get());
        throw new AvailabilityNotCreatedException(" not created " + availability.getId());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> editAvailability(@PathVariable("id") Long id, @RequestBody Availability availability) {
        Optional<Availability> optionalAvailability = this.availabilityService.findById(id);
        if (optionalAvailability.isEmpty())
            throw new InvalidAvailabilityException(id);

        optionalAvailability = this.availabilityService.editAvailability(availability, id);
        if (optionalAvailability.isPresent())
            return ResponseEntity.ok(optionalAvailability.get());

        throw new AvailabilityNotEditedException("Availability not edited " + id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Availability")
    public static class InvalidAvailabilityException extends RuntimeException {
        public InvalidAvailabilityException(Long id) {
            super("The availability with id " + id + " does not exist");
        }
    }


}
