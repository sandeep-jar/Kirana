package assignment.Kirana.Helpers;

import assignment.Kirana.models.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

public class JwtFunctions {
    @Value("${jwt.secretString}")
    private String stringKey;
    public String genSecretString(){
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        String key  = Base64.getEncoder().encodeToString(keyBytes);
        return  key;
    }
    public boolean verifyUser(String jwtToken, String user){
        try{
            byte[] keyBytes = Base64.getDecoder().decode(stringKey);
            SecretKey key  = Keys.hmacShaKeyFor(keyBytes);

            Claims tokenData = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
            if(tokenData.get("userId",String.class).equals(user)){
                return true;
            }
            else {
                return false;
            }

        }
        catch (Exception err){
            System.out.println("error occured in parsing jwt");
            System.out.println(err.getMessage());
            return false;
        }
    }
}
