package com.ridetogether;

// import java.util.LinkedHashMap;
import java.util.List;
import java.time.LocalDate;
// import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public long createTrip(String tripName) {
        return createTrip(tripName, null, null, null);
    }

    public long createTrip(
            String tripName,
            String destination,
            LocalDate startDate,
            LocalDate endDate) {

        Trip trip = new Trip(
                tripName,
                destination,
                startDate,
                endDate);

        Trip savedTrip = tripRepository.save(trip);

        return savedTrip.getId();
    }

    public void addPoll(
            long tripId,
            String question,
            List<String> optionTexts) {

        Trip trip = getTripById(tripId);

        TripPoll poll = new TripPoll(question, optionTexts);

        trip.addPoll(poll);

        tripRepository.save(trip);
    }

    public List<TripPoll> getPolls(long tripId) {
        Trip trip = getTripById(tripId);

        List<TripPoll> polls = trip.getPolls();

        for (TripPoll poll : polls) {
            for (PollOption option : poll.getOptions()) {
                option.getVoteCount();
            }
        }

        return polls;
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
        tripRepository.save(trip);
    }

    public List<String> getMembers(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.getMembers();
    }

    private Trip getTripById(long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId));
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
        tripRepository.save(trip);
    }

    public List<Trip> getTrips() {
        return tripRepository.findAll();
    }

    public List<Settlement> calculateSettlements(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.calculateSettlements();
    }

    public List<Expense> getExpenses(long tripId) {
        Trip trip = getTripById(tripId);
        return trip.getExpenses();
    }

    public void voteOnPoll(
            long tripId,
            String memberName,
            long pollId,
            long optionId) {

        Trip trip = getTripById(tripId);

        trip.voteOnPoll(memberName, pollId, optionId);

        tripRepository.save(trip);
    }
}