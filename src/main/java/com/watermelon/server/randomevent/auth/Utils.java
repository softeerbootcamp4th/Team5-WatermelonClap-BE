package com.watermelon.server.randomevent.auth;

public class Utils {

    public static String parseAuthenticationHeaderValue(String headerValue) {
        return headerValue.substring(7);
    }

}
