package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.service.VideoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/audio")
public class AudioController {


    private VideoService videoService;

    public AudioController(VideoService videoService) {
        this.videoService = videoService;
    }


    @GetMapping("/streamfile/**")
    public ResponseEntity<StreamingResponseBody> playAudioV01(
            @RequestHeader(value = "Range", required = false) String rangeHeader, HttpServletRequest request

    ) {
        return this.videoService.playAudioV01(rangeHeader, request.getRequestURI().replace("/web-backend/audio/streamfile",""));
    }


}
