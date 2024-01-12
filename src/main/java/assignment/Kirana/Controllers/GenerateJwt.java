package assignment.Kirana.Controllers;

// import assignment.Kirana.Helpers.GenerateJwtHelper;
import assignment.Kirana.Helpers.JwtFunctions;
import assignment.Kirana.models.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 *created for experimenting and testing , please ignore
 */
/** Created for experimenting . */
@RestController
public class GenerateJwt {

    @Autowired JwtFunctions jwtHelper;

    // Key used for signing JWT tokens
    SecretKey key = Jwts.SIG.HS256.key().build();

    /**
     * Generates a JWT token based on the provided user authentication data.
     *
     * @param authData The user authentication data.
     * @return ResponseEntity containing the generated JWT token.
     */
    @PostMapping("/transactions/jwt")
    public ResponseEntity<?> genJwtToken(@RequestBody UserAuth authData) {
        String token = Jwts.builder().claim("userId", authData.getUserId()).signWith(key).compact();
        return ResponseEntity.ok(token);
    }

    /**
     * Decodes a JWT token and retrieves the user ID.
     *
     * @param jwtToken The JWT token to be decoded.
     * @return ResponseEntity containing the user ID extracted from the JWT token.
     */
    @PostMapping("transactions/DecodeJwt")
    public ResponseEntity<?> decodeJwt(@RequestBody String jwtToken) {
        Claims authdata =
                Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
        String userId = authdata.get("userId", String.class);
        return ResponseEntity.ok(userId);
    }

    /**
     * Retrieves the current secret key used for JWT signing.
     *
     * @return ResponseEntity containing the current secret key.
     */
    @GetMapping("/getSecretKey")
    public ResponseEntity<SecretKey> getSecretKey() {
        SecretKey key2 = Jwts.SIG.HS256.key().build();
        return ResponseEntity.ok(key2);
    }

    /**
     * Generates a custom JWT token for the specified user ID using JwtFunctions.
     *
     * @param userId The user ID for which the JWT token is generated.
     * @return ResponseEntity containing the generated JWT token.
     */
    @GetMapping("/jwt/{userId}")
    public ResponseEntity<String> customJwt(@PathVariable String userId) {
        String token = jwtHelper.generateJwtForUser(userId);
        return ResponseEntity.ok(token);
    }
}
