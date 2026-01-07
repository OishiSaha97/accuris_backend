package com.datasoft.bkash.ea.service;

import com.google.common.io.ByteStreams;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class FileUploadService {
    @Autowired
    private ImageService imageService;
//    @Autowired
//    private ModifiedInfoHistoryRepository modifiedInfoHistoryRepository;
//    @Autowired
//    private FileUploadDao fileUploadDao;

    @Value("${image.location.question-image}")
    public String photoPath;
    @Value("${image.location.relative-img-dir}")
    public String imageDir;
    @Value("${sftp_server}")
    public String sftp_server_ip;
    @Value("${sftp_port}")
    public int sftp_server_port;
    @Value("${sftp_user}")
    public String sftp_server_userName;
    @Value("${sftp_pass}")
    public String sftp_server_pass;
    @Value("${sftp_path}")
    public String sftp_server_base_path;
    @Value("${sftp_file_download_path}")
    public String sftp_download_path;

    //making sftp profile directory
    public static boolean makeProfileDirectory(ChannelSftp channelSftp, String dirPath, String profileId) throws IOException, JSchException {
        try {
            channelSftp.cd(dirPath);
            channelSftp.mkdir(profileId);
            return true;
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_FAILURE) {
                // The directory already exists, return true
                return true;
            } else {
                // Handle other exceptions by cleaning up and re-throwing
                e.printStackTrace();
                channelSftp.exit();
                channelSftp.disconnect();
                channelSftp.getSession().disconnect();
                throw new IOException("Error while making profile directory", e);
            }
        }
    }

    //making sftp question directory
    public static boolean makeQuestionDirectory(ChannelSftp channelSftp, String dirPath, String ID) throws IOException, JSchException {
        try {
            channelSftp.cd(dirPath);
            channelSftp.mkdir(ID);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
        }
        return false;
    }

    public static boolean writeFileToFtpServer(ChannelSftp channelSftp, String filePath, String fileName, MultipartFile file) throws IOException, JSchException {
        try {
            channelSftp.cd(filePath);
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            channelSftp.put(inputStream, fileName);
            inputStream.close();
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
        }
        return false;
    }

    //connecting to sftp server
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

    /**
     * Responsible to make session with ChannelExec
     * @author Mahadi Hasan Joy
     * @since 22-03-2021
     * @return
     * @throws JSchException
     */
    public Channel setupJschForExec() throws JSchException {
        JSch jsch = new JSch();
        Properties config = new java.util.Properties();
        try {
            Session jschSession = jsch.getSession(sftp_server_userName, sftp_server_ip);
            jschSession.setPassword(sftp_server_pass);
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            jschSession.connect();
            return jschSession.openChannel("exec");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public Map<String, Object> uploadFile(MultipartFile file, String ID, String docType) throws IOException, JSchException {
        Map<String, Object> map = new HashMap<>();
        String imageGetPath = "";
        String fullPath = "";
        String sftpPath = "";
        ChannelSftp channelSftp = setupJsch();
        try {

            channelSftp.connect();

            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (extension.toUpperCase().equals(".JPEG") || extension.toUpperCase().equals(".JPG") || extension.toUpperCase().equals(".PNG") || extension.toUpperCase().equals(".MP4") || extension.toUpperCase().equals(".MPEG") || extension.toUpperCase().equals(".MP3") || extension.toUpperCase().equals(".WAV") || extension.toUpperCase().equals(".PDF")) {

                switch (extension.toUpperCase()) {
                    case ".JPG":
                    case ".JPEG":
                    case ".PNG":
                        if (docType.equals("PROFILE")) {
                            fullPath = photoPath + "allfiles/images/profiles/" + ID + "/";
                            imageGetPath = imageDir + "images/profiles/" + ID + "/";
                            sftpPath = sftp_server_base_path + "allfiles/images/profiles/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeProfileDirectory(channelSftp, sftp_server_base_path + "allfiles/images/profiles/", ID);
                            }
                        } else {
                            fullPath = photoPath + "allfiles/images/questions/" + ID + "/";
                            imageGetPath = imageDir + "images/questions/" + ID + "/";
                            sftpPath = sftp_server_base_path + "allfiles/images/questions/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeQuestionDirectory(channelSftp, sftp_server_base_path + "allfiles/images/questions/", ID);
                            }
                        }
                        break;
                    case ".MP4":
                    case ".MPEG":
                        if (docType.equals("PROFILE")) {
                            fullPath = photoPath + "videos/profiles/" + ID + "/";
                            imageGetPath = imageDir + "videos/profiles/" + ID + "/";
                            sftpPath = sftp_server_base_path + "videos/profiles/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeProfileDirectory(channelSftp, sftp_server_base_path + "videos/profiles/", ID);
                            }
                        } else {
                            fullPath = photoPath + "videos/questions/" + ID + "/";
                            imageGetPath = imageDir + "videos/questions/" + ID + "/";
                            sftpPath = sftp_server_base_path + "videos/questions/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeQuestionDirectory(channelSftp, sftp_server_base_path + "videos/questions/", ID);
                            }
                        }
                        break;
                    case ".MP3":
                    case ".WAV":
                        if (docType.equals("PROFILE")) {
                            fullPath = photoPath + "audios/profiles/" + ID + "/";
                            imageGetPath = imageDir + "audios/profiles/" + ID + "/";
                            sftpPath = sftp_server_base_path + "audios/profiles/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeProfileDirectory(channelSftp, sftp_server_base_path + "audios/profiles/", ID);
                            }
                        } else {
                            fullPath = photoPath + "audios/questions/" + ID + "/";
                            imageGetPath = imageDir + "audios/questions/" + ID + "/";
                            sftpPath = sftp_server_base_path + "audios/questions/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeQuestionDirectory(channelSftp, sftp_server_base_path + "audios/questions/", ID);
                            }
                        }
                        break;
                    case ".PDF":
                        if (docType.equals("PROFILE")) {
                            fullPath = photoPath + "documents/profiles/" + ID + "/";
                            imageGetPath = imageDir + "documents/profiles/" + ID + "/";
                            sftpPath = sftp_server_base_path + "documents/profiles/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeProfileDirectory(channelSftp, sftp_server_base_path + "documents/profiles/", ID);
                            }
                        } else {
                            fullPath = photoPath + "documents/questions/" + ID + "/";
                            imageGetPath = imageDir + "documents/questions/" + ID + "/";
                            sftpPath = sftp_server_base_path + "documents/questions/" + ID + "/";
                            try {
                                channelSftp.cd(sftpPath);
                            } catch (Exception e) {
                                makeQuestionDirectory(channelSftp, sftp_server_base_path + "documents/questions/", ID);
                            }
                        }
                        break;
                }
                String fileName = file.getOriginalFilename().replaceAll("\\s+", "-");
//                fullPath = fullPath + "-" + ID + "-" + fileName;
                //invoke file upload function
                writeFileToFtpServer(channelSftp, sftpPath, "-" + ID + "-" + fileName, file);
                map.put("fileLocation", imageGetPath + "-" + ID + "-" + fileName);
                map.put("isSuccess", Boolean.TRUE);
                map.put("status", "Image save successful");
            } else {
                map.put("status", Boolean.FALSE);
                map.put("message", "Format not supported");
                log.error("Save unsuccessful with image name : {} and extension : {}", ID, extension);
                channelSftp.exit();
                channelSftp.disconnect();
                channelSftp.getSession().disconnect();
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
            map.put("isSuccess", Boolean.FALSE);
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
        }
//        finally {
//            channelSftp.exit();
//            channelSftp.disconnect();
//            channelSftp.getSession().disconnect();
//        }
        return map;
    }

    public Map<String, Object> upload(MultipartFile file, String loginId, Optional<Integer> tempId) throws IOException, JSchException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> objectMap = uploadFile(file, loginId, "PROFILE");
        if (Boolean.parseBoolean(objectMap.get("isSuccess").toString())) {
//            Image image = new Image();
//            image.setImagePath(objectMap.get("fileLocation").toString());
//            Image savedImage = imageService.save(image);
//            if (tempId.isPresent()) {
//                addImageToModifyInfoHistoryTable(tempId.get(), savedImage);
//            }
            map.put("isSuccess", Boolean.TRUE);
            map.put("status", "Image save successful");

        } else {
            map.put("isSuccess", Boolean.FALSE);
        }
        return map;
    }



    public byte[] getImage(String filePath) {
        log.info("Image path : {}", filePath);

        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
//            e.printStackTrace();
            log.error("Image error {}",e.getMessage());
        }
        return new byte[0];
    }

    public byte[] downloadFileFromSftpServer(String filePath) throws JSchException, IOException {
        ChannelSftp channelSftp = setupJsch();
        try {
            channelSftp.connect();
            String remoteFile = sftp_download_path + filePath;
            byte[] targetArray = ByteStreams.toByteArray(channelSftp.get(remoteFile));
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
            return targetArray;
        } catch (Exception e) {
            channelSftp.exit();
            channelSftp.disconnect();
            channelSftp.getSession().disconnect();
        }
//        finally {
//            channelSftp.exit();
//            channelSftp.disconnect();
//            channelSftp.getSession().disconnect();
//        }
        return null;
    }

    public Map<String, Object> uploadAutoApprove(MultipartFile file, String loginId, Integer tempId) throws IOException, JSchException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> objectMap = uploadFile(file, loginId, "PROFILE");
        if (Boolean.parseBoolean(objectMap.get("isSuccess").toString())) {
//            Image image = new Image();
//            image.setImagePath(objectMap.get("fileLocation").toString());
//            Image savedImage = imageService.save(image);
//            fileUploadDao.setUserImage(tempId,savedImage);

            map.put("isSuccess", Boolean.TRUE);
            map.put("status", "Image save successful");

        } else {
            map.put("isSuccess", Boolean.FALSE);
        }
        return map;
    }
}
