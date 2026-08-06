package com.ridetogether;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    @Test
    void createsExactSettlements() {
        Trip trip = new Trip("Coorg Weekend Ride");

        trip.addMember("Anirudh");
        trip.addMember("Sanjay");
        trip.addMember("Rahul");

        trip.addExpense(
                new Expense("Fuel", "Anirudh", 1_500_000L));

        trip.addExpense(
                new Expense("Hotel", "Sanjay", 800_000L));

        List<Settlement> settlements = trip.calculateSettlements();

        assertEquals(2, settlements.size());

        Settlement firstSettlement = settlements.get(0);
        assertEquals("Rahul", firstSettlement.getFrom());
        assertEquals("Anirudh", firstSettlement.getTo());
        assertEquals(733_333L, firstSettlement.getAmountInPaise());

        Settlement secondSettlement = settlements.get(1);
        assertEquals("Rahul", secondSettlement.getFrom());
        assertEquals("Sanjay", secondSettlement.getTo());
        assertEquals(33_333L, secondSettlement.getAmountInPaise());
    }
}