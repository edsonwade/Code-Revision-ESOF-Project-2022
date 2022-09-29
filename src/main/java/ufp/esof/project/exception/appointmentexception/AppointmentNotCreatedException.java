package ufp.esof.project.exception.appointmentexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppointmentNotCreatedException extends RuntimeException {
    public AppointmentNotCreatedException(String message) {
        super(message);
    }
}
