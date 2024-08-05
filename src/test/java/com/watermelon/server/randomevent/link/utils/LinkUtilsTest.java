package com.watermelon.server.randomevent.link.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LinkUtilsTest {

    @Test
    void test(){

        //given
        String uuid = UUID.randomUUID().toString();

        //when
        String encoded = LinkUtils.toBase62(uuid);
        String decoded = LinkUtils.fromBase62(encoded);

        //then
        assertEquals(uuid, decoded);

    }

}