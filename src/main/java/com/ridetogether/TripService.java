package com.ridetogether;

import java.util.LinkedHashMap;
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
}