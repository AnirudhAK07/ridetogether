package com.ridetogether;

public final class Money {
    private Money(){
        
    }

    public static String format(long paise) {
        boolean isNegative = paise < 0;
        long absolutePaise = Math.abs(paise);

        long rupees = absolutePaise / 100;
        long remainingPaise = absolutePaise % 100;

        String sign = isNegative ? "-" : "";

        return sign + "Rs. " + rupees + "." +
            String.format("%02d", remainingPaise);
}
}
