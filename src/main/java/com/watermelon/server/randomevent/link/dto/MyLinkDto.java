package com.watermelon.server.randomevent.link.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyLinkDto {

    private String link;

    public static MyLinkDto create(String link) {
        return MyLinkDto.builder()
                .link(link)
                .build();
    }

    public static MyLinkDto createTestDto(){
        return create("https://www.google.com");
    }

}