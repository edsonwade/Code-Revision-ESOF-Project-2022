package ufp.esof.project.exception;

public class DegreeAlreadyExistsException extends RuntimeException {
    public DegreeAlreadyExistsException(String name) {
        super("Degree with name \"" + name + "\" already exists");
    }
    

}
