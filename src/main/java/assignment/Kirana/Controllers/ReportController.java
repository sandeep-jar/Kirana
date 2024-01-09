package assignment.Kirana.Controllers;

import assignment.Kirana.Services.ReportService;
import assignment.Kirana.models.MonthlyReport;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired private ReportService reportService;

    @GetMapping("/monthly/{month}/{userId}")
    public ResponseEntity<MonthlyReport> getMonthlyReport(
            @PathVariable String userId, @PathVariable int month) {
        LocalDateTime currentTime = LocalDateTime.now();
        int year = currentTime.getYear();
        MonthlyReport report = reportService.getMonthlyReportOfUser(month, year, userId);
        return ResponseEntity.ok(report);
    }
}
