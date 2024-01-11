package assignment.Kirana.Controllers;

import assignment.Kirana.Services.JwtServices;
import assignment.Kirana.Services.UserService;
import assignment.Kirana.models.Entity.User;
import assignment.Kirana.models.Response.ApiResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller class for handling user-related operations. */
@RestController
public class UserController {

    private final JwtServices jwtServices;
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param jwtServices The service for handling JWT-related operations.
     * @param userService The service for handling user-related operations.
     */
    public UserController(JwtServices jwtServices, UserService userService) {
        this.userService = userService;
        this.jwtServices = jwtServices;
    }

    /**
     * Adds a new user to the system.
     *
     * @param user The user to be added.
     * @return ResponseEntity containing ApiResponse with the result of the user addition.
     */
    @PostMapping("/user/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return ResponseEntity containing a list of all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    /**
     * Retrieves a specific user by their user ID.
     *
     * @param userId The user ID for which the user is retrieved.
     * @return ResponseEntity containing the retrieved user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Generates a JWT token for user login.
     *
     * @param userId The user ID for which the JWT token is generated.
     * @return ResponseEntity containing the generated JWT token.
     */
    @GetMapping("/user/login/{userId}")
    public ResponseEntity<String> login(@PathVariable String userId) {
        String loginToken = jwtServices.generateJwtForUser(userId);
        return ResponseEntity.ok(loginToken);
    }
}
