/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:44
 * Version:1
 */

package ufp.esof.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
