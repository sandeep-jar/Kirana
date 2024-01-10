package assignment.Kirana.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthenticatedRequest extends RuntimeException {
    public UnAuthenticatedRequest(String message) {
        super(message);
    }
}
