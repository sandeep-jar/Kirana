package assignment.Kirana.Services;

import assignment.Kirana.models.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtServices {
    @Autowired private UserService userService;
    public static final String SECRET =
            "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

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

    public boolean verifyAdmin(String jwtToken) {
        try {
            // Define the expected admin role
            String admin = "admin";

            // Parse and verify the JWT token using the provided signing key
            Claims tokenData =
                    Jwts.parser()
                            .verifyWith(
                                    getSignKey()) // Assuming you have a method to get the signing
                            // key
                            .build()
                            .parseSignedClaims(jwtToken)
                            .getPayload();

            // Check if the role in the token matches the expected admin role
            if (tokenData.get("role", String.class).equals(admin)) {
                return true; // Token has admin role
            } else {
                return false; // Token does not have admin role
            }

        } catch (Exception err) {
            // Handle any exceptions that occur during token parsing or verification
            System.out.println("Error occurred in parsing JWT");
            System.out.println(err.getMessage());
            return false;
        }
    }

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
            System.out.println("error occured in parsing jwt");
            System.out.println(err.getMessage());
            return false;
        }
    }

    public String getUserIdFromJwt(String jwtToken) {
        Claims claimData =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();
        return claimData.get("userId", String.class);
    }

    public String generateJwtForUser(String userId) {

        User user = userService.getUser(userId);
        // 2days
        Date expirationDate = new Date(System.currentTimeMillis() + 100 * 60 * 60 * 24 * 2);

        return Jwts.builder()
                .expiration(expirationDate)
                .claim("userId", userId)
                .claim("role", user.getRole())
                .signWith(getSignKey())
                .compact();
    }
}
