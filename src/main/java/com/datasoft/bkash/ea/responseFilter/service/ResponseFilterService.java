package com.datasoft.bkash.ea.responseFilter.service;

import com.datasoft.bkash.ea.responseFilter.dto.ResponseFilterDto;
import com.datasoft.bkash.ea.responseFilter.dto.SseResponseDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletResponse;

public interface ResponseFilterService {
    void request(ResponseFilterDto responseFilterDto);
    ResponseFilterDto prepareRequest(ResponseFilterDto responseFilterDto);
    void sendResponse(SseResponseDto sseResponseDto, boolean retry);
    SseEmitter subscribe(String id);

    void download(String id, String type, HttpServletResponse response);
}
