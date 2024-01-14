package assignment.Kirana.Controllers;

import assignment.Kirana.Services.TransactionsService;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.Response.ApiResponse;
import assignment.Kirana.models.TransactionRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller class for handling transaction-related operations. */
@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    /**
     * Constructor for TransactionsController.
     *
     * @param transactionsService The service for handling transaction-related operations.
     */
    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    /**
     * Adds a new transaction.
     *
     * @param data The transaction data to be added.
     * @return ResponseEntity containing ApiResponse with the result of the transaction addition.
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransaction(@RequestBody TransactionRequest data) {

        return ResponseEntity.ok(transactionsService.transactionHandler(data));
    }

    /**
     * Retrieves a list of all transactions.
     *
     * @return ResponseEntity containing a list of all transactions.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<Transactions>> getAllTransactionData() {
        List<Transactions> transactionList = transactionsService.getAllTransactions();
        return ResponseEntity.ok(transactionList);
    }
}
