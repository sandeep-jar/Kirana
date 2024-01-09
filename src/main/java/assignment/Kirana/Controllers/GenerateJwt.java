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
// import assignment.Kirana.models.UserAuth;

@RestController
public class GenerateJwt {
    @Autowired JwtFunctions jwtHelper;
    SecretKey key = Jwts.SIG.HS256.key().build();
    // private final long expiry = 864_000_000;

    @PostMapping("/transactions/jwt")
    public ResponseEntity<?> genJwtToken(@RequestBody UserAuth authData) {
        String token = Jwts.builder().claim("userId", authData.getUserId()).signWith(key).compact();
        return ResponseEntity.ok(token);
    }

    @PostMapping("transactions/DecodeJwt")
    public ResponseEntity<?> decodeJwt(@RequestBody String jwtToken) {
        Claims authdata =
                Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
        String userId = authdata.get("userId", String.class);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/getSecretKey")
    public ResponseEntity<SecretKey> getSecretKey() {
        SecretKey key2 = Jwts.SIG.HS256.key().build();
        return ResponseEntity.ok(key2);
    }
    

    @GetMapping("/jwt/{userId}")
    public ResponseEntity<String> customJwt(@PathVariable String userId) {
        String token = jwtHelper.generateJwtForUser(userId);
        return ResponseEntity.ok(token);
    }
}
