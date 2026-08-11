package com.ridetogether;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class Trip {
    private String name;
    private List<Expense> expenses;
    private List<String> members;

    public Trip(String name) {
        this.name = name;
        this.expenses = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        if (!members.contains(expense.getPaidBy())) {
            throw new IllegalArgumentException("The expense payer must be a member of the trip");
        }
        expenses.add(expense);
    }

    public void printExpense() {
        System.out.println("Trip: " + name);
        for (Expense expense : expenses) {
            expense.printSummary();
        }
    }

    // public double calculateTotalSpent() {
    // double total = 0;
    // for (Expense expense : expenses) {
    // total += expense.getAmount();
    // }
    // return total;
    // }

    public long calculateTotalSpentInPaise() {
        long totalInPaise = 0;

        for (Expense expense : expenses) {
            totalInPaise += expense.getAmountInPaise();
        }

        return totalInPaise;
    }

    public long calculateBaseShareInPaise() {
        if (members.isEmpty()) {
            throw new IllegalStateException("A trip needs at least one member");
        }
        return calculateTotalSpentInPaise() / members.size();
    }

    public long calculateRemainderInPaise() {
        if (members.isEmpty()) {
            throw new IllegalStateException(
                    "A trip needs at least one member.");
        }

        return calculateTotalSpentInPaise() % members.size();
    }

    public Map<String, Long> calculateExactBalances() {
        long baseShareInPaise = calculateBaseShareInPaise();
        long remainderInPaise = calculateRemainderInPaise();

        Map<String, Long> balances = new LinkedHashMap<>();

        for (int index = 0; index < members.size(); index++) {
            String member = members.get(index);

            long shareInPaise = baseShareInPaise;

            if (index < remainderInPaise) {
                shareInPaise += 1;
            }

            balances.put(member, -shareInPaise);
        }

        for (Expense expense : expenses) {
            String payer = expense.getPaidBy();
            long currentBalance = balances.get(payer);

            balances.put(
                    payer,
                    currentBalance + expense.getAmountInPaise());
        }

        return balances;
    }

    public List<Settlement> calculateSettlements() {
        Map<String, Long> balances = calculateExactBalances();
        List<Settlement> settlements = new ArrayList<>();

        while (true) {
            String debtor = null;
            String creditor = null;

            for (Map.Entry<String, Long> entry : balances.entrySet()) {
                String member = entry.getKey();
                long balance = entry.getValue();

                if (balance < 0 &&
                        (debtor == null ||
                                balance < balances.get(debtor))) {

                    debtor = member;
                }

                if (balance > 0 &&
                        (creditor == null ||
                                balance > balances.get(creditor))) {

                    creditor = member;
                }
            }

            if (debtor == null || creditor == null) {
                break;
            }

            long amountInPaise = Math.min(
                    -balances.get(debtor),
                    balances.get(creditor));

            settlements.add(
                    new Settlement(debtor, creditor, amountInPaise));

            balances.put(
                    debtor,
                    balances.get(debtor) + amountInPaise);

            balances.put(
                    creditor,
                    balances.get(creditor) - amountInPaise);
        }

        return settlements;
    }

    public void addMember(String member) {
        members.add(member);
    }

    public List<String> getMembers() {
        return List.copyOf(members);
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    // public double calculateEqualShare() {
    // if (members.isEmpty()) {
    // throw new IllegalStateException("A trip needs at least one member");
    // }
    // return calculateTotalSpent() / members.size();
    // }

    // public Map<String, Double> calculateBalances() {
    // double equalShare = calculateEqualShare();
    // Map<String, Double> balances = new LinkedHashMap<>();

    // for (String member : members) {
    // balances.put(member, -equalShare);
    // }

    // for(

    // Expense expense:expenses)
    // {
    // String payer = expense.getPaidBy();
    // double currentBalance = balances.get(payer);

    // balances.put(payer, currentBalance + expense.getAmount());
    // }return balances;}

}
