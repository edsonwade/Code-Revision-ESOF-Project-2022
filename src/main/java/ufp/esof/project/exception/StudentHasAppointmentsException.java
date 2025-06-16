/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:14:43
 * Version:1
 */

package ufp.esof.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentHasAppointmentsException extends RuntimeException {
    public StudentHasAppointmentsException(String message) {
        super(message);
    }
}
