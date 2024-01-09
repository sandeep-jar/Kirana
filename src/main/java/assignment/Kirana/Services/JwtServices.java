package assignment.Kirana.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtServices {
    public static final String SECRET =
            "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return key;
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
        return Jwts.builder().claim("userId", userId).signWith(getSignKey()).compact();
    }
}
