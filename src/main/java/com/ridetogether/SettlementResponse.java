package com.ridetogether;

public class SettlementResponse {
    private final String from;
    private final String to;
    private final String amount;

    public SettlementResponse(String from, String to, String amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAmount() {
        return amount;
    }
}