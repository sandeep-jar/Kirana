package assignment.Kirana.models.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private boolean success = true;
    //    @JsonIgnoreProperties(value = {"createdAt", "updatedAt"})
    private Object data;

    private Object view;

    private String status;

    private String error;

    private Object errorMessage;

    private String errorCode;

    private String displayMsg;
}
