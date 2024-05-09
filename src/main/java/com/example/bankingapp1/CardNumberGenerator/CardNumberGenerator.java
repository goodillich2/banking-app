package com.example.bankingapp1.CardNumberGenerator;

import java.security.SecureRandom;
import java.util.Random;

public class CardNumberGenerator {
    private static final Random RANDOM = new SecureRandom();

    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(RANDOM.nextInt(10));
        }
        return cardNumber.toString();
    }

    public static String generateCVV() {
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(RANDOM.nextInt(10));
        }
        return cvv.toString();
    }
}

