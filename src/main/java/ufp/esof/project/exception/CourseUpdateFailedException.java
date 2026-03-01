package ufp.esof.project.exception;

@SuppressWarnings("all")
public class CourseUpdateFailedException extends RuntimeException {
    public CourseUpdateFailedException(Long id) {
        super("Failed to update course with id \"" + id + "\"");
    }

    public CourseUpdateFailedException(String message) {
        super(message);
    }

    public CourseUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
