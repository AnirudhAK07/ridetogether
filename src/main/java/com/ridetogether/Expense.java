package com.ridetogether;

public class Expense {
    private String description;
    private String paidBy;
    // private double amount;
    private long amountInPaise;

    public Expense(String description, String paidBy, long amountInPaise) {
        this.description = description;
        this.paidBy = paidBy;
        // this.amount = amount;
        if (amountInPaise <= 0) {
            throw new IllegalArgumentException("An expense must be greater than Zero");
        }
        this.amountInPaise = amountInPaise;
    }

    public void printSummary() {
        System.out.println(paidBy + " paid " + Money.format(amountInPaise) + " for " + description);
    }

    public long getAmountInPaise() {
        return amountInPaise;
    }

    public String getPaidBy() {
        return paidBy;
    }
}
