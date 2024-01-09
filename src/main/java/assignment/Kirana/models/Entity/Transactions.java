package assignment.Kirana.models.Entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Transactions {
    @Id private String transactionId;
    private String from;
    private Double amount;
    private String initialCurrency;

    private String finalCurrency;
    private String to;

    private int month;

    private int year;
    private int day;

    private LocalDateTime transactionTime;
}
