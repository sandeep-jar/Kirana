package assignment.Kirana.Controllers;

import assignment.Kirana.Services.ExchangeRateService;
import assignment.Kirana.Services.JwtServices;
import assignment.Kirana.Services.TransactionsService;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.ExchangeRatesResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired private TransactionsService transactionsService;
    @Autowired private ExchangeRateService exchangeRateService;

    @Autowired private JwtServices jwtServices;

    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(
            @RequestHeader("Authorization") String AuthorizationHeader,
            @RequestBody Transactions data) {
        try {
            String jwtToken = AuthorizationHeader.replace("Bearer ", "");
            String userId = data.getFrom();
            // JwtFunctions jwtHelper = new JwtFunctions();
            boolean auth = jwtServices.verifyUser(jwtToken, userId);

            if (!auth) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("try logging in again");
            }
            String currencyType = data.getInitialCurrency().toUpperCase();
            data.setInitialCurrency(currencyType);
            data.setFinalCurrency("USD");
            ExchangeRatesResponse ExcahngeData = exchangeRateService.getRates();
            Map<String, Double> rates = ExcahngeData.getRates();
            Double convertedAmount = data.getAmount() / rates.get(currencyType);
            BigDecimal roundedAmount =
                    new BigDecimal(convertedAmount).setScale(2, RoundingMode.HALF_UP);
            data.setAmount(roundedAmount.doubleValue());
            transactionsService.addTransaction(data);
            return ResponseEntity.ok("transaction added successfully");
        } catch (Exception err) {
            System.out.println("error occured while adding the transaction data");
            System.out.println(err.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("transaction failed try again later");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transactions>> getAllTransactionData() {
        try {
            List<Transactions> transactionList = transactionsService.getAllTransactions();
            return ResponseEntity.ok(transactionList);
        } catch (Exception e) {
            System.out.println(
                    "Error occurred while retrieving all transaction data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
