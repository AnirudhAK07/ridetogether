package com.ridetogether;

public class PollOptionResponse {
    private final long id;
    private final String text;
    private final int voteCount;

    public PollOptionResponse(
            long id,
            String text,
            int voteCount) {

        this.id = id;
        this.text = text;
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getVoteCount() {
        return voteCount;
    }
}