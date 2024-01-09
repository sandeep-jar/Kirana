package assignment.Kirana.models;

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

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getInitialCurrency() {
        return this.initialCurrency;
    }

    public void setInitialCurrency(String initialCurrency) {
        this.initialCurrency = initialCurrency;
    }

    public String getFinalCurrency() {
        return this.finalCurrency;
    }

    public void setFinalCurrency(String finalCurrency) {
        this.finalCurrency = finalCurrency;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return this.from;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
