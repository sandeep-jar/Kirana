package assignment.Kirana.Security;

import assignment.Kirana.Exceptions.UserNotFound;
import assignment.Kirana.Repositories.UserRepository;
import assignment.Kirana.models.Entity.User;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user details by username for authentication.
     *
     * @param username The username for which to load user details.
     * @return UserDetails for the given username.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Authentication entry point 2 called");

        // Find user details by username from the repository
        Optional<User> userInfo = userRepository.findById(username);

        User user = userInfo.orElseThrow(() -> new UserNotFound("no such user"));
        System.out.println(user.getPassword());
        // Map User entity to CustomUserDetails or throw exception if user not found
        return new CustomUserDetails(user);
    }
}
