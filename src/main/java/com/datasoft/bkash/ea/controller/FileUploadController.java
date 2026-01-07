package com.datasoft.bkash.ea.controller;

import com.datasoft.bkash.ea.service.FileUploadService;
import com.jcraft.jsch.JSchException;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.datasoft.bkash.ea.utils.Utils.isValidFilePath;
@RestController
@RequestMapping("/file")
//@Api("File upload controller")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    //@ApiOperation(value = "Image upload", response = Map.class)
    public Map<String, Object> uploadFile(@RequestBody MultipartFile file, @RequestParam("id") String id, @RequestParam Optional<Integer> tempId) throws IOException, JSchException {
        return fileUploadService.upload(file, id, tempId);
    }

    @GetMapping(value = "/download", produces = {"application/pdf", "image/png", "image/jpeg", "audio/mp4", "audio/mpeg", "audio/vnd.wave", "application/octet-stream"})
    public ResponseEntity<byte[]> getFile(@RequestParam String filePath) throws IOException, JSchException {
        if(isValidFilePath(filePath)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            byte[] imagarr = fileUploadService.downloadFileFromSftpServer(filePath);
            return new ResponseEntity<>(imagarr, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @PostMapping("/uploadAutoApprove")
    //@ApiOperation(value = "Image upload", response = Map.class)
    public Map<String, Object> uploadAutoApprove(@RequestBody MultipartFile file, @RequestParam("id") String id, @RequestParam Integer tempId) throws IOException, JSchException {
        return fileUploadService.uploadAutoApprove(file, id, tempId);
    }

}
