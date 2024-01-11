package assignment.Kirana.Controllers;

import assignment.Kirana.Helpers.JwtFunctions;
import assignment.Kirana.Repositories.TransactionRepository;
import assignment.Kirana.Services.ExchangeRateService;
// import assignment.Kirana.Services.ReportService;
import assignment.Kirana.Services.TransactionsService;
import assignment.Kirana.models.Entity.Transactions;
import assignment.Kirana.models.ExchangeRatesResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** created just for experimenting and testing please ignore */
@RestController
public class TestControllers {
    @Autowired private ExchangeRateService exchangeRateService;
    @Autowired private JwtFunctions jwtHelper;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private TransactionsService transactionsService;

    // @Autowired
    // private ReportService reportService;
    @GetMapping("/ExchangeResponse")
    public ResponseEntity<ExchangeRatesResponse> getExchangeRateResponse() {
        ExchangeRatesResponse res = exchangeRateService.getRates();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/userIdFromToken/{jwtToken}")
    public ResponseEntity<String> userFromToken(@PathVariable String jwtToken) {
        String userId = jwtHelper.getUserNameFromJwt(jwtToken);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/transactionsListMonthly")
    public ResponseEntity<List<Transactions>> getTransactionsOfMonth() {
        List<Transactions> list = transactionRepository.findAllByMonthAndYear(1, 2024);
        return ResponseEntity.ok(list);
    }

    //    @GetMapping("/MonthlyAverage")
    //    public ResponseEntity<Double> MonthlyAverage(){
    //
    //        Double ans = reportService.monthlyAverage(1,2024);
    //        return ResponseEntity.ok(ans);
    //
    //
    //    }
}
