package net.branium.utils;

import java.util.Random;

public class RandomGenerateUtils {

    /**
     * Generate random string with alphanumeric
     *
     * @param length the length of the string
     * @return the random string
     */
    public static String randomAlphanumericString(int length) {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

        StringBuilder randomString = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static String randomNumberString(int length) {
        String numberCharacters = "0123456789";

        StringBuilder randomNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(numberCharacters.length());
            char randomChar = numberCharacters.charAt(randomIndex);
            randomNumber.append(randomChar);
        }

        return randomNumber.toString();
    }
}
