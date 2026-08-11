package com.ridetogether;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String paidBy;
    // private double amount;
    private long amountInPaise;

    protected Expense() {
    }

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

    public String getDescription() {
        return description;
    }
}
