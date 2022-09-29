package ufp.esof.project.exception.availabilityexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Availability not deleted")
public class AvailabilityNotDeletedException extends RuntimeException {
    public AvailabilityNotDeletedException(String message) {
        super(message);
    }
}
