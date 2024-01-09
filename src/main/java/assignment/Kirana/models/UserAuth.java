package assignment.Kirana.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    private String userId;

    private String paymentCompleted;
}
