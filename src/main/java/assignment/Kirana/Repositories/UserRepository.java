package assignment.Kirana.Repositories;

import assignment.Kirana.models.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {}
