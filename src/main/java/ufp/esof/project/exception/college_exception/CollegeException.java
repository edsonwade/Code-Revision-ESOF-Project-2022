package ufp.esof.project.exception.college_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "Invalid Input")
public class CollegeException extends RuntimeException {
    public CollegeException(String message) {
        super(message);
    }

}
