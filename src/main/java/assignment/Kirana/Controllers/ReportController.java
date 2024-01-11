package assignment.Kirana.Controllers;

import assignment.Kirana.Configurations.RateLimitConfig;
import assignment.Kirana.Exceptions.InvalidJwtException;
import assignment.Kirana.Services.JwtServices;
import assignment.Kirana.Services.ReportService;
import assignment.Kirana.models.Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** The ReportController class handles HTTP requests related to generating monthly reports. */
@RestController
@RequestMapping("/report")
public class ReportController {
    private ReportService reportService;
    private JwtServices jwtServices;

    /**
     * Constructor to initialize the ReportController with required services.
     *
     * @param reportService An instance of ReportService for generating monthly reports.
     * @param jwtServices An instance of JwtServices for JWT token validation.
     */
    @Autowired
    public ReportController(
            ReportService reportService, JwtServices jwtServices, RateLimitConfig rateLimitConfig) {
        this.reportService = reportService;
        this.jwtServices = jwtServices;
    }

    /**
     * Retrieves the monthly report for a specified user and month.
     *
     * @param AuthorizationHeader The Authorization header containing the JWT token.
     * @param userId The ID of the user for whom the report is requested.
     * @param month The month for which the report is requested.
     * @return ResponseEntity<ApiResponse> containing the result of the monthly report request.
     * @throws InvalidJwtException If the provided JWT token is invalid.
     */
    @GetMapping("/monthly/{userId}")
    public ResponseEntity<ApiResponse> getMonthlyReport(
            @RequestHeader("Authorization") String AuthorizationHeader,
            @PathVariable String userId,
            @RequestParam int month)
            throws Exception {
        // Extract JWT token from Authorization header
        String jwtToken = AuthorizationHeader.replace("Bearer ", "");

        // Validate JWT token and return the ResponseEntity with the monthly report ApiResponse
        return ResponseEntity.ok(
                reportService.getMonthlyReportApiResponse(month, userId, jwtToken));
    }

    @GetMapping("/yearly/{userId}")
    public ResponseEntity<ApiResponse> getYearlyReport(
            @RequestHeader("Authorization") String AuthHeader,
            @PathVariable String userId,
            @RequestParam int year)
            throws Exception {
        String jwtToken = AuthHeader.replace("Bearer ", "");
        return ResponseEntity.ok(reportService.getYearlyReportApiResponse(year, userId, jwtToken));
    }
}
