package com.ridetogether;

import java.time.LocalDate;

public class TripSummaryResponse {
    private final long id;
    private final String name;
    private final String destination;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TripSummaryResponse(
            long id,
            String name,
            String destination,
            LocalDate startDate,
            LocalDate endDate) {

        this.id = id;
        this.name = name;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}