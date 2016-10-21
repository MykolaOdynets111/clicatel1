package com.touch.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by sbryt on 6/9/2016.
 */
public class StringUtils {
    public static String generateRandomString(int countOfSymbols) {
        return RandomStringUtils.randomAlphanumeric(countOfSymbols).toLowerCase();
    }

    public static String generateEmail() {
        return "UserEmail_" + generateRandomString(15) + "@fake.perfectial.com";
    }

    public static String generateRandomIntegerString(int countOfSymbols) {
        return RandomStringUtils.randomNumeric(countOfSymbols);
    }
}
