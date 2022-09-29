package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.models.Degree;
import ufp.esof.project.services.DegreeService;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1/degree")
@RequiredArgsConstructor
public class DegreeController {

    private final DegreeService degreeService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Degree>> getAllDegrees() {
        return ResponseEntity.ok(this.degreeService.findAllDegrees());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Degree> getDegreeById(@PathVariable("id") Long id) {
        Optional<Degree> optionalDegree = this.degreeService.findById(id);
        if (optionalDegree.isPresent())
            return ResponseEntity.ok(optionalDegree.get());
        throw new InvalidDegreeException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Degree> createDegree(@RequestBody Degree degree) {
        Optional<Degree> degreeOptional = this.degreeService.createDegree(degree);
        if (degreeOptional.isPresent())
            return ResponseEntity.ok(degreeOptional.get());
        throw new DegreeNotCreatedException(degree.getName());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Degree> editDegree(@PathVariable("id") Long id, @RequestBody Degree degree) {
        Optional<Degree> degreeOptional = this.degreeService.findById(id);
        if (degreeOptional.isEmpty())
            throw new InvalidDegreeException(id);

        degreeOptional = this.degreeService.editDegree(degreeOptional.get(), degree, id);
        if (degreeOptional.isPresent())
            return ResponseEntity.ok(degreeOptional.get());

        throw new DegreeNotEditedException(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteDegree(@PathVariable("id") Long id) {
        boolean res = this.degreeService.deleteById(id);
        Optional<Degree> degreeOptional = this.degreeService.findById(id);
        if (degreeOptional.isPresent())
            throw new DegreeNotDeletedException(degreeOptional.get().getName());

        if (res)
            return ResponseEntity.ok("Degree deleted successfully!");
        else
            throw new InvalidDegreeException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Degree not created")
    public static class DegreeNotCreatedException extends RuntimeException {
        public DegreeNotCreatedException(String name) {
            super("The degree with name \"" + name + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Degree not deleted")
    public static class DegreeNotDeletedException extends RuntimeException {
        public DegreeNotDeletedException(String name) {
            super("The degree with name \"" + name + "\" was not deleted");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Degree")
    public static class InvalidDegreeException extends RuntimeException {
        public InvalidDegreeException(Long id) {
            super("The degree with id \"" + id + "\" does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Degree not edited")
    public static class DegreeNotEditedException extends RuntimeException {
        public DegreeNotEditedException(Long id) {
            super("The degree with id \"" + id + "\" was not edited");
        }
    }
}
