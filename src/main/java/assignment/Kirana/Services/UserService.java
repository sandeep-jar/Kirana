package assignment.Kirana.Services;

import assignment.Kirana.Repositories.UserRepository;
import assignment.Kirana.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);


    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String id){
        Optional<User> result = userRepository.findById(id);

            return result.orElse(null);
    }
}
