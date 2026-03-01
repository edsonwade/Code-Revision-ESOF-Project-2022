package ufp.esof.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Degree not found")
public class DegreeNotFoundException extends RuntimeException {
    public DegreeNotFoundException(Long id) {
        super("Degree with id \"" + id + "\" not found");
    }
    
    public DegreeNotFoundException(String message) {
        super(message);
    }
}
