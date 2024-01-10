package assignment.Kirana.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAdminException extends RuntimeException {
    public NotAdminException(String message) {
        super(message);
    }
}
