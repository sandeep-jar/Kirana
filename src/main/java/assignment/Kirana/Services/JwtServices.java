package assignment.Kirana.Services;

import assignment.Kirana.Configurations.RateLimitConfig;
import assignment.Kirana.Exceptions.RateLimitExceededException;
import assignment.Kirana.Exceptions.UserNotFound;
import assignment.Kirana.models.Entity.User;
import io.github.bucket4j.Bucket;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Service class for handling JWT (JSON Web Token) related operations. */
@Service
public class JwtServices {

    private final UserService userService;
    private final RateLimitConfig rateLimiter;

    public JwtServices(UserService userService, RateLimitConfig rateLimiter) {
        this.rateLimiter = rateLimiter;
        this.userService = userService;
    }

    /** The secret key used for JWT signing and verification. */
    @Value("${secretKey}")
    public String SECRET;

    /**
     * Retrieves the secret key for JWT signing and verification.
     *
     * @return SecretKey object
     */
    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    /**
     * Verifies if a given JWT has expired.
     *
     * @param jwt The JWT token to be checked for expiration.
     * @return true if the token has expired, false otherwise.
     */
    public boolean verifyExpiry(String jwt) {
        try {
            Claims tokenData =
                    Jwts.parser()
                            .verifyWith(getSignKey())
                            .build()
                            .parseSignedClaims(jwt)
                            .getPayload();
            Date expiry = tokenData.getExpiration();
            return expiry.before(new Date());
        } catch (Exception err) {
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * Verifies if a given JWT has the "admin" role.
     *
     * @param jwtToken The JWT token to be checked for the "admin" role.
     * @return true if the token has the "admin" role, false otherwise.
     */
    public boolean verifyAdmin(String jwtToken) {
        try {
            String admin = "admin";
            Claims tokenData =
                    Jwts.parser()
                            .verifyWith(getSignKey())
                            .build()
                            .parseSignedClaims(jwtToken)
                            .getPayload();
            if (tokenData.get("role", String.class).equals(admin)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception err) {
            return false;
        }
    }

    /**
     * Verifies if a given JWT has a specific user ID.
     *
     * @param jwtToken The JWT token to be checked for the specified user ID.
     * @param user The user ID to compare with the one in the JWT.
     * @return true if the token has the specified user ID, false otherwise.
     */
    public boolean verifyUser(String jwtToken, String user) {
        try {
            Claims tokenData =
                    Jwts.parser()
                            .verifyWith(getSignKey())
                            .build()
                            .parseSignedClaims(jwtToken)
                            .getPayload();
            if (tokenData.get("userId", String.class).equals(user)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception err) {
            System.out.println("Error occurred in parsing JWT");
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the user ID from a given JWT token.
     *
     * @param jwtToken The JWT token from which to extract the user ID.
     * @return The user ID extracted from the JWT token.
     */
    public String getUserIdFromJwt(String jwtToken) {
        Claims claimData =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();
        return claimData.get("userId", String.class);
    }

    /**
     * Generates a JWT token for a specified user ID with an expiration time of 2 days.
     *
     * @param userId The user ID for which to generate the JWT token.
     * @return The generated JWT token.
     */
    public String generateJwtForUser(String userId) {
        Bucket bucket = rateLimiter.resolveBucket(userId);
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("request quota exceeded , try after some time");
        }

        User user = userService.getUser(userId);
        // null is returned if user is not in database , if null throw error
        if (user == null) {
            throw new UserNotFound("such user doesn't exists");
        }

        // 2 days expiration time
        Date expirationDate = new Date(System.currentTimeMillis() + 100 * 60 * 60 * 24 * 2);

        return Jwts.builder()
                .expiration(expirationDate)
                .claim("userId", userId)
                .claim("role", user.getRole())
                .signWith(getSignKey())
                .compact();
    }
}
