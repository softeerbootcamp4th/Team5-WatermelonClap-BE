package com.watermelon.server.lottery.auth.utils;

public class AuthUtils {

    public static String parseAuthenticationHeaderValue(String headerValue) {
        return headerValue.substring(7);
    }

}
