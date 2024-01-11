package assignment.Kirana.Services;

import assignment.Kirana.Exceptions.InvalidAmountException;
import assignment.Kirana.Exceptions.UnAuthenticatedRequest;
import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.Validators.TransactionValidator;
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

/** Service class for managing financial transactions. */
@Service
public class TransactionsService {
    private final TransactionRepository transactionRepo;
    private final JwtServices jwtServices;

    private final TransactionValidator transactionValidator;

    private final ExchangeRateService exchangeRateService;

    /**
     * Constructor for TransactionsService.
     *
     * @param transactionRepository The repository for managing transactions.
     * @param jwtServices The service for JWT token operations.
     * @param exchangeRateService The service for fetching exchange rates.
     */
    @Autowired
    public TransactionsService(
            TransactionRepository transactionRepository,
            JwtServices jwtServices,
            ExchangeRateService exchangeRateService , TransactionValidator transactionValidator) {
        this.jwtServices = jwtServices;
        this.transactionRepo = transactionRepository;
        this.exchangeRateService = exchangeRateService;
        this.transactionValidator = transactionValidator;
    }

    /**
     * Adds a new transaction to the system.
     *
     * @param data The transaction request data.
     * @return The added transaction.
     */
    public Transactions addTransaction(TransactionRequest data) {
        transactionValidator.validateTransactionDetails(data);
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

    /**
     * Handles a transaction request and performs necessary validations.
     *
     * @param jwtToken The JWT token for authentication.
     * @param data The transaction request data.
     * @return ApiResponse containing the result of the transaction.
     * @throws UnAuthenticatedRequest If the request fails authentication.
     * @throws InvalidAmountException If the transaction amount is invalid.
     */
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

    /**
     * Retrieves all transactions for a specific month and year.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @return A list of Transactions for the specified month and year.
     */
    public List<Transactions> getAllTransactionOfMonth(int month, int year) {
        return transactionRepo.findAllByMonthAndYear(month, year);
    }

    /**
     * Retrieves monthly debit transactions for a specific user.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param userId The ID of the user for whom transactions are to be retrieved.
     * @return A list of debit Transactions for the specified month, year, and user.
     */
    public List<Transactions> getMonthlyDebitOfUser(int month, int year, String userId) {
        return transactionRepo.findAllByMonthAndYearAndFrom(month, year, userId);
    }

    /**
     * Retrieves monthly credit transactions for a specific user.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param userId The ID of the user for whom transactions are to be retrieved.
     * @return A list of credit Transactions for the specified month, year, and user.
     */
    public List<Transactions> getMonthlyCreditOfUser(int month, int year, String userId) {
        return transactionRepo.findAllByMonthAndYearAndTo(month, year, userId);
    }

    /**
     * Retrieves yearly debit transactions for a specific user.
     *
     * @param year The year for which transactions are to be retrieved.
     * @param userId The ID of the user for whom transactions are to be retrieved.
     * @return A list of debit Transactions for the specified year and user.
     */
    public List<Transactions> getYearlyDebitOfUser(int year, String userId) {
        return transactionRepo.findAllByYearAndFrom(year, userId);
    }

    /**
     * Retrieves yearly credit transactions for a specific user.
     *
     * @param year The year for which transactions are to be retrieved.
     * @param userId The ID of the user for whom transactions are to be retrieved.
     * @return A list of credit Transactions for the specified year and user.
     */
    public List<Transactions> getYearlyCreditOfUser(int year, String userId) {
        return transactionRepo.findAllByYearAndTo(year, userId);
    }

    /**
     * Retrieves all transactions.
     *
     * @return A list of all Transactions.
     */
    public List<Transactions> getAllTransactions() {
        return transactionRepo.findAll();
    }
}
