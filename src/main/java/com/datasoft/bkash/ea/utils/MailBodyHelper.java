package com.datasoft.bkash.ea.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class MailBodyHelper {
    private String userName;
    private String senderName;
    private String phoneNumber;
    private String address;
    private String headline;
    private String verifyLink;
    private String buttonText;
    private int year;
    private Date date;
    private String[] mailList;
    private String[] nameList;
    private String reason;
    private String uniqueId;
    private Integer queryLogId;
    private Integer userId;
    private String[] investigationIds;
    private String[] crIssueIds;
}
