package assignment.Kirana.Services;

import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.MonthlyReport;
import assignment.Kirana.models.Transactions;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired TransactionsService transactionsService;

    @Autowired TransactionRepository transactionRepo;

    private static final Map<Integer, Integer> monthDaysMap =
            new HashMap<Integer, Integer>() {
                {
                    put(1, 31); // January
                    put(2, 28); // February (non-leap year)
                    put(3, 31); // March
                    put(4, 30); // April
                    put(5, 31); // May
                    put(6, 30); // June
                    put(7, 31); // July
                    put(8, 31); // August
                    put(9, 30); // September
                    put(10, 31); // October
                    put(11, 30); // November
                    put(12, 31); // December
                }
            };

    public Double amountSum(List<Transactions> transactions) {
        Double sum = 0.0;

        for (Transactions transaction : transactions) {
            sum += transaction.getAmount();
        }

        return sum;
    }

    public Double dailyAverage(int day, int month, int year) {
        // Fetch transactions for the specified day, month, and year
        List<Transactions> transactions =
                transactionRepo.findAllByDayAndMonthAndYear(day, month, year);
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);
    }

    public Double monthlyAverage(int month, int year) {
        // Fetch transactions for the specified month and year
        List<Transactions> transactions = transactionRepo.findAllByMonthAndYear(month, year);

        // Calculate the monthly average of amounts
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);
    }

    public Double YearlyAverage(int year) {
        List<Transactions> transactions = transactionRepo.findAllByYear(year);
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);
    }

    private Double round(Double value, int decimalPlaces) {
        if (value == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public MonthlyReport getMonthlyReportOfUser(int month, int year, String userId) {
        List<Transactions> monthlyCredit =
                transactionsService.getMonthlyCreditOfUser(month, year, userId);
        List<Transactions> monthlyDebit =
                transactionsService.getMonthlyDebitOfUser(month, year, userId);

        Double totalCreditAmount = round(amountSum(monthlyCredit), 2);
        Double totalDebitAmount = round(amountSum(monthlyDebit), 2);

        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = round(totalCreditAmount / monthDaysMap.get(month), 2);
        Double averageDebit = round(totalDebitAmount / monthDaysMap.get(month), 2);
        Double averageTransaction = round(totalAmount / monthDaysMap.get(month), 2);

        MonthlyReport report = new MonthlyReport();
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);

        return report;
    }
}
