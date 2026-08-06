package com.ridetogether;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {

    @Test
    void formatsPositivePaise() {
        String formatted = Money.format(1_500_075L);

        assertEquals("Rs. 15000.75", formatted);
    }

    @Test
    void formatsNegativePaise() {
        String formatted = Money.format(-766_666L);

        assertEquals("-Rs. 7666.66", formatted);
    }
}