package com.ridetogether;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final Map<Long, Trip> trips = new LinkedHashMap<>();
    private long nextTripId = 1;

    public long createTrip(String tripName) {
        long tripId = nextTripId;
        nextTripId++;

        Trip trip = new Trip(tripName);
        trips.put(tripId, trip);

        return tripId;
    }

    // public void addMember(long tripId, String memberName) {
    // Trip trip = trips.get(tripId);
    // if (trip == null) {
    // throw new IllegalArgumentException("Trip not found" + tripId);
    // }
    // trip.addMember(memberName);
    // }

    public void addMember(long tripId, String memberName) {
        Trip trip = getTripById(tripId);
        trip.addMember(memberName);
    }

    public List<String> getMembers(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.getMembers();
    }

    private Trip getTripById(long tripId) {
        Trip trip = trips.get(tripId);

        if (trip == null) {
            // throw new IllegalArgumentException("Trip not found: " + tripId);
            throw new TripNotFoundException(tripId);
        }

        return trip;
    }

    public void addExpense(
            long tripId,
            String description,
            String paidBy,
            long amountInPaise) {

        Trip trip = getTripById(tripId);

        Expense expense = new Expense(
                description,
                paidBy,
                amountInPaise);

        trip.addExpense(expense);
    }

    public List<Settlement> calculateSettlements(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.calculateSettlements();
    }

    public List<Expense> getExpenses(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.getExpenses();
    }
}