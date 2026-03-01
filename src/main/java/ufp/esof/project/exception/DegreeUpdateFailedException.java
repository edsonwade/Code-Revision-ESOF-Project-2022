package ufp.esof.project.exception;

@SuppressWarnings("all")
public class DegreeUpdateFailedException extends RuntimeException {
    public DegreeUpdateFailedException(Long id) {
        super("Failed to update degree with id \"" + id + "\"");
    }
    
    public DegreeUpdateFailedException(String message) {
        super(message);
    }
    
    public DegreeUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
