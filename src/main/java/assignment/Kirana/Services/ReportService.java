package assignment.Kirana.Services;

import assignment.Kirana.Configurations.RateLimitConfig;
import assignment.Kirana.Exceptions.InvalidDateComponentsException;
import assignment.Kirana.Exceptions.NotAdminException;
import assignment.Kirana.Exceptions.RateLimitExceededException;
import assignment.Kirana.Exceptions.TokenExpiredException;
import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.Response.ApiResponse;
import assignment.Kirana.models.Response.MonthlyReport;
import assignment.Kirana.models.Response.WeeklyReport;
import assignment.Kirana.models.Response.YearlyReport;
import io.github.bucket4j.Bucket;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The ReportService class provides business logic for generating monthly reports and handling
 * related operations.
 */
@Service
public class ReportService {
    private final TransactionsService transactionsService;
    private final TransactionRepository transactionRepo;
    private final JwtServices jwtServices;

    private final RateLimitConfig rateLimitConfig;

    /**
     * Constructor to initialize the ReportService with required services.
     *
     * @param transactionsService An instance of TransactionsService for fetching transaction data.
     * @param transactionRepository An instance of TransactionRepository for database interactions.
     * @param jwtServices An instance of JwtServices for JWT token validation.
     */
    @Autowired
    public ReportService(
            TransactionsService transactionsService,
            TransactionRepository transactionRepository,
            JwtServices jwtServices,
            RateLimitConfig rateLimitConfig) {
        this.jwtServices = jwtServices;
        this.transactionRepo = transactionRepository;
        this.transactionsService = transactionsService;
        this.rateLimitConfig = rateLimitConfig;
    }

    /*
     * Map to get the number of days in a month based on the month number.
     */
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

    /**
     * Calculates the sum of amounts in a given list of transactions.
     *
     * @param transactions The list of transactions for which the sum is calculated.
     * @return The sum of amounts in the provided transactions.
     */
    public Double amountSum(List<Transactions> transactions) {
        Double sum = 0.0;
        for (Transactions transaction : transactions) {
            sum += transaction.getAmount();
        }
        return sum;
    }

    /**
     * Calculates the daily average transaction amount for a specified day, month, and year.
     *
     * @param day The day for which the daily average is calculated.
     * @param month The month for which the daily average is calculated.
     * @param year The year for which the daily average is calculated.
     * @return The daily average transaction amount.
     */
    public Double dailyAverage(int day, int month, int year) {
        List<Transactions> transactions =
                transactionRepo.findAllByDayAndMonthAndYear(day, month, year);
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();
        return average.orElse(0.0);
    }

    /**
     * Calculates the monthly average transaction amount for a specified month and year.
     *
     * @param month The month for which the monthly average is calculated.
     * @param year The year for which the monthly average is calculated.
     * @return The monthly average transaction amount.
     */
    public Double monthlyAverage(int month, int year) {
        List<Transactions> transactions = transactionRepo.findAllByMonthAndYear(month, year);
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();
        return average.orElse(0.0);
    }

    /**
     * Calculates the yearly average transaction amount for a specified year.
     *
     * @param year The year for which the yearly average is calculated.
     * @return The yearly average transaction amount.
     */
    public Double YearlyAverage(int year) {

        List<Transactions> transactions = transactionRepo.findAllByYear(year);
        OptionalDouble average =
                transactions.stream().mapToDouble(Transactions::getAmount).average();
        return average.orElse(0.0);
    }

    /**
     * Rounds off a double value to a specified number of decimal places using RoundingMode.HALF_UP.
     *
     * @param value The double value to be rounded.
     * @param decimalPlaces The number of decimal places to round off to.
     * @return The rounded double value.
     */
    private Double round(Double value, int decimalPlaces) {
        if (value == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Creates a MonthlyReport for the given user, including total and average transaction details.
     *
     * @param month The month for which the report is generated.
     * @param year The year for which the report is generated.
     * @param userId The ID of the user for whom the report is generated.
     * @return MonthlyReport containing total and average transaction details.
     */
    public MonthlyReport createMonthlyReportOfUser(int month, int year, String userId) {
        // Fetch monthly credit and debit transactions for the specified user
        List<Transactions> monthlyCredit =
                transactionsService.getMonthlyCreditOfUser(month, year, userId);
        List<Transactions> monthlyDebit =
                transactionsService.getMonthlyDebitOfUser(month, year, userId);

        // Calculate total credit and debit amounts, and round off to 2 decimal places
        Double totalCreditAmount = round(amountSum(monthlyCredit), 2);
        Double totalDebitAmount = round(amountSum(monthlyDebit), 2);

        // Calculate total amount, average credit, average debit, and average transaction
        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = round(totalCreditAmount / monthDaysMap.get(month), 2);
        Double averageDebit = round(totalDebitAmount / monthDaysMap.get(month), 2);
        Double averageTransaction = round(totalAmount / monthDaysMap.get(month), 2);

        // Create and return MonthlyReport
        MonthlyReport report = new MonthlyReport();
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);

        return report;
    }

    /**
     * Takes year userId and returns the YearlyReport, calculates : total amount,average
     * credit,average debit,netAmount,average transaction,
     *
     * @param year year for which report is being created
     * @param userId userId The user ID for whom the report is being generated.
     * @return YearlyReport
     */
    public YearlyReport createYearlyReportOfUser(int year, String userId) {
        // Fetch monthly credit and debit transactions for the specified user
        List<Transactions> YearlyCredit = transactionsService.getYearlyCreditOfUser(year, userId);
        List<Transactions> YearlyDebit = transactionsService.getYearlyDebitOfUser(year, userId);

        // Calculate total credit and debit amounts, and round off to 2 decimal places
        Double totalCreditAmount = round(amountSum(YearlyCredit), 2);
        Double totalDebitAmount = round(amountSum(YearlyDebit), 2);

        // Calculate total amount, average credit, average debit, and average transaction
        Double totalDays = 365.0;
        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = round(totalCreditAmount / totalDays, 2);
        Double averageDebit = round(totalDebitAmount / totalDays, 2);
        Double averageTransaction = round(totalAmount / totalDays, 2);
        Double netAmount = round(totalCreditAmount - totalDebitAmount, 2);

        // Create and return MonthlyReport
        YearlyReport report = new YearlyReport();
        report.setNetProfit(netAmount);
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);
        report.setNetProfit(netAmount);

        return report;
    }

    /**
     * Takes year userId and returns the WeeklyReport, calculates : total amount,average
     * credit,average debit,netAmount,average transaction,
     *
     * @param userId userId The user ID for whom the report is being generated.
     * @return YearlyReport
     */
    public WeeklyReport createWeeklyReportOfUser(String userId) {
        // Fetch monthly credit and debit transactions for the specified user
        List<Transactions> weeklyCredit =
                transactionsService.getCreditTransactionOfPastWeek(userId);
        List<Transactions> weeklyDebit = transactionsService.getDebitTransactionOfPastWeek(userId);

        // Calculate total credit and debit amounts, and round off to 2 decimal places
        Double totalCreditAmount = round(amountSum(weeklyCredit), 2);
        Double totalDebitAmount = round(amountSum(weeklyDebit), 2);

        // Calculate total amount, average credit, average debit, and average transaction
        Double totalDays = 7.0;
        Double totalAmount = totalDebitAmount + totalCreditAmount;
        Double averageCredit = round(totalCreditAmount / totalDays, 2);
        Double averageDebit = round(totalDebitAmount / totalDays, 2);
        Double averageTransaction = round(totalAmount / totalDays, 2);
        Double netAmount = totalCreditAmount - totalDebitAmount;

        // Create and return MonthlyReport
        WeeklyReport report = new WeeklyReport();

        report.setNetProfit(netAmount);
        report.setTotalTransaction(totalAmount);
        report.setTotalDebit(totalDebitAmount);
        report.setTotalCredit(totalCreditAmount);
        report.setAverageCredit(averageCredit);
        report.setAverageDebit(averageDebit);
        report.setAverageTransaction(averageTransaction);

        return report;
    }

    /**
     * Generates an ApiResponse containing the MonthlyReport for the given user.
     *
     * @param month The month for which the report is generated.
     * @param userId The ID of the user for whom the report is generated.
     * @param jwtToken The JWT token for authentication and authorization.
     * @return ApiResponse containing the MonthlyReport and appropriate status.
     * @throws TokenExpiredException If the provided JWT token is expired.
     * @throws NotAdminException If the user is not an admin and does not have access.
     */
    public ApiResponse getMonthlyReportApiResponse(int month, String userId, String jwtToken)
            throws TokenExpiredException, NotAdminException {
        System.out.println("entered report");
        // Verify JWT token expiry and admin status
        boolean isExpired = jwtServices.verifyExpiry(jwtToken);
        boolean isAdmin = jwtServices.verifyAdmin(jwtToken);

        String key = "monthlyReport" + userId;
        Bucket bucket = rateLimitConfig.resolveBucket(key,5);
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("request quota exceeded , try after some time");
        }

        if (month < 1 || month > 12) {
            throw new InvalidDateComponentsException("invalid Month");
        }

        // Handle token expiration exception
        if (isExpired) {
            throw new TokenExpiredException("Login session expired, please login again.");
        }

        // Handle not admin exception
        if (!isAdmin) {
            throw new NotAdminException("Only admin users can access this service.");
        }

        // Create ApiResponse with MonthlyReport data and return
        ApiResponse api = new ApiResponse();
        LocalDateTime currentTime = LocalDateTime.now();
        int year = currentTime.getYear();
        MonthlyReport report = createMonthlyReportOfUser(month, year, userId);
        api.setData(report);
        return api;
    }

    /**
     * Generates an ApiResponse containing the MonthlyReport for the given user.
     *
     * @param year The year for which the report is generated.
     * @param userId The ID of the user for whom the report is generated.
     * @param jwtToken The JWT token for authentication and authorization.
     * @return ApiResponse containing the MonthlyReport and appropriate status.
     * @throws TokenExpiredException If the provided JWT token is expired.
     * @throws NotAdminException If the user is not an admin and does not have access.
     * @throws InvalidDateComponentsException if year is not valid
     */
    public ApiResponse getYearlyReportApiResponse(int year, String userId, String jwtToken)
            throws TokenExpiredException, NotAdminException {
        // Verify JWT token expiry and admin status
        boolean isExpired = jwtServices.verifyExpiry(jwtToken);
        boolean isAdmin = jwtServices.verifyAdmin(jwtToken);
        String key = "yearlyReport" + userId;
        Bucket bucket = rateLimitConfig.resolveBucket(key,5);
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("request quota exceeded , try after some time");
        }

        if (year < 0) {
            throw new InvalidDateComponentsException("invalid year");
        }

        // Handle token expiration exception

        // Handle not admin exception
        if (!isAdmin) {
            throw new NotAdminException("Only admin users can access this service.");
        }
        if (isExpired) {
            throw new TokenExpiredException("Login session expired, please login again.");
        }

        // Create ApiResponse with MonthlyReport data and return
        ApiResponse api = new ApiResponse();
        YearlyReport report = createYearlyReportOfUser(year, userId);
        api.setData(report);
        return api;
    }

    /**
     * Generates an ApiResponse containing the MonthlyReport for the given user.
     *
     * @param userId The ID of the user for whom the report is generated.
     * @param jwtToken The JWT token for authentication and authorization.
     * @return ApiResponse containing the MonthlyReport and appropriate status.
     * @throws TokenExpiredException If the provided JWT token is expired.
     * @throws NotAdminException If the user is not an admin and does not have access.
     * @throws InvalidDateComponentsException if year is not valid
     */
    public ApiResponse getWeeklyReportApiResponse(String userId, String jwtToken)
            throws TokenExpiredException, NotAdminException {
        // Verify JWT token expiry and admin status
        boolean isExpired = jwtServices.verifyExpiry(jwtToken);
        boolean isAdmin = jwtServices.verifyAdmin(jwtToken);
        String key = "weeklyReport" + userId;
        Bucket bucket = rateLimitConfig.resolveBucket(key,5);
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("request quota exceeded , try after some time");
        }

        // Handle token expiration exception
        if (isExpired) {
            throw new TokenExpiredException("Login session expired, please login again.");
        }

        // Handle not admin exception
        if (!isAdmin) {
            throw new NotAdminException("Only admin users can access this service.");
        }

        // Create ApiResponse with MonthlyReport data and return
        ApiResponse api = new ApiResponse();
        WeeklyReport report = createWeeklyReportOfUser(userId);
        api.setData(report);
        return api;
    }
}
