package assignment.Kirana.Controllers;

//import assignment.Kirana.Helpers.GenerateJwtHelper;
import assignment.Kirana.Helpers.JwtFunctions;
import assignment.Kirana.models.UserAuth;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
// import assignment.Kirana.models.UserAuth;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@RestController
public class GenerateJwt {
    SecretKey key = Jwts.SIG.HS256.key().build();
    // private final long expiry = 864_000_000;


    @PostMapping("/transactions/jwt")
    public ResponseEntity<?> genJwtToken(@RequestBody UserAuth authData){
        String token = Jwts.builder().claim("userId",authData.getUserId()).claim("paymentCompleted",authData.getPaymentCompleted()).signWith(key).compact();
        return ResponseEntity.ok(token);

    }

    @PostMapping("transactions/DecodeJwt")
    public ResponseEntity<?> decodeJwt(@RequestBody String jwtToken){
        Claims authdata = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
        String userId = authdata.get("userId", String.class);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/getSecret")
    public ResponseEntity<String> getSecret(){
        JwtFunctions jwtHelper  = new JwtFunctions();
        String key = jwtHelper.genSecretString();
        return ResponseEntity.ok(key);
    }
}
