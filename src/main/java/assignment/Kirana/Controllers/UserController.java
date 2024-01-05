package assignment.Kirana.Controllers;


import assignment.Kirana.Services.UserService;
import assignment.Kirana.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired

    private UserService userService;

    @PostMapping("/user")

    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            System.out.println("Error in adding user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Adding the user failed");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            System.out.println("Error occurred while getting all users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId){
        try {
            User user = userService.getUser(userId);
            if (user == null) {
                return ResponseEntity.status(404).body("no such user");
            } else {
                return ResponseEntity.ok(user);
            }
        }
        catch (Exception err){
            System.out.println("error occured in getUser");
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting user, try again");
        }

    }

}
