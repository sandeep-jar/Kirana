package assignment.Kirana.Validators;

import assignment.Kirana.Exceptions.InvalidAmountException;
import assignment.Kirana.Exceptions.InvalidCurrencyException;
import assignment.Kirana.Exceptions.UserNotFound;
import assignment.Kirana.Services.UserService;
import assignment.Kirana.models.Entity.User;
import assignment.Kirana.models.TransactionRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    private final UserService userService;
    private final CurrencyCodeValidator currencyCodeValidator;

    public TransactionValidator(
            UserService userService, CurrencyCodeValidator currencyCodeValidator) {
        this.userService = userService;
        this.currencyCodeValidator = currencyCodeValidator;
    }

    /**
     * @param requestData transaction data provided by user throws suitable exception for respective
     *     errors
     */
    public void validateTransactionDetails(TransactionRequest requestData) {
        // check if amount is valid
        if (requestData.getAmount() <= 0) {
            throw new InvalidAmountException("amount should be greater tha zero");
        }

        // checks for if user exists , userNotFound exception is thrown if user doesnt exists
        User sender = userService.getUser(requestData.getFrom());
        if (sender == null) {
            throw new UserNotFound("sender not found");
        }
        User reciever = userService.getUser(requestData.getTo());

        // exception if receiver is null
        if (reciever == null) {
            throw new UserNotFound("receiver not found");
        }

        String currency = requestData.getInitialCurrency();

        // exception if currency not in Exchange price api
        if (!currencyCodeValidator.isValidCurrencyCode(currency)) {
            throw new InvalidCurrencyException("this currency is not supported");
        }
    }
}
