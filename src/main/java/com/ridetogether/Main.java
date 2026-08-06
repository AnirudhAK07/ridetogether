package com.ridetogether;

public class Main {
    public static void main(String[] args) {
        // System.out.println("RideTogether is running!");
        // Expense fullExpense =new Expense("Fuel", "Anirudh", 15000);


        Trip coorgTrip = new Trip("Coorg weekend Ride");

        coorgTrip.addMember("Anirudh");
        coorgTrip.addMember("Sanjay");
        coorgTrip.addMember("Rahul");

        Expense fuelExpense = new Expense("Fuel", "Anirudh", 15000);
        Expense hotelExpense = new Expense("Hotel", "Sanjay", 8000);



        coorgTrip.addExpense(fuelExpense);
        coorgTrip.addExpense(hotelExpense);
        coorgTrip.printExpense();
        double totalSpent = coorgTrip.calculateTotalSpent();
        System.out.println("Total spent: Rs. " + totalSpent);

        double equalShare = coorgTrip.calculateEqualShare();
        System.out.println("Each member should pay Rs. " + equalShare);
    }
}