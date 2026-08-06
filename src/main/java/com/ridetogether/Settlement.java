package com.ridetogether;

public class Settlement {
    private String from;
    private String to;
    private long amountInPaise;

    public Settlement(String from, String to, long amountInPaise) {
        if (amountInPaise <= 0) {
            throw new IllegalArgumentException("A settlement amount must be greater than zero");
        }
        this.from = from;
        this.to = to;
        this.amountInPaise = amountInPaise;
    }

    public void printSummary() {
        System.out.println(
                from + " pays " +
                        to + " " +
                        Money.format(amountInPaise));
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public long getAmountInPaise() {
        return amountInPaise;
    }
}
