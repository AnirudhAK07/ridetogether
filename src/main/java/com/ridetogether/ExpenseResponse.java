package com.ridetogether;

public class ExpenseResponse {
    private final String description;
    private final String paidBy;
    private final String amount;

    public ExpenseResponse(
            String description,
            String paidBy,
            String amount) {
        this.description = description;
        this.paidBy = paidBy;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public String getAmount() {
        return amount;
    }
}