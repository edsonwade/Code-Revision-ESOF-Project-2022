/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:14:19
 * Version:
 */

package ufp.esof.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(String message) {
        super(message);
    }
}
