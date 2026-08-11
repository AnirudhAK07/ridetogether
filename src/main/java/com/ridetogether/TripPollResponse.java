package com.ridetogether;

import java.util.List;

public class TripPollResponse {
    private final long id;
    private final String question;
    private final List<PollOptionResponse> options;

    public TripPollResponse(
            long id,
            String question,
            List<PollOptionResponse> options) {

        this.id = id;
        this.question = question;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<PollOptionResponse> getOptions() {
        return options;
    }
}