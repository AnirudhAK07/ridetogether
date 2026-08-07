package com.ridetogether;

public class CreateTripResponse {
    private final long id;
    private final String name;

    public CreateTripResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}