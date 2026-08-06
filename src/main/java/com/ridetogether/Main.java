package com.ridetogether;

import java.util.Map;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // System.out.println("RideTogether is running!");
        // Expense fullExpense =new Expense("Fuel", "Anirudh", 15000);

        // System.out.println(Money.format(1_500_075L));
        // System.out.println(Money.format(-766_666L));
        Trip coorgTrip = new Trip("Coorg weekend Ride");

        coorgTrip.addMember("Anirudh");
        coorgTrip.addMember("Sanjay");
        coorgTrip.addMember("Rahul");

        Expense fuelExpense = new Expense("Fuel", "Anirudh", 1_500_000L);
        Expense hotelExpense = new Expense("Hotel", "Sanjay", 800_000L);

        coorgTrip.addExpense(fuelExpense);
        coorgTrip.addExpense(hotelExpense);
        coorgTrip.printExpense();
        // double totalSpent = coorgTrip.calculateTotalSpent();
        // System.out.println("Total spent: Rs. " + totalSpent);

        // double equalShare = coorgTrip.calculateEqualShare();
        // System.out.println("Each member should pay Rs. " + equalShare);

        // Map<String, Double> balances = coorgTrip.calculateBalances();

        // System.out.println("\n Balances");

        // for( Map.Entry<String , Double> balance : balances.entrySet()){
        // System.out.printf("%s: Rs. %.2f%n",balance.getKey(),balance.getValue());
        // }
        long exactTotalInPaise = coorgTrip.calculateTotalSpentInPaise();
        System.out.println("Exact total:" + Money.format(exactTotalInPaise));

        long baseShareInPaise = coorgTrip.calculateBaseShareInPaise();
        System.out.println("Base share: " + Money.format(baseShareInPaise));

        long remainderInPaise = coorgTrip.calculateRemainderInPaise();
        System.out.println("Remainder to distribute: " + remainderInPaise + " paise");

        Map<String, Long> exactBalances = coorgTrip.calculateExactBalances();

        System.out.println("\nExact balances:");

        for (Map.Entry<String, Long> balance : exactBalances.entrySet()) {

            System.out.println(
                    balance.getKey() + ": " +
                            Money.format(balance.getValue()));
        }
        List<Settlement> settlements = coorgTrip.calculateSettlements();

        System.out.println("\nSettlements:");

        for (Settlement settlement : settlements) {
            settlement.printSummary();
        }

    }
}