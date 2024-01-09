package assignment.Kirana.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReport {
    private Double totalTransaction;

    private Double totalDebit;

    private Double totalCredit;

    private Double averageCredit;

    private Double averageDebit;

    private Double averageTransaction;

    public void setTotalTransaction(Double totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public void setAverageCredit(Double averageCredit) {
        this.averageCredit = averageCredit;
    }

    public void setAverageDebit(Double averageDebit) {
        this.averageDebit = averageDebit;
    }

    public void setAverageTransaction(Double averageTransaction) {
        this.averageTransaction = averageTransaction;
    }


}
