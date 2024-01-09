package assignment.Kirana.Services;

import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.Entity.Transactions;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {
    @Autowired private TransactionRepository transactionRepo;

    public Transactions addTransaction(Transactions transaction) {

        LocalDateTime time = LocalDateTime.now();
        transaction.setTransactionTime(time);
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year = time.getYear();
        transaction.setDay(day);
        transaction.setYear(year);
        transaction.setMonth(month);
        return transactionRepo.save(transaction);
    }

    public List<Transactions> getAllTransactionOfMonth(int month, int year) {
        return transactionRepo.findAllByMonthAndYear(month, year);
    }

    public List<Transactions> getMonthlyDebitOfUser(int month, int year, String userId) {
        return transactionRepo.findAllByMonthAndYearAndFrom(month, year, userId);
    }

    public List<Transactions> getMonthlyCreditOfUser(int month, int year, String userId) {
        return transactionRepo.findAllByMonthAndYearAndTo(month, year, userId);
    }

    public List<Transactions> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
