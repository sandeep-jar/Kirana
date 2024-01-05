package assignment.Kirana.Controllers;


import assignment.Kirana.Services.TransactionsService;
import assignment.Kirana.models.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @PostMapping("/add")
    public ResponseEntity<String> addTransaction(@RequestHeader("Authorization") String AuthorizationHeader,@RequestBody Transactions data){
        try{
            String jwtToken = AuthorizationHeader.replace("Bearer ","");
            String secret = "secret";
            Key key =  Keys.hmacShaKeyFor(secretKey.getBytes());




            transactionsService.addTransaction(data);
            return  ResponseEntity.ok("transaction added successfully");
        }
        catch (Exception err){
            System.out.println("error occured while adding the transaction data");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("transaction failed try again later");
        }

    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Transactions>> getAllTransactionData() {
        try {
            List<Transactions> transactionList = transactionsService.getAllTransactions();
            return ResponseEntity.ok(transactionList);
        } catch (Exception e) {
            System.out.println("Error occurred while retrieving all transaction data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
