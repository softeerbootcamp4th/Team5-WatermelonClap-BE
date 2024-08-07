package com.watermelon.server.auth.utils;

public class AuthUtils {

    public static String parseAuthenticationHeaderValue(String headerValue) {
        return headerValue.substring(7);
    }

}
