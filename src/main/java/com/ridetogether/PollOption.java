package com.ridetogether;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "poll_options")
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ElementCollection
    private Set<String> voters;

    protected PollOption() {
        this.voters = new LinkedHashSet<>();
    }

    public PollOption(String text) {
        this();

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "A poll option cannot be empty");
        }

        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getVoteCount() {
        return voters.size();
    }

    boolean hasVoter(String voterName) {
        return voters.contains(voterName);
    }

    void addVoter(String voterName) {
        voters.add(voterName);
    }

    void removeVoter(String voterName) {
        voters.remove(voterName);
    }
}