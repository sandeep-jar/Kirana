package assignment.Kirana.Services;

import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class TransactionsService {
    @Autowired
    private TransactionRepository transactionRepo;


    public Transactions addTransaction(Transactions transaction)

    {
        transaction.setTransactionTime(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }

    public List<Transactions> getAllTransactions(){
        return  transactionRepo.findAll();
    }

}
