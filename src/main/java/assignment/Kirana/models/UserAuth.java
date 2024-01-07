package assignment.Kirana.models;

public class UserAuth {
    private String userId;
    private String paymentCompleted;

    // No-arg constructor
    public UserAuth() {
    }

    // All-arg constructor
    public UserAuth(String userId, String paymentCompleted) {
        this.userId = userId;
        this.paymentCompleted = paymentCompleted;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(String paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
}
