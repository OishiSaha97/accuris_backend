package com.datasoft.bkash.ea.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class SftpConfig {

    @Value("${sftp_server}")
    private String sftpHost;

    @Value("${sftp_port}")
    private int sftpPort;

    @Value("${sftp_user}")
    private String sftpUsername;

    @Value("${sftp_pass}")
    private String sftpPassword;

    @Value("${sftp_path}")
    private String sftpBasePath;
    @Value("${sftp_path}")
    private String sftp_server_base_path;
}
