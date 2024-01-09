package assignment.Kirana.Repositories;

import assignment.Kirana.models.Entity.Transactions;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transactions, String> {
    List<Transactions> findAllByDayAndMonthAndYear(int day, int month, int year);

    List<Transactions> findAllByMonthAndYear(int month, int year);

    List<Transactions> findAllByYear(int year);

    List<Transactions> findAllByMonthAndYearAndFrom(int month, int year, String from);

    List<Transactions> findAllByMonthAndYearAndTo(int month, int year, String to);

    List<Transactions> findAllByDayAndMonthAndYearAndFrom(
            int day, int month, int year, String from);

    List<Transactions> findAllByDayAndMonthAndYearAndTo(int day, int month, int year, String to);

    List<Transactions> findAllByYearAndFrom(int year, String from);

    List<Transactions> findAllByYearAndTo(int year, String to);
}
