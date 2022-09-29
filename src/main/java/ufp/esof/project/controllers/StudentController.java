package ufp.esof.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufp.esof.project.models.Student;
import ufp.esof.project.services.StudentService;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        var optionalStudent = this.studentService.findById(id);
        if (optionalStudent.isPresent())
            return ResponseEntity.ok(optionalStudent.get());
        throw new InvalidStudentException(id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        Optional<Student> optionalStudent = this.studentService.findById(id);
        if (optionalStudent.isEmpty())
            throw new InvalidStudentException(id);

        if (this.studentService.deleteById(id))
            return ResponseEntity.ok("Student deleted successfully!");
        throw new StudentNotEmptyException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Optional<Student> optionalStudent = this.studentService.createStudent(student);
        if (optionalStudent.isPresent())
            return ResponseEntity.ok(optionalStudent.get());
        throw new StudentNotCreatedException(student.getName());
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> editStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Optional<Student> optionalStudent = this.studentService.findById(id);
        if (optionalStudent.isEmpty())
            throw new InvalidStudentException(id);

        optionalStudent = this.studentService.editStudent(optionalStudent.get(), student, id);
        if (optionalStudent.isPresent())
            return ResponseEntity.ok(optionalStudent.get());

        throw new StudentNotEditedException(id);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Student")
    public static class InvalidStudentException extends RuntimeException {
        public InvalidStudentException(Long id) {
            super("The student with id " + id + " does not exist");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Student not empty")
    public static class StudentNotEmptyException extends RuntimeException {
        public StudentNotEmptyException(Long id) {
            super("The student with id \"" + id + "\" has appointments associated. Please delete the appointments before deleting the student.");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Student not created")
    public static class StudentNotCreatedException extends RuntimeException {
        public StudentNotCreatedException(String name) {
            super("The student with name \"" + name + "\" was not created");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Student not edited")
    public static class StudentNotEditedException extends RuntimeException {
        public StudentNotEditedException(Long id) {
            super("The student with id \"" + id + "\" was not edited");
        }
    }
}
