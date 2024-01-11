package assignment.Kirana.Validators;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


/*
has functions for validating transaction request details
 */
@Component
public class CurrencyCodeValidator {
    public static final Set<String> VALID_CURRENCY_CODES = new HashSet<>();

    static {
        VALID_CURRENCY_CODES.add("ADA");
        VALID_CURRENCY_CODES.add("AED");
        VALID_CURRENCY_CODES.add("AFN");
        VALID_CURRENCY_CODES.add("ALL");
        VALID_CURRENCY_CODES.add("AMD");
        VALID_CURRENCY_CODES.add("ANG");
        VALID_CURRENCY_CODES.add("AOA");
        VALID_CURRENCY_CODES.add("ARB");
        VALID_CURRENCY_CODES.add("ARS");
        VALID_CURRENCY_CODES.add("AUD");
        VALID_CURRENCY_CODES.add("AWG");
        VALID_CURRENCY_CODES.add("AZN");
        VALID_CURRENCY_CODES.add("BAM");
        VALID_CURRENCY_CODES.add("BBD");
        VALID_CURRENCY_CODES.add("BDT");
        VALID_CURRENCY_CODES.add("BGN");
        VALID_CURRENCY_CODES.add("BHD");
        VALID_CURRENCY_CODES.add("BIF");
        VALID_CURRENCY_CODES.add("BMD");
        VALID_CURRENCY_CODES.add("BNB");
        VALID_CURRENCY_CODES.add("BND");
        VALID_CURRENCY_CODES.add("BOB");
        VALID_CURRENCY_CODES.add("BRL");
        VALID_CURRENCY_CODES.add("BSD");
        VALID_CURRENCY_CODES.add("BTC");
        VALID_CURRENCY_CODES.add("BTN");
        VALID_CURRENCY_CODES.add("BWP");
        VALID_CURRENCY_CODES.add("BYN");
        VALID_CURRENCY_CODES.add("BYR");
        VALID_CURRENCY_CODES.add("BZD");
        VALID_CURRENCY_CODES.add("CAD");
        VALID_CURRENCY_CODES.add("CDF");
        VALID_CURRENCY_CODES.add("CHF");
        VALID_CURRENCY_CODES.add("CLF");
        VALID_CURRENCY_CODES.add("CLP");
        VALID_CURRENCY_CODES.add("CNY");
        VALID_CURRENCY_CODES.add("COP");
        VALID_CURRENCY_CODES.add("CRC");
        VALID_CURRENCY_CODES.add("CUC");
        VALID_CURRENCY_CODES.add("CUP");
        VALID_CURRENCY_CODES.add("CVE");
        VALID_CURRENCY_CODES.add("CZK");
        VALID_CURRENCY_CODES.add("DAI");
        VALID_CURRENCY_CODES.add("DJF");
        VALID_CURRENCY_CODES.add("DKK");
        VALID_CURRENCY_CODES.add("DOP");
        VALID_CURRENCY_CODES.add("DOT");
        VALID_CURRENCY_CODES.add("DZD");
        VALID_CURRENCY_CODES.add("EGP");
        VALID_CURRENCY_CODES.add("ERN");
        VALID_CURRENCY_CODES.add("ETB");
        VALID_CURRENCY_CODES.add("ETH");
        VALID_CURRENCY_CODES.add("EUR");
        VALID_CURRENCY_CODES.add("FJD");
        VALID_CURRENCY_CODES.add("FKP");
        VALID_CURRENCY_CODES.add("GBP");
        VALID_CURRENCY_CODES.add("GEL");
        VALID_CURRENCY_CODES.add("GGP");
        VALID_CURRENCY_CODES.add("GHS");
        VALID_CURRENCY_CODES.add("GIP");
        VALID_CURRENCY_CODES.add("GMD");
        VALID_CURRENCY_CODES.add("GNF");
        VALID_CURRENCY_CODES.add("GTQ");
        VALID_CURRENCY_CODES.add("GYD");
        VALID_CURRENCY_CODES.add("HKD");
        VALID_CURRENCY_CODES.add("HNL");
        VALID_CURRENCY_CODES.add("HRK");
        VALID_CURRENCY_CODES.add("HTG");
        VALID_CURRENCY_CODES.add("HUF");
        VALID_CURRENCY_CODES.add("IDR");
        VALID_CURRENCY_CODES.add("ILS");
        VALID_CURRENCY_CODES.add("IMP");
        VALID_CURRENCY_CODES.add("INR");
        VALID_CURRENCY_CODES.add("IQD");
        VALID_CURRENCY_CODES.add("IRR");
        VALID_CURRENCY_CODES.add("ISK");
        VALID_CURRENCY_CODES.add("JEP");
        VALID_CURRENCY_CODES.add("JMD");
        VALID_CURRENCY_CODES.add("JOD");
        VALID_CURRENCY_CODES.add("JPY");
        VALID_CURRENCY_CODES.add("KES");
        VALID_CURRENCY_CODES.add("KGS");
        VALID_CURRENCY_CODES.add("KHR");
        VALID_CURRENCY_CODES.add("KMF");
        VALID_CURRENCY_CODES.add("KPW");
        VALID_CURRENCY_CODES.add("KRW");
        VALID_CURRENCY_CODES.add("KWD");
        VALID_CURRENCY_CODES.add("KYD");
        VALID_CURRENCY_CODES.add("KZT");
        VALID_CURRENCY_CODES.add("LAK");
        VALID_CURRENCY_CODES.add("LBP");
        VALID_CURRENCY_CODES.add("LKR");
        VALID_CURRENCY_CODES.add("LRD");
        VALID_CURRENCY_CODES.add("LSL");
        VALID_CURRENCY_CODES.add("LTC");
        VALID_CURRENCY_CODES.add("LTL");
        VALID_CURRENCY_CODES.add("LVL");
        VALID_CURRENCY_CODES.add("LYD");
        VALID_CURRENCY_CODES.add("MAD");
        VALID_CURRENCY_CODES.add("MDL");
        VALID_CURRENCY_CODES.add("MGA");
        VALID_CURRENCY_CODES.add("MKD");
        VALID_CURRENCY_CODES.add("MMK");
        VALID_CURRENCY_CODES.add("MNT");
        VALID_CURRENCY_CODES.add("MOP");
        VALID_CURRENCY_CODES.add("MRO");
        VALID_CURRENCY_CODES.add("MUR");
        VALID_CURRENCY_CODES.add("MVR");
        VALID_CURRENCY_CODES.add("MWK");
        VALID_CURRENCY_CODES.add("MXN");
        VALID_CURRENCY_CODES.add("MYR");
        VALID_CURRENCY_CODES.add("MZN");
        VALID_CURRENCY_CODES.add("NAD");
        VALID_CURRENCY_CODES.add("NGN");
        VALID_CURRENCY_CODES.add("NIO");
        VALID_CURRENCY_CODES.add("NOK");
        VALID_CURRENCY_CODES.add("NPR");
        VALID_CURRENCY_CODES.add("NZD");
        VALID_CURRENCY_CODES.add("OMR");
        VALID_CURRENCY_CODES.add("OP");
        VALID_CURRENCY_CODES.add("PAB");
        VALID_CURRENCY_CODES.add("PEN");
        VALID_CURRENCY_CODES.add("PGK");
        VALID_CURRENCY_CODES.add("PHP");
        VALID_CURRENCY_CODES.add("PKR");
        VALID_CURRENCY_CODES.add("PLN");
        VALID_CURRENCY_CODES.add("PYG");
        VALID_CURRENCY_CODES.add("QAR");
        VALID_CURRENCY_CODES.add("RON");
        VALID_CURRENCY_CODES.add("RSD");
        VALID_CURRENCY_CODES.add("RUB");
        VALID_CURRENCY_CODES.add("RWF");
        VALID_CURRENCY_CODES.add("SAR");
        VALID_CURRENCY_CODES.add("SBD");
        VALID_CURRENCY_CODES.add("SCR");
        VALID_CURRENCY_CODES.add("SDG");
        VALID_CURRENCY_CODES.add("SEK");
        VALID_CURRENCY_CODES.add("SGD");
        VALID_CURRENCY_CODES.add("SHP");
        VALID_CURRENCY_CODES.add("SLL");
        VALID_CURRENCY_CODES.add("SOL");
        VALID_CURRENCY_CODES.add("SOS");
        VALID_CURRENCY_CODES.add("SRD");
        VALID_CURRENCY_CODES.add("STD");
        VALID_CURRENCY_CODES.add("SVC");
        VALID_CURRENCY_CODES.add("SYP");
        VALID_CURRENCY_CODES.add("SZL");
        VALID_CURRENCY_CODES.add("THB");
        VALID_CURRENCY_CODES.add("TJS");
        VALID_CURRENCY_CODES.add("TMT");
        VALID_CURRENCY_CODES.add("TND");
        VALID_CURRENCY_CODES.add("TOP");
        VALID_CURRENCY_CODES.add("TRY");
        VALID_CURRENCY_CODES.add("TTD");
        VALID_CURRENCY_CODES.add("TWD");
        VALID_CURRENCY_CODES.add("TZS");
        VALID_CURRENCY_CODES.add("UAH");
        VALID_CURRENCY_CODES.add("UGX");
        VALID_CURRENCY_CODES.add("USD");
        VALID_CURRENCY_CODES.add("UYU");
        VALID_CURRENCY_CODES.add("UZS");
        VALID_CURRENCY_CODES.add("VEF");
        VALID_CURRENCY_CODES.add("VND");
        VALID_CURRENCY_CODES.add("VUV");
        VALID_CURRENCY_CODES.add("WST");
        VALID_CURRENCY_CODES.add("XAF");
        VALID_CURRENCY_CODES.add("XAG");
        VALID_CURRENCY_CODES.add("XAU");
        VALID_CURRENCY_CODES.add("XCD");
        VALID_CURRENCY_CODES.add("XDR");
        VALID_CURRENCY_CODES.add("XOF");
        VALID_CURRENCY_CODES.add("XPD");
        VALID_CURRENCY_CODES.add("XPF");
        VALID_CURRENCY_CODES.add("XPT");
        VALID_CURRENCY_CODES.add("XRP");
        VALID_CURRENCY_CODES.add("YER");
        VALID_CURRENCY_CODES.add("ZAR");
        VALID_CURRENCY_CODES.add("ZMK");
        VALID_CURRENCY_CODES.add("ZMW");
        VALID_CURRENCY_CODES.add("ZWL");
    }


    /**
     *
     * @param currencyCode currency code provided by user in transaction request
     * @return true if valid currency else returns false
     */
    public  boolean isValidCurrencyCode(String currencyCode) {
        String uppercaseCode = currencyCode.toUpperCase();
        return VALID_CURRENCY_CODES.contains(uppercaseCode);
    }
}

