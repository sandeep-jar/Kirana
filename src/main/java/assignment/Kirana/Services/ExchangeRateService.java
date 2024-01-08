package assignment.Kirana.Services;

import assignment.Kirana.models.ExchangeRatesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ExchangeRateService {

    private static final String API_URL = "https://api.fxratesapi.com/latest";

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRatesResponse getRates() {
        try {
            // Make GET request and parse response directly into ExchangeRatesResponse
            return restTemplate.getForObject(API_URL, ExchangeRatesResponse.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
