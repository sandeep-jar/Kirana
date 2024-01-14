package assignment.Kirana.Security;

import assignment.Kirana.Configurations.RateLimitConfig;
import assignment.Kirana.Exceptions.InvalidJwtException;
import assignment.Kirana.Exceptions.NotAdminException;
import assignment.Kirana.Exceptions.TokenExpiredException;
import assignment.Kirana.Services.JwtServices;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Custom JWT authentication filter that integrates rate limiting. */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtServices jwtAuthService;


    /**
     * Constructor for JwtAuthenticationFilter.
     *
     * @param jwtAuthService Service for JWT authentication operations.
     * @param customUserDetailsService Custom user details service.
     */
    public JwtAuthenticationFilter(
            JwtServices jwtAuthService,
            CustomUserDetailsService customUserDetailsService
            ) {
        this.jwtAuthService = jwtAuthService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Internal method to perform JWT authentication and rate limiting for each request.
     *
     * @param request HTTP request.
     * @param response HTTP response.
     * @param filterChain Filter chain for processing the request.
     * @throws ServletException If an exception occurs during the filter chain execution.
     * @throws IOException If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, InvalidJwtException, TokenExpiredException {
        // Extract token and username from the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtAuthService.getUserIdFromJwt(token);
            // returns error message if jwt token is invalid
            if(username==null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid jwt Token");
                return;
            }
        }

        // Check if the user is authenticated and if the token is valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if(jwtAuthService.verifyExpiry(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("login session expired, login again");
                return;


            }
            else
            {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
