package com.ridetogether;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "trip_polls")
public class TripPoll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "poll_id")
    private List<PollOption> options;

    protected TripPoll() {
        this.options = new ArrayList<>();
    }

    public TripPoll(String question, List<String> optionTexts) {
        this();

        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("A poll needs a question");
        }

        if (optionTexts == null || optionTexts.size() < 2) {
            throw new IllegalArgumentException(
                    "A poll needs at least two options");
        }

        this.question = question;

        for (String optionText : optionTexts) {
            options.add(new PollOption(optionText));
        }
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<PollOption> getOptions() {
        return List.copyOf(options);
    }

    public void vote(String voterName, long optionId) {
        PollOption selectedOption = null;

        for (PollOption option : options) {
            if (option.getId().equals(optionId)) {
                selectedOption = option;
                break;
            }
        }

        if (selectedOption == null) {
            throw new IllegalArgumentException(
                    "The selected option does not exist");
        }

        for (PollOption option : options) {
            option.removeVoter(voterName);
        }

        selectedOption.addVoter(voterName);
    }
}