package com.watermelon.server.randomevent.link.controller;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.link.service.LinkService;
import com.watermelon.server.randomevent.link.utils.LinkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static com.watermelon.server.randomevent.link.utils.LinkUtils.makeUrl;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("/link")
    public ResponseEntity<MyLinkDto> getMyLink(
            @Uid String uid
    ){
        return new ResponseEntity<>(linkService.getMyLink(uid), HttpStatus.OK);
    }

    @GetMapping("/link/{shortedUri}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortedUri
    ){
        HttpHeaders headers = new HttpHeaders();

        // 현재 서버의 URL을 기반으로 새로운 경로를 생성
        headers.add(HttpHeaders.LOCATION, makeUrl(linkService.getUrl(shortedUri)));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}