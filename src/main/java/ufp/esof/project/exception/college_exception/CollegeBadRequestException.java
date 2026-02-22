/**
 * Author: vanilson muhongo
 * Date:22/02/2026
 * Time:16:37
 * Version:1
 */

package ufp.esof.project.exception.college_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CollegeBadRequestException extends RuntimeException {
    public CollegeBadRequestException(String name) {
        super("College with name '" + name + "' was not created");
    }

    public CollegeBadRequestException(Long id) {
        super("College with id " + id + " has degrees associated. Delete degrees first.");
    }
}
