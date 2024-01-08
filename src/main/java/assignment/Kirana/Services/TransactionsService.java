package assignment.Kirana.Services;

import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.Transactions;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;


@Service
public class TransactionsService {
    @Autowired
    private TransactionRepository transactionRepo;

    public Transactions addTransaction(Transactions transaction)

    {
        LocalDateTime time = LocalDateTime.now();
        transaction.setTransactionTime(time);
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year  = time.getYear();
        transaction.setDay(day);
        transaction.setYear(year);
        transaction.setMonth(month);
        return transactionRepo.save(transaction);
    }

    public Double dailyAverage(int day, int month, int year) {
        // Fetch transactions for the specified day, month, and year
        List<Transactions> transactions = transactionRepo.findAllByDayAndMonthAndYear(day, month, year);

        // Calculate the daily average of amounts
        OptionalDouble average = transactions.stream()
                .mapToDouble(Transactions::getAmount)
                .average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);
    }

    public Double monthlyAverage(int month, int year) {
        // Fetch transactions for the specified month and year
        List<Transactions> transactions = transactionRepo.findAllByMonthAndYear(month, year);

        // Calculate the monthly average of amounts
        OptionalDouble average = transactions.stream()
                .mapToDouble(Transactions::getAmount)
                .average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);
    }

    public Double YearlyAverage(int year){
        List<Transactions> transactions = transactionRepo.findAllByYear(year);
        OptionalDouble average = transactions.stream()
                .mapToDouble(Transactions::getAmount)
                .average();

        // Return the result, or 0.0 if there are no transactions
        return average.orElse(0.0);

    }
    public List<Transactions> getAllTransactions(){
        return  transactionRepo.findAll();
    }

}
