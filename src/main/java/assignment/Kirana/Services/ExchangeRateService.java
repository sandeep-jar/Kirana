package assignment.Kirana.Services;

import assignment.Kirana.models.ExchangeRatesResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/** Service class for fetching exchange rates from an external API. */
@Service
public class ExchangeRateService {

    private static final String API_URL = "https://api.fxratesapi.com/latest";

    private final RestTemplate restTemplate;

    /**
     * Constructor for ExchangeRateService.
     *
     * @param restTemplate The RestTemplate used for making HTTP requests.
     */
    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves the latest exchange rates from the external API.
     *
     * @return ExchangeRatesResponse containing the latest exchange rates.
     */
    @Cacheable(value = "currencyApi")
    public ExchangeRatesResponse getRates() {
        // Make GET request and parse response directly into ExchangeRatesResponse
        return restTemplate.getForObject(API_URL, ExchangeRatesResponse.class);
    }
}
