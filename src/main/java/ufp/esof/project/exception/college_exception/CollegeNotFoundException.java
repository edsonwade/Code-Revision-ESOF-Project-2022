package ufp.esof.project.exception.college_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid id College")
public class CollegeNotFoundException extends RuntimeException {
    public CollegeNotFoundException(String message) {
        super(message);
    }
}