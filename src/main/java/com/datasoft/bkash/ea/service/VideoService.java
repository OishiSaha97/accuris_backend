package com.datasoft.bkash.ea.service;


import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
//import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import static com.datasoft.bkash.ea.utils.Utils.isValidFilePath;


@Service
@Slf4j
public class VideoService {

    @Value("${sftp_server}")
    private String sftp_server_ip;
    @Value("${sftp_port}")
    private int sftp_server_port;
    @Value("${sftp_user}")
    private String sftp_server_userName;
    @Value("${sftp_pass}")
    private String sftp_server_pass;
    @Value("${sftp_path}")
    private String sftp_server_base_path;
    @Value("${sftp_file_download_path}")
    private String sftp_download_path;
    @Value("${sftp_base_dir}")
    private String sftp_base_dir;
    @Value("${attachment.location-query_module}")
    private String query_module_path;
    @Value("${attachment.location-investigation_module}")
    private String investigation_module_path;
    @Value("${attachment.location-approval_module}")
    private String approval_module_path;

    public ResponseEntity<ResourceRegion> getFullVideo(
            String type,
            String assessmentId,
            String videoId,
            HttpHeaders headers) {

        try {

            String remoteFilePath = "";
            if (type.matches("assesment")) {
                remoteFilePath = sftp_server_base_path + "videos/" + type + '/' + assessmentId + '/' + videoId + ".mp4";
            } else if (type.matches("irm")) {
                remoteFilePath = sftp_server_base_path + "irm/" + assessmentId + "/videos/" + videoId + ".mp4";
            } else if (type.matches("query")) {
                remoteFilePath = sftp_server_base_path + query_module_path + assessmentId + "/videos/" + videoId + ".mp4";

            }
            ChannelSftp channelSftp = setupJsch();
            channelSftp.connect();
            String tempFileName = videoId + ".mp4";
            File localtempFile = new File(tempFileName);
            OutputStream outputStream = null;

            outputStream = new FileOutputStream(localtempFile);
            channelSftp.get(remoteFilePath, outputStream);

            FileSystemResource resource = new FileSystemResource(localtempFile.getPath());
            File file = new File(resource.getPath());
            UrlResource video = new UrlResource("file:" + file.getAbsolutePath());
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
            outputStream.close();
            localtempFile.delete();
            ResourceRegion region = resourceRegion(video, headers);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaTypeFactory
                            .getMediaType(video)
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))

                    .body(region);
        } catch (FileNotFoundException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (SftpException e) {
            throw new RuntimeException(e);
        }
    }

    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
        long contentLength = video.contentLength();
        Optional<HttpRange> range = headers.getRange().stream().findFirst();
        if (range.isPresent()) {
            long start = range.get().getRangeStart(contentLength);
            long end = range.get().getRangeEnd(contentLength);
            long rangeLength = Math.min(1 * 1024 * 1024, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            val rangeLength = Math.min(1 * 1024 * 1024, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }

    public ResponseEntity<StreamingResponseBody> playMediaV01(
            String rangeHeader, String flPath) {
        if(isValidFilePath(flPath)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            StreamingResponseBody responseStream;
            String remoteFilePath = flPath;

//            remoteFilePath =sftp_server_base_path+"videos/" + type + '/' + assessmentId + '/' + videoId + ".mp4";
//            if(type.matches("assesment")) {
//                remoteFilePath =sftp_server_base_path+"videos/" + type + '/' + assessmentId + '/' + videoId + ".mp4";
//            }
//            else if(type.matches("irm")){
//                remoteFilePath = sftp_server_base_path+"irm/" + assessmentId + "/videos/" + videoId + ".mp4";
//            }
//            else if(type.matches("query")){
//                remoteFilePath = sftp_server_base_path+"query-module/" + assessmentId + "/videos/" + videoId + ".mp4";
//
//            }
//            else if(type.matches("investigation")){
//                remoteFilePath =sftp_server_base_path+"investigation-module/" + assessmentId + "/videos/" + videoId + ".mp4";
//
//            }
//            else if(type.matches("approval-module")){
//                remoteFilePath =sftp_server_base_path+"approval-module/" + assessmentId + "/videos/" + videoId + ".mp4";
//
//            }

            ChannelSftp channelSftp = setupJsch();
            channelSftp.connect();


            String[] pathStrings = flPath.split("/");
            Integer PathStringLength = pathStrings.length;
            String tempFileName = (pathStrings[PathStringLength - 1]).split("\\.")[0] + ".mp4";

            File localtempFile = new File(tempFileName);
            OutputStream outputStream = null;

            outputStream = new FileOutputStream(localtempFile);
            try{
                channelSftp.get(remoteFilePath, outputStream);
            } catch (Exception e){
                channelSftp.get(sftp_base_dir+remoteFilePath, outputStream);
            }

            FileSystemResource resource = new FileSystemResource(localtempFile.getPath());
            String filePathString = resource.getPath();
            Path filePath = Paths.get(filePathString);
            Long fileSize = Files.size(filePath);
            byte[] buffer = new byte[1024];
            final HttpHeaders responseHeaders = new HttpHeaders();

            if (rangeHeader == null) {
                responseHeaders.add("Content-Type", "video/mp4");
                responseHeaders.add("Content-Length", fileSize.toString());
                responseStream = os -> {
                    RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                    long pos = 0;
                    file.seek(pos);
                    while (pos < fileSize - 1) {
                        file.read(buffer);
                        os.write(buffer);
                        pos += buffer.length;
                    }
                    os.flush();
                    localtempFile.delete();

                };

                channelSftp.exit();
                channelSftp.disconnect();
                channelSftp.getSession().disconnect();
                outputStream.close();


                return new ResponseEntity<StreamingResponseBody>
                        (responseStream, responseHeaders, HttpStatus.OK);
            }

            String[] ranges = rangeHeader.split("-");
            Long rangeStart = Long.parseLong(ranges[0].substring(6));
            Long rangeEnd;
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }

            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }

            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            responseHeaders.add("Content-Type", "video/mp4");
            responseHeaders.add("Content-Length", contentLength);
            responseHeaders.add("Accept-Ranges", "bytes");
            responseHeaders.add("Content-Range", "bytes" + " " +
                    rangeStart + "-" + rangeEnd + "/" + fileSize);
            final Long _rangeEnd = rangeEnd;
            responseStream = os -> {
                RandomAccessFile file = new RandomAccessFile(filePathString, "r");

                long pos = rangeStart;
                file.seek(pos);
                while (pos < _rangeEnd) {
                    file.read(buffer);
                    os.write(buffer);
                    pos += buffer.length;
                }
                os.flush();
                localtempFile.delete();

            };
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
            outputStream.close();
//            localtempFile.delete();

            return new ResponseEntity<StreamingResponseBody>
                    (responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
        } catch (Exception e) {
            log.error("Error Message => {}, Error Reason => {}, StackTrace => {}", e.getMessage(), e.getCause(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<StreamingResponseBody> playAudioV01(
            String rangeHeader,
            String flPath) {
        if(isValidFilePath(flPath)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            StreamingResponseBody responseStream;
            String remoteFilePath = flPath;
            ;
//            if(type.matches("assesment")) {
//                remoteFilePath = sftp_server_base_path+"audios/" + type + '/' + assessmentId + '/' + videoId + ".mp3";
//            }
//            else if(type.matches("irm")){
//                remoteFilePath =sftp_server_base_path+"irm/" + assessmentId + "/audios/" + videoId + ".mp3";
//            }
//            else if(type.matches("query")){
//                remoteFilePath = sftp_server_base_path+"query-module/" + assessmentId + "/audios/" + videoId + ".mp3";
//
//            }
//
//            else if(type.matches("investigation")){
//                remoteFilePath = sftp_server_base_path+"investigation-module/" + assessmentId + "/audios/" + videoId + ".mp3";
//
//            }
//            else if(type.matches("approval-module")){
//                remoteFilePath =sftp_server_base_path+"approval-module/" + assessmentId + "/audios/" + videoId + ".mp3";
//
//            }

            ChannelSftp channelSftp = setupJsch();
            channelSftp.connect();

            String[] pathStrings = flPath.split("/");
            Integer PathStringLength = pathStrings.length;
            String tempFileName = (pathStrings[PathStringLength - 1]).split("\\.")[0] + ".mp3";

            File localtempFile = new File(tempFileName);
            OutputStream outputStream = null;

            outputStream = new FileOutputStream(localtempFile);
            try{
                channelSftp.get(remoteFilePath, outputStream);
            } catch (Exception e){
                channelSftp.get(sftp_base_dir+remoteFilePath, outputStream);
            }

            FileSystemResource resource = new FileSystemResource(localtempFile.getPath());
            String filePathString = resource.getPath();
            Path filePath = Paths.get(filePathString);
            Long fileSize = Files.size(filePath);
            byte[] buffer = new byte[1024];
            final HttpHeaders responseHeaders = new HttpHeaders();

            if (rangeHeader == null) {
                responseHeaders.add("Content-Type", "audio/mp3");
                responseHeaders.add("Content-Length", fileSize.toString());
                responseStream = os -> {
                    RandomAccessFile file = new RandomAccessFile(filePathString, "r");
                    long pos = 0;
                    file.seek(pos);
                    while (pos < fileSize - 1) {
                        file.read(buffer);
                        os.write(buffer);
                        pos += buffer.length;
                    }
                    os.flush();
                    localtempFile.delete();

                };

                channelSftp.exit();
                channelSftp.disconnect();
                channelSftp.getSession().disconnect();
                outputStream.close();


                return new ResponseEntity<StreamingResponseBody>
                        (responseStream, responseHeaders, HttpStatus.OK);
            }

            String[] ranges = rangeHeader.split("-");
            Long rangeStart = Long.parseLong(ranges[0].substring(6));
            Long rangeEnd;
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }

            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }

            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
            responseHeaders.add("Content-Type", "audio/mp3");
            responseHeaders.add("Content-Length", contentLength);
            responseHeaders.add("Accept-Ranges", "bytes");
            responseHeaders.add("Content-Range", "bytes" + " " +
                    rangeStart + "-" + rangeEnd + "/" + fileSize);
            final Long _rangeEnd = rangeEnd;
            responseStream = os -> {
                RandomAccessFile file = new RandomAccessFile(filePathString, "r");

                long pos = rangeStart;
                file.seek(pos);
                while (pos < _rangeEnd) {
                    file.read(buffer);
                    os.write(buffer);
                    pos += buffer.length;
                }
                os.flush();
                localtempFile.delete();

            };
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
            outputStream.close();
//            localtempFile.delete();

            return new ResponseEntity<StreamingResponseBody>
                    (responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
        } catch (Exception e) {
            log.error("Error Message => {}, Error Reason => {}, StackTrace => {}", e.getMessage(), e.getCause(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Properties config = new java.util.Properties();
        try {
            Session jschSession = jsch.getSession(sftp_server_userName, sftp_server_ip);
            jschSession.setPassword(sftp_server_pass);
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            jschSession.connect();
            return (ChannelSftp) jschSession.openChannel("sftp");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


}
