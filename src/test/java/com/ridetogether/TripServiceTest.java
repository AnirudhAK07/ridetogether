package com.ridetogether;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TripServiceTest {

    @Test
    void addsAndReturnsTripMembers() {
        TripService tripService = new TripService();

        long tripId = tripService.createTrip("Coorg Weekend Ride");
        tripService.addMember(tripId, "Rahul");

        assertEquals(List.of("Rahul"), tripService.getMembers(tripId));
    }
}