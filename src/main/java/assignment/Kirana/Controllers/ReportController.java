package assignment.Kirana.Controllers;

import assignment.Kirana.Services.JwtServices;
import assignment.Kirana.Services.ReportService;
import assignment.Kirana.models.MonthlyReport;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired private ReportService reportService;

    @Autowired private JwtServices jwtServices;

    @GetMapping("/monthly/{month}/{userId}")
    public ResponseEntity<?> getMonthlyReport(
            @RequestHeader("Authorization") String AuthorizationHeader,
            @PathVariable String userId,
            @PathVariable int month) {
        try {
            String jwtToken = AuthorizationHeader.replace("Bearer ", "");
            // JwtFunctions jwtHelper = new JwtFunctions();
            boolean isAdmin = jwtServices.verifyAdmin(jwtToken);
            boolean expired = jwtServices.verifyExpiry(jwtToken);
            if (expired) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("login expired login again");
            }
            if (isAdmin) {
                LocalDateTime currentTime = LocalDateTime.now();
                int year = currentTime.getYear();
                MonthlyReport report = reportService.getMonthlyReportOfUser(month, year, userId);
                return ResponseEntity.ok(report);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

        } catch (Exception err) {
            System.out.println("error occured in getMonthlyReport" + err.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
