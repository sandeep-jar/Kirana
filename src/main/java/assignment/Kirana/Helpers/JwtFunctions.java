package assignment.Kirana.Helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/** Used for learning about jwt , Experimenting and testing. */
@Service
public class JwtFunctions {

    private final Environment environment;

    /** Secret key for JWT signature. Loaded from application properties. */
    @Value("${secretKey}")
    public String SECRET;

    /**
     * Constructor for JwtFunctions, injecting the environment.
     *
     * @param environment The Spring environment.
     */
    public JwtFunctions(Environment environment) {
        this.environment = environment;
    }

    /**
     * Retrieves the signing key for JWT based on the provided secret.
     *
     * @return The SecretKey for JWT signing.
     */
    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a random secret string for JWT signing.
     *
     * @return A randomly generated secret string.
     */
    public String genSecretString() {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * Verifies if a given user matches the user in the JWT token.
     *
     * @param jwtToken The JWT token to be verified.
     * @param user The user ID to be checked against the token.
     * @return True if the user matches, false otherwise.
     */
    public boolean verifyUser(String jwtToken, String user) {
        try {
            Claims tokenData =
                    Jwts.parser()
                            .verifyWith(getSignKey())
                            .build()
                            .parseSignedClaims(jwtToken)
                            .getPayload();
            return tokenData.get("userId", String.class).equals(user);
        } catch (Exception err) {
            System.out.println("Error occurred in parsing JWT");
            System.out.println(err.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the user ID from the claims of a JWT token.
     *
     * @param jwtToken The JWT token to extract user ID from.
     * @return The user ID extracted from the token.
     */
    public String getUserNameFromJwt(String jwtToken) {
        Claims claimData =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();
        return claimData.get("userId", String.class);
    }

    /**
     * Generates a JWT token for a given user ID.
     *
     * @param userId The user ID for which the JWT token is generated.
     * @return The generated JWT token.
     */
    public String generateJwtForUser(String userId) {
        return Jwts.builder().claim("userId", userId).signWith(getSignKey()).compact();
    }
}
