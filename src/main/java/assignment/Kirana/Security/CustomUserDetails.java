package assignment.Kirana.Security;

import assignment.Kirana.models.Entity.User;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Custom implementation of UserDetails for representing user details in the Kirana application. */
public class CustomUserDetails implements UserDetails {

    User user;

    /**
     * Constructor to create CustomUserDetails from User entity.
     *
     * @param user User entity containing user details.
     */
    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return A collection of authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return The username.
     */
    @Override
    public String getUsername() {

        return this.user.getId();
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the user's account is valid (i.e., not expired), false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Account expiration logic can be added if needed
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return True if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Account locking logic can be added if needed
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return True if the user's credentials are valid (i.e., not expired), false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credential expiration logic can be added if needed
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true; // User enable/disable logic can be added if needed
    }
}
