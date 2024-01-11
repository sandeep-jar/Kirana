package assignment.Kirana.Controllers;

import assignment.Kirana.Services.TransactionsService;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.Response.ApiResponse;
import assignment.Kirana.models.TransactionRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransaction(
            @RequestHeader("Authorization") String AuthorizationHeader,
            @RequestBody TransactionRequest data) {
        String jwtToken = AuthorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(transactionsService.transactionHandler(jwtToken, data));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Transactions>> getAllTransactionData() {
        List<Transactions> transactionList = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactionList);
    }
}
