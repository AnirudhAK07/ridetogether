package com.ridetogether;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TripServiceTest {

    @Autowired
    private TripService tripService;

    @Test
    void addsAndReturnsTripMembers() {
        long tripId = tripService.createTrip("Coorg Weekend Ride");
        tripService.addMember(tripId, "Rahul");

        assertEquals(List.of("Rahul"), tripService.getMembers(tripId));
    }

    @Test
    void calculatesSettlementsForATrip() {
        long tripId = tripService.createTrip("Coorg Weekend Ride");

        tripService.addMember(tripId, "Anirudh");
        tripService.addMember(tripId, "Sanjay");
        tripService.addMember(tripId, "Rahul");

        tripService.addExpense(tripId, "Fuel", "Anirudh", 1_500_000L);
        tripService.addExpense(tripId, "Hotel", "Sanjay", 800_000L);

        List<Settlement> settlements = tripService.calculateSettlements(tripId);

        assertEquals(2, settlements.size());
        assertEquals("Rahul", settlements.get(0).getFrom());
        assertEquals("Anirudh", settlements.get(0).getTo());
        assertEquals(733_333L, settlements.get(0).getAmountInPaise());
    }

    @Test
    void throwsNotFoundWhenTripDoesNotExist() {
        assertThrows(
                TripNotFoundException.class,
                () -> tripService.getMembers(999L));
    }

    @Test
    void letsAMemberChangeTheirPollVote() {
        long tripId = tripService.createTrip("Coorg Weekend Ride");

        tripService.addMember(tripId, "Anirudh");

        tripService.addPoll(
                tripId,
                "Which route should we take?",
                List.of("Mysuru route", "Chikmagalur route"));

        TripPoll poll = tripService.getPolls(tripId).get(0);

        long mysuruOptionId = poll.getOptions().get(0).getId();
        long chikmagalurOptionId = poll.getOptions().get(1).getId();

        tripService.voteOnPoll(
                tripId,
                "Anirudh",
                poll.getId(),
                mysuruOptionId);

        tripService.voteOnPoll(
                tripId,
                "Anirudh",
                poll.getId(),
                chikmagalurOptionId);

        TripPoll updatedPoll = tripService.getPolls(tripId).get(0);

        assertEquals(0, updatedPoll.getOptions().get(0).getVoteCount());
        assertEquals(1, updatedPoll.getOptions().get(1).getVoteCount());
    }
}