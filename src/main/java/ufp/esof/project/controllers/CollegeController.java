package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.models.College;
import ufp.esof.project.services.CollegeService;

import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<College>> getAllColleges() {
        return ResponseEntity.ok(this.collegeService.getAllColleges());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<College> getCollegeById(@PathVariable("id") Long id) {
        Optional<College> collegeOptional = this.collegeService.findById(id);
        if (collegeOptional.isPresent())
            return ResponseEntity.ok(collegeOptional.get());
        throw new InvalidCollegeException(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCollege(@PathVariable("id") Long id) {
        Optional<College> optionalCollege = this.collegeService.findById(id);
        if (optionalCollege.isEmpty())
            throw new InvalidCollegeException(id);

        if (this.collegeService.deleteById(id))
            return ResponseEntity.ok("College deleted successfully!");
        throw new CollegeNotEmptyException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<College> createCollege(@RequestBody College college) {
        Optional<College> optionalCollege = this.collegeService.createCollege(college);
        if (optionalCollege.isPresent())
            return ResponseEntity.ok(optionalCollege.get());
        throw new CollegeNotCreatedException(college.getName());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<College> editCollege(@PathVariable("id") Long id, @RequestBody College college) {
        Optional<College> optionalCollege = this.collegeService.findById(id);
        if (optionalCollege.isEmpty())
            throw new InvalidCollegeException(id);

        optionalCollege = this.collegeService.editCollege(optionalCollege.get(), college, id);
        if (optionalCollege.isPresent())
            return ResponseEntity.ok(optionalCollege.get());

        throw new CollegeNotEditedException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid College")
    public static class InvalidCollegeException extends RuntimeException {
        public InvalidCollegeException(Long id) {
            super("The college with id " + id + " does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "College not created")
    public static class CollegeNotCreatedException extends RuntimeException {
        public CollegeNotCreatedException(String name) {
            super("The college with name \"" + name + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "College not edited")
    public static class CollegeNotEditedException extends RuntimeException {
        public CollegeNotEditedException(Long id) {
            super("The college with id \"" + id + "\" was not edited");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "College not empty")
    public static class CollegeNotEmptyException extends RuntimeException {
        public CollegeNotEmptyException(Long id) {
            super("The college with id \"" + id + "\" has degrees associated. Please delete the degrees before deleting the college.");
        }
    }
}
