package assignment.Kirana.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRatesResponse {

    private boolean success;

    private String terms;

    private String privacy;

    private long timestamp;

    private String date;

    private String base;

    private Map<String, Double> rates;
}
