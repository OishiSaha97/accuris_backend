package com.datasoft.bkash.ea.responseFilter;

import com.datasoft.bkash.ea.responseFilter.service.ResponseFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class ResponseFilterController {

    private final ResponseFilterService responseFilterService;

    @GetMapping("/heavy-requests/subscribe/{id}")
    public SseEmitter subscribe(@PathVariable String id) {
        return responseFilterService.subscribe(id);
    }


    @PostMapping("/heavy-requests/download")
    public void download(@RequestParam String id, @RequestParam String type, HttpServletResponse response) {
        responseFilterService.download(id, type, response);
    }
}
