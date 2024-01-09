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
        return new ResponseEntity<ApiResponse>(response,HttpStatus.UNAUTHORIZED);
    }
}
