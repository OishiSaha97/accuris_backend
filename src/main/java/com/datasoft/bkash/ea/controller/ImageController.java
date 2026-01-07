package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.service.FileUploadService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/allfiles")
//@Api("Image view controller")
@Slf4j
public class ImageController {
    @Value("${image.location.root-dir}")
    private String imgDir;
    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping(path = "/images/{url}/**")
    //@ApiOperation(value = "Image upload", response = Blob.class)
    public byte[] image(HttpServletRequest httpServletRequest) {
        log.info("Requested Uri : {}", httpServletRequest.getRequestURI());
        log.info("Requested Header : {}", httpServletRequest.getHeaderNames());
        return fileUploadService.getImage(imgDir + httpServletRequest.getRequestURI());
    }
}
