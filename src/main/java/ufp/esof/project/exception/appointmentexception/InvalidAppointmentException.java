package ufp.esof.project.exception.appointmentexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidAppointmentException extends RuntimeException {
    public InvalidAppointmentException(String message) {
        super(message);
    }

}
