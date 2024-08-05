package com.watermelon.server.randomevent.link.utils;

public class LinkUtils {

    private final static String BASE_URL = "http://localhost:8080";

    public static  String makeUrl(String uri){
        return BASE_URL+"/"+uri;
    }

}
