package ufp.esof.project.exception.availabilityexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AvailabilityNotEditedException extends RuntimeException {
    public AvailabilityNotEditedException(String message) {
        super(message);
    }
}
