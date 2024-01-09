package assignment.Kirana.Helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class JwtFunctions {
    Environment environment;
    public static final String SECRET =
            "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public JwtFunctions(Environment environment) {
        this.environment = environment;
    }

    public String genSecretString() {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        String key = Base64.getEncoder().encodeToString(keyBytes);
        return key;
    }

    public boolean verifyUser(String jwtToken, String user) {
        try {
            //            String stringKey = environment.getProperty("jwt.secretString");
            //            byte[] keyBytes = Base64.getDecoder().decode(stringKey);
            //            SecretKey key  = Keys.hmacShaKeyFor(keyBytes);

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

    public String getUserNameFromJwt(String jwtToken) {
        Claims claimData =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();
        String userId = claimData.get("userId", String.class);
        return userId;
    }

    public String generateJwtForUser(String userId) {
        //        String stringKey = environment.getProperty("jwt.secretString");
        //        System.out.println("stringKey is");
        //        System.out.println(stringKey);
        //        byte[] keyBytes  = Base64.getDecoder().decode(stringKey);
        //        SecretKey key  = Keys.hmacShaKeyFor(keyBytes);
        String token = Jwts.builder().claim("userId", userId).signWith(getSignKey()).compact();
        return token;
    }
}
