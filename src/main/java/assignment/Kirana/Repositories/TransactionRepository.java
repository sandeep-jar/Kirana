package assignment.Kirana.Repositories;

import assignment.Kirana.models.Entity.Transactions;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/** Repository interface for managing Transaction entities in MongoDB. */
public interface TransactionRepository extends MongoRepository<Transactions, String> {

    /**
     * Retrieves all transactions for a specific day, month, and year.
     *
     * @param day The day for which transactions are to be retrieved.
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @return A list of Transactions for the specified day, month, and year.
     */
    List<Transactions> findAllByDayAndMonthAndYear(int day, int month, int year);

    /**
     * Retrieves all transactions for a specific month and year.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @return A list of Transactions for the specified month and year.
     */
    List<Transactions> findAllByMonthAndYear(int month, int year);

    /**
     * Retrieves all transactions for a specific year.
     *
     * @param year The year for which transactions are to be retrieved.
     * @return A list of Transactions for the specified year.
     */
    List<Transactions> findAllByYear(int year);

    /**
     * Retrieves all debit transactions for a specific month, year, and user.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param from The ID of the user from whom transactions are to be retrieved.
     * @return A list of debit Transactions for the specified month, year, and user.
     */
    List<Transactions> findAllByMonthAndYearAndFrom(int month, int year, String from);

    /**
     * Retrieves all credit transactions for a specific month, year, and user.
     *
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param to The ID of the user to whom transactions are to be retrieved.
     * @return A list of credit Transactions for the specified month, year, and user.
     */
    List<Transactions> findAllByMonthAndYearAndTo(int month, int year, String to);

    /**
     * Retrieves all debit transactions for a specific day, month, year, and user.
     *
     * @param day The day for which transactions are to be retrieved.
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param from The ID of the user from whom transactions are to be retrieved.
     * @return A list of debit Transactions for the specified day, month, year, and user.
     */
    List<Transactions> findAllByDayAndMonthAndYearAndFrom(
            int day, int month, int year, String from);

    /**
     * Retrieves all credit transactions for a specific day, month, year, and user.
     *
     * @param day The day for which transactions are to be retrieved.
     * @param month The month for which transactions are to be retrieved.
     * @param year The year for which transactions are to be retrieved.
     * @param to The ID of the user to whom transactions are to be retrieved.
     * @return A list of credit Transactions for the specified day, month, year, and user.
     */
    List<Transactions> findAllByDayAndMonthAndYearAndTo(int day, int month, int year, String to);

    /**
     * Retrieves all debit transactions for a specific year and user.
     *
     * @param year The year for which transactions are to be retrieved.
     * @param from The ID of the user from whom transactions are to be retrieved.
     * @return A list of debit Transactions for the specified year and user.
     */
    List<Transactions> findAllByYearAndFrom(int year, String from);

    /**
     * Retrieves all credit transactions for a specific year and user.
     *
     * @param year The year for which transactions are to be retrieved.
     * @param to The ID of the user to whom transactions are to be retrieved.
     * @return A list of credit Transactions for the specified year and user.
     */
    List<Transactions> findAllByYearAndTo(int year, String to);
}
