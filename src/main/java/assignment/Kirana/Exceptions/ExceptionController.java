package assignment.Kirana.Exceptions;

import assignment.Kirana.models.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Controller advice to handle various custom exceptions and provide appropriate responses. */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handles InvalidJwtException and returns a response with UNAUTHORIZED status.
     *
     * @param invalidJwtException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = InvalidJwtException.class)
    public ResponseEntity<ApiResponse> invalidJwtHandler(InvalidJwtException invalidJwtException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setError(invalidJwtException.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles RateLimitExceededException and returns a response with TOO_MANY_REQUESTS status.
     *
     * @param rateLimitException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = RateLimitExceededException.class)
    public ResponseEntity<ApiResponse> rateLimitExceptionHandler(
            RateLimitExceededException rateLimitException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setError(rateLimitException.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * Handles TokenExpiredException and returns a response with UNAUTHORIZED status.
     *
     * @param tokenExpiredException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ApiResponse> tokenExpiredHandler(
            TokenExpiredException tokenExpiredException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(tokenExpiredException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles NotAdminException and returns a response with UNAUTHORIZED status.
     *
     * @param notAdminException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = NotAdminException.class)
    public ResponseEntity<ApiResponse> notAdminException(NotAdminException notAdminException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(notAdminException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles InvalidAmountException and returns a response with BAD_REQUEST status.
     *
     * @param invalidAmountException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<ApiResponse> notAdminException(
            InvalidAmountException invalidAmountException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(invalidAmountException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UnAuthenticatedRequest and returns a response with UNAUTHORIZED status.
     *
     * @param unAuthenticatedRequest The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = UnAuthenticatedRequest.class)
    public ResponseEntity<ApiResponse> notAdminException(
            UnAuthenticatedRequest unAuthenticatedRequest) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(unAuthenticatedRequest.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles UserNotFound and returns a response with NOT_FOUND status.
     *
     * @param userNotFoundException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = UserNotFound.class)
    public ResponseEntity<ApiResponse> userNotFoundHandler(UserNotFound userNotFoundException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setSuccess(false);
        response.setError(userNotFoundException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidDateComponentsException and returns a response with BAD_REQUEST status.
     *
     * @param invalidDateComponentsException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = InvalidDateComponentsException.class)
    public ResponseEntity<ApiResponse> invalidDateException(
            InvalidDateComponentsException invalidDateComponentsException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setSuccess(false);
        response.setError(invalidDateComponentsException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidCurrencyException and returns a response with BAD_REQUEST status.
     *
     * @param invalidCurrencyException The exception to be handled.
     * @return ResponseEntity containing ApiResponse with error details and status code.
     */
    @ExceptionHandler(value = InvalidCurrencyException.class)
    public ResponseEntity<ApiResponse> invalidDateException(
           InvalidCurrencyException invalidCurrencyException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setSuccess(false);
        response.setError(invalidCurrencyException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
