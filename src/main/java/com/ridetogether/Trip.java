package com.ridetogether;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String name;
    private List<Expense> expenses;
    private List<String> members;

    public Trip (String name){
        this.name = name;
        this.expenses = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }

    public void printExpense(){
        System.out.println("Trip: "+ name);
        for (Expense expense : expenses){
            expense.printSummary();
        }
    }
    public double calculateTotalSpent(){
        double total=0;
        for (Expense expense : expenses){
            total+=expense.getAmount();
        }
        return total;
    }

    public void addMember(String member){
        members.add(member);
    }
    public double calculateEqualShare(){
        if(members.isEmpty()){
            throw new IllegalStateException("A trip needs at least one member");
        }
        return calculateTotalSpent()/members.size();
    }

}
