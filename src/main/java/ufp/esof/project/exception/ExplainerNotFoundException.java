/**
 * Author: vanilson muhongo
 * Date:16/06/2025
 * Time:15:50
 * Version:
 */

package ufp.esof.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExplainerNotFoundException extends RuntimeException {

    public ExplainerNotFoundException(String message) {
        super(message);
    }
}
