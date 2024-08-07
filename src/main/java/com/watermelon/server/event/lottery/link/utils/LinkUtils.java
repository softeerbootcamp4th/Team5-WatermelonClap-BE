package com.watermelon.server.event.lottery.link.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

public class LinkUtils {

    private final static String BASE_URL = "http://localhost:8080";
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static  String makeUrl(String uri){
        return BASE_URL+"/"+uri;
    }

    public static String toBase62(String uuid) {
        UUID uuidObj = UUID.fromString(uuid);

        StringBuilder sb = new StringBuilder();

        //UUID 의 각각의 64비트를 ByteBuffer 에 삽입
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuidObj.getMostSignificantBits());
        byteBuffer.putLong(uuidObj.getLeastSignificantBits());

        //연산을 위해 ByteBuffer 로 BigInteger 를 만듦
        BigInteger bi = new BigInteger(1, byteBuffer.array());

        //62로 나누고 그 나머지에 대한 문자를 추가
        while (bi.compareTo(BigInteger.ZERO) > 0) {
            sb.append(BASE62.charAt(bi.mod(BigInteger.valueOf(62)).intValue()));
            bi = bi.divide(BigInteger.valueOf(62));
        }

        //거꾸로된 String 값을 반환
        return sb.reverse().toString();

    }

    public static String fromBase62(String base62) {
        //base62를 다시 거꾸로 변환
        StringBuilder sb = new StringBuilder(base62);
        base62 = sb.reverse().toString();

        //자릿수를 올리며 BigInteger 에 자릿수만큼 곱한 값을 더함.
        BigInteger bi = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;
        for (int i = 0; i < base62.length(); i++) {
            bi = bi.add(BigInteger.valueOf(BASE62.indexOf(base62.charAt(i))).multiply(multiplier));
            multiplier = multiplier.multiply(BigInteger.valueOf(62));
        }

        byte[] bytes = bi.toByteArray();
        byte[] uuidBytes = new byte[16];


        // 변환된 bytes 를 uuidBytes 에 옮김. 이 때 맨 앞에 0이 들어가있을 수 있는데(원인불명), 이 때는 자리수를 하나씩 옮겨야 함.
        int srcPos = 0;
        if(bytes[0]==0) srcPos = 1;
        System.arraycopy(bytes, srcPos, uuidBytes, 0, 16);

        //bytes 를 다시 64bit 씩 나누어서 UUID 로 생성 후 반환
        ByteBuffer bb = ByteBuffer.wrap(uuidBytes);
        long mostSigBits = bb.getLong();
        long leastSigBits = bb.getLong();
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        return uuid.toString();
    }
}
