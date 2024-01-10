package assignment.Kirana;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableCaching
public class KiranaApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



    @Bean
    public RateLimiter rateLimiter() {
        RateLimiterConfig rateLimiterConfig = new RateLimiterConfig.Builder().limitForPeriod(2).limitRefreshPeriod(Duration.ofMinutes(1)).timeoutDuration(Duration.ofMillis(100)).build();
        return  RateLimiter.of("try",rateLimiterConfig);

    }

    public static void main(String[] args) {
        SpringApplication.run(KiranaApplication.class, args);
    }
}
