package assignment.Kirana.Services;

import assignment.Kirana.Exceptions.UserNotFound;
import assignment.Kirana.Repositories.UserRepository;
import assignment.Kirana.models.Entity.User;
import assignment.Kirana.models.Response.ApiResponse;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service class for managing user-related operations. */
@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    /**
     * Adds a new user to the system.
     *
     * @param user The user to be added.
     * @return ApiResponse containing the result of the operation.
     */
    public ApiResponse addUser(User user) {
        try {
            if (user.getName() == null) {
                throw new InvalidParameterException("enter valid name");
            }
            User data = userRepository.save(user);
            ApiResponse response = new ApiResponse();
            response.setData(data);
            response.setDisplayMsg("user added successfully");
            return response;
        } catch (Exception err) {
            ApiResponse response = new ApiResponse();
            response.setSuccess(false);
            response.setStatus("error");
            response.setError(err.getMessage());
            response.setDisplayMsg("adding the user failed try again");
            return response;
        }
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws UserNotFound If the user with the given ID is not found.
     */
    public User getUser(String id) {
        Optional<User> result = userRepository.findById(id);
        User user = result.orElse(null);
        if (user == null) {
            throw new UserNotFound("such user doesn't exist");
        } else {
            return user;
        }
    }
}
