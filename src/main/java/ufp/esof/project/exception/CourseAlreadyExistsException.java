package ufp.esof.project.exception;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String name) {
        super("Course with name \"" + name + "\" already exists");
    }

}
