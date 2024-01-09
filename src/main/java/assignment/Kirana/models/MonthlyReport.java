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
}
