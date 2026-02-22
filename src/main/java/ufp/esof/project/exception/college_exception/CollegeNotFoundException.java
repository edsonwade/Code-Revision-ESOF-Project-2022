package ufp.esof.project.exception.college_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CollegeNotFoundException extends RuntimeException {
    public CollegeNotFoundException(String message) {
        super(message);
    }
    public CollegeNotFoundException(Long id) {
        super("College with id " + id + " not found");
    }
}