package assignment.Kirana.Controllers;

import assignment.Kirana.Services.JwtServices;
import assignment.Kirana.Services.UserService;
import assignment.Kirana.models.Entity.User;
import assignment.Kirana.models.Response.ApiResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final JwtServices jwtServices;
    private final UserService userService;

    public UserController(JwtServices jwtServices, UserService userService) {
        this.userService = userService;
        this.jwtServices = jwtServices;
    }

    @PostMapping("/user/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/login/{userId}")
    public ResponseEntity<String> login(@PathVariable String userId) {
        String loginToken = jwtServices.generateJwtForUser(userId);
        return ResponseEntity.ok(loginToken);
    }
}
