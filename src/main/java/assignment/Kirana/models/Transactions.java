package assignment.Kirana.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Transactions {
    @Id
    private String transactionId;
    private String from;
    private Number amount;
    private String currency;
    private String to;
    private LocalDateTime transactionTime;

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
