package assignment.Kirana.Exceptions;

public class UnAuthenticatedRequest extends RuntimeException {
    public UnAuthenticatedRequest(String message) {
        super(message);
    }
}
