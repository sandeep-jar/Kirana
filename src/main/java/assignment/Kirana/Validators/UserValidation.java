package assignment.Kirana.Validators;

import assignment.Kirana.models.Entity.User;
import java.security.InvalidParameterException;
import org.springframework.stereotype.Component;

/*
this is validator class
includes functions for validating user related models
 */
@Component
public class UserValidation {

    public void validateUser(User user) {
        // validate name
        if (user.getName() == null) {
            throw new InvalidParameterException("give a valid name");
        }

        // mobile number should not be less than 10 digits
        if (user.getMobile() == null || user.getMobile().length() < 10) {
            throw new InvalidParameterException("mobile number too short or not provided");
        }

        // user can not assign himself as admin
        if (user.getRole().equals("admin")) {
            throw new InvalidParameterException(
                    "you can not assign yourself as admin , ask me to do it");
        }
    }
}
