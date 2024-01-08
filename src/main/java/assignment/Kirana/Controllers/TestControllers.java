package assignment.Kirana.Controllers;

import assignment.Kirana.Helpers.JwtFunctions;
import assignment.Kirana.Services.ExchangeRateService;
import assignment.Kirana.models.ExchangeRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestControllers {
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private JwtFunctions jwtHelper;
    @GetMapping("/ExchangeResponse")
    public ResponseEntity<ExchangeRatesResponse> getExchangeRateResponse(){
        ExchangeRatesResponse res = exchangeRateService.getRates();
        return ResponseEntity.ok(res);

    }

    @GetMapping("/userIdFromToken/{jwtToken}")
    public ResponseEntity<String> userFromToken(@PathVariable String jwtToken){
        String userId =  jwtHelper.getUserNameFromJwt(jwtToken);
        return ResponseEntity.ok(userId);
    }
}
