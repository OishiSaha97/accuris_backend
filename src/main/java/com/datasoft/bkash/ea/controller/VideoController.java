package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.service.VideoService;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/video")
public class VideoController {


    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @GetMapping("/streamfile/**")
    public ResponseEntity<StreamingResponseBody> playMediaV01(
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            HttpServletRequest request
    ) {
        return this.videoService.playMediaV01(rangeHeader,request.getRequestURI().replace("/web-backend/video/streamfile",""));
    }


//    @GetMapping("/streamfile/{type}/{assessmentId}/{vidioId}")
//    public ResponseEntity<ResourceRegion> playMediaV01(
//            @PathVariable("type") String type,
//            @PathVariable("assessmentId") String assessmentId,
//            @PathVariable("vidioId") String videoId,
//            @RequestHeader  HttpHeaders headers
//    )
//    {
//
//        return this.videoService.getFullVideo(type, assessmentId, videoId, headers);
//
//
//
//
//    }


}
