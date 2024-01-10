package assignment.Kirana.Exceptions;

import assignment.Kirana.models.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = InvalidJwtException.class)
    public ResponseEntity<ApiResponse> invalidJwtHandler(InvalidJwtException invalidJwtException) {
        ApiResponse response = new ApiResponse();
        response.setStatus("error");
        response.setError(invalidJwtException.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ApiResponse> tokenExpiredHandler(
            TokenExpiredException tokenExpiredException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(tokenExpiredException.getMessage());
        return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NotAdminException.class)
    public ResponseEntity<ApiResponse> notAdminException(NotAdminException notAdminException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(notAdminException.getMessage());
        return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<ApiResponse> notAdminException(
            InvalidAmountException invalidAmountException) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(invalidAmountException.getMessage());
        return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnAuthenticatedRequest.class)
    public ResponseEntity<ApiResponse> notAdminException(
            UnAuthenticatedRequest unAuthenticatedRequest) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setStatus("error");
        response.setError(unAuthenticatedRequest.getMessage());
        return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
    }
}
