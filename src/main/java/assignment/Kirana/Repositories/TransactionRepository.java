package assignment.Kirana.Repositories;

import assignment.Kirana.models.Transactions;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transactions,String> {
}
