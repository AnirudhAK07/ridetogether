package com.ridetogether;

public class Expense {
    private String description;
    private String paidBy;
    private double amount;

    public Expense(String description , String paidBy , double amount){
        this.description = description;
        this.paidBy = paidBy;
        this.amount = amount;
    }
    public void printSummary(){
        System.out.println(paidBy + " paid Rs. " + amount + " for " + description);
    }
    public double getAmount() 
    {
    return amount;
    }
}
