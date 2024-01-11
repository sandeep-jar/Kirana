package assignment.Kirana.Services;

import assignment.Kirana.Exceptions.InvalidAmountException;
import assignment.Kirana.Exceptions.UnAuthenticatedRequest;
import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.ExchangeRatesResponse;
import assignment.Kirana.models.Response.ApiResponse;
import assignment.Kirana.models.TransactionRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {
    private final TransactionRepository transactionRepo;
    private final JwtServices jwtServices;

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public TransactionsService(
            TransactionRepository transactionRepository,
            JwtServices jwtServices,
            ExchangeRateService exchangeRateService) {
        this.jwtServices = jwtServices;
        this.transactionRepo = transactionRepository;
        this.exchangeRateService = exchangeRateService;
    }

    public Transactions addTransaction(TransactionRequest data) {
        Transactions transaction = new Transactions();
        transaction.setInitialCurrency(data.getInitialCurrency());
        transaction.setFrom(data.getFrom());
        transaction.setTo(data.getTo());
        String currencyType = transaction.getInitialCurrency().toUpperCase();
        transaction.setInitialCurrency(currencyType);
        transaction.setFinalCurrency("USD");
        ExchangeRatesResponse ExchangeData = exchangeRateService.getRates();
        Map<String, Double> rates = ExchangeData.getRates();
        Double convertedAmount = data.getAmount() / rates.get(currencyType);
        BigDecimal roundedAmount =
                new BigDecimal(convertedAmount).setScale(2, RoundingMode.HALF_UP);
        transaction.setAmount(roundedAmount.doubleValue());

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

    public ApiResponse transactionHandler(String jwtToken, TransactionRequest data) {
        String userId = data.getFrom();
        boolean auth = jwtServices.verifyUser(jwtToken, userId);
        if (!auth) {
            throw new UnAuthenticatedRequest("your request failed authentication");
        }
        if (data.getAmount() <= 0) {
            throw new InvalidAmountException("amount should be greater than zero");
        }
        Transactions completedTransaction = addTransaction(data);
        ApiResponse response = new ApiResponse();
        response.setStatus("success");
        response.setData(completedTransaction);
        return response;
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
