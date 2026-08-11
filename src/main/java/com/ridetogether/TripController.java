package com.ridetogether;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/demo/settlements")
    public List<SettlementResponse> getDemoSettlements() {
        Trip trip = new Trip("Coorg Weekend Ride");

        trip.addMember("Anirudh");
        trip.addMember("Sanjay");
        trip.addMember("Rahul");

        trip.addExpense(new Expense("Fuel", "Anirudh", 1_500_000L));
        trip.addExpense(new Expense("Hotel", "Sanjay", 800_000L));

        List<SettlementResponse> response = new ArrayList<>();

        for (Settlement settlement : trip.calculateSettlements()) {
            response.add(new SettlementResponse(
                    settlement.getFrom(),
                    settlement.getTo(),
                    Money.format(settlement.getAmountInPaise())));
        }

        return response;
    }

    @PostMapping
    public CreateTripResponse createTrip(@RequestBody CreateTripRequest request) {
        long tripId = tripService.createTrip(
                request.getName(),
                request.getDestination(),
                request.getStartDate(),
                request.getEndDate());

        return new CreateTripResponse(tripId, request.getName());
    }

    @PostMapping("/{tripId}/members")
    public void addMember(
            @PathVariable long tripId,
            @RequestBody AddMemberRequest request) {
        tripService.addMember(tripId, request.getName());
    }

    @GetMapping("/{tripId}/members")
    public List<String> getMembers(@PathVariable long tripId) {
        return tripService.getMembers(tripId);
    }

    @PostMapping("/{tripId}/expenses")
    public void addExpense(
            @PathVariable long tripId,
            @RequestBody AddExpenseRequest request) {

        tripService.addExpense(
                tripId,
                request.getDescription(),
                request.getPaidBy(),
                request.getAmountInPaise());
    }

    @GetMapping("/{tripId}/settlements")
    public List<SettlementResponse> getSettlements(@PathVariable long tripId) {
        List<SettlementResponse> response = new ArrayList<>();

        for (Settlement settlement : tripService.calculateSettlements(tripId)) {
            response.add(new SettlementResponse(
                    settlement.getFrom(),
                    settlement.getTo(),
                    Money.format(settlement.getAmountInPaise())));
        }

        return response;
    }

    @GetMapping("/{tripId}/expenses")
    public List<ExpenseResponse> getExpenses(@PathVariable long tripId) {
        List<ExpenseResponse> response = new ArrayList<>();

        for (Expense expense : tripService.getExpenses(tripId)) {
            response.add(new ExpenseResponse(
                    expense.getDescription(),
                    expense.getPaidBy(),
                    Money.format(expense.getAmountInPaise())));
        }

        return response;
    }

    @GetMapping
    public List<TripSummaryResponse> getTrips() {
        List<TripSummaryResponse> response = new ArrayList<>();

        for (Trip trip : tripService.getTrips()) {
            response.add(new TripSummaryResponse(
                    trip.getId(),
                    trip.getName(),
                    trip.getDestination(),
                    trip.getStartDate(),
                    trip.getEndDate()));
        }

        return response;
    }
}