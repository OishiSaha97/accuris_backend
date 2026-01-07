package com.datasoft.bkash.ea.model;

import org.springframework.beans.factory.annotation.Value;

public class Strings {
    /**
     * Date format for saving in elasticsearch
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    /**
     * Message shown when save successful
     */
    public static final String SAVE_SUCCESSFUL = "Save successfully";
    public static final String KPI_SAVE_SUCCESSFUL = "KPI save successfully";
    public static final String PLAN_SAVE_SUCCESSFUL = "Plan save successfully";
    public static final String SUPER_ADMIN = "SUPER ADMINISTRATOR";
    public static final String ASSIGNED_SUCCESSFUL = "Successfully Assigned";
    /**
     * Message shown when save is unsuccessful
     */
    public static final String SAVE_UNSUCCESSFUL = "Save unsuccessful";
    /**
     * Message shown when update successful
     */
    public static final String UPDATE_SUCCESSFUL = "Updated successful";
    /**
     * Error message shown when user is not found
     */
    public static final String USER_NOT_FOUND = "User not found";
    /**
     * Error message shown when user is Inactive
     */
    public static final String USER_INACTIVE = "User Is Inactive";
    /**
     * Error message shown when region is not found
     */
    public static final String ACCOUNT_NUMBER_NOT_FOUND = "Account number not found";
    /**
     * Error message shown when region is not found
     */
    public static final String REGION_NOT_FOUND = "Region not found";
    /**
     * Error message shown when question is not found
     */
    public static final String QUESTION_NOT_FOUND = "Question not found";
    /**
     * Error message shown when question is not found
     */
    public static final String QUESTION_OPTION_NOT_FOUND = "Question option not found";
    /**
     * Error message shown when question is not found
     */
    public static final String CONTROL_REPORT_NOT_FOUND = "Control report not found";
    /**
     * Error message shown when questionnaire is not found
     */
    public static final String QUESTIONNAIRE_NOT_FOUND = "Questionnaire not found";
    /**
     * Error message shown when question is not found
     */
    public static final String PUBLISHED_QUESTION_NOT_FOUND = "Published Question not found";
    /**
     * Error message shown when question is not found
     */
    public static final String ASSESSEE_NOT_FOUND = "Assessee not found";
    public static final String ASSESSMENT_NOT_FOUND = "Assessment not found";
    public static final String INVALID_APPROVE_STATUS = "Invalid approve status";
    public static final String KPI_NOT_FOUND = "KPI NOT FOUND";
    /**
     * Error message shown when an invalid action is being requested to be done
     */
    public static final String INVALID_ACTION_CUSTOM_MESSAGE = "Invalid Action: %s";
    /**
     * Error message shown when KPI is Published Successfully
     */
    public static final String PUBLISH_SUCCESSFULL = "KPI Published Successfully";
    public static final String EDD_PUBLISHED_SUCCESSFULLY = "EDD Published Successfully";
    public static final String PUBLISH_UNSUCCESSFULL = "KPI is Not Published!";
    public static final String KPI_ALREADY_PUBLISHED = "KPI ALREADY PUBLISHED";
    public static final String INVALID_USER_SELECTED = "INVALID USER SELECTED";
    public static final String KPI_TARGET_MISMATCH = "KPI TARGET MISMATCH";
    public static final String KPI_DATE_INVALID = "KPI CANNOT BE SET TO PREVIOUS DATE";
    public static final String PLAN_DATE_INVALID = "PLAN CANNOT BE SET TO PREVIOUS DATE";
    public static final String USER_NOT_ASSIGNED_TO_REGION = "USER NOT ASSIGNED TO REGION";
    public static final String USER_ASSIGNMENT_SUCCESSFUL = "USER IS ASSIGNED TO REGION";
    public static final String TASK_NOT_FOUND = "TASK NOT FOUND";
    public static final String DSO_NOT_FOUND = "DSO NOT FOUND";
    public static final String DSO_TR_NOT_FOUND = "DSO Transaction NOT FOUND";
    public static final String ALREADY_EXISTS = "Already Exists";
    public static final String DELETE_SUCCESSFUL = "Delete Successful";
    public static final String PLAN_NOT_FOUND = "Plan not found";
    public static final String MERCHANT_NOT_FOUND = "Merchant not found";
    public static final String IMAGE_NOT_FOUND = "Image not found";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String ROLE_NAME_ALREADY_EXIST = "Role name already exist";
    public static final String LOGIN_ID_ALREADY_EXIST = "Login id already exist";
    public static final String CONNECTION_DROP_OUT = "Connection Drops Out";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String USER_ASSIGNED_TO_REGION = "User is already assigned to region";
    public static final String UNASSIGNED_SUCCESSFUL = "Unassigned Successful";
    public static final String USER_NOT_CAO = "User is not CAO";
    public static final String START_YEAR_IS_GREATER_THAN_END_YEAR = "Start year is Greater than End year";
    public static final String YEARS_ARE_ABSENT = "Start Year and End year is not inserted";
    public static final String INVALID_YEAR_INPUT = "Start Year or End year is negative";
    public static final String DELETE_UNSUCCESSFUL = "Delete Unsuccessful";
    public static final String CAO_KPI_RECORDS_NOT_FOUND = "CAO KPI Records not found";
    public static final String DH_NOT_FOUND = "DH NOT FOUND";
    public static final String DAO_NOT_FOUND = "DAO NOT FOUND";
    public static final String AGENT_DOES_NOT_EXIST = "Agent does not exist";
    public static final String DH_DOES_NOT_EXIST = "DH does not exist";
    public static final String DSO_DOES_NOT_EXIST = "DSO does not exist";
    public static final String DAO_DOES_NOT_EXIST = "DAO does not exist";
    public static final String SECTION_ID_NOT_FOUND = "SECTION_ID_NOT_FOUND";
    public static final String SYSTEM_ADMIN = "SYSTEM ADMINISTRATOR";
    public static final String DEFAULT_SYSTEM_ADMIN_USER = "system_admin";
    public static final String DEFAULT_SUPER_ADMIN_USER = "super_admin";
    public static final String FCA_ADMIN = "FCA ADMINISTRATOR";
    public static final String FCA_SUPERVISOR = "FCA SUPERVISOR";
    public static final String SUBJECT_FOR_ROLE_ADD = "Approve the new role";
    public static final String SUBJECT_FOR_ROLE_UPDATE = "Approve the updated role";
    public static final String SUBJECT_FOR_USER_UPDATE = "Approve the updated user";
    public static final String CONTENT_FOR_ROLE_ADD = "Please approve the role bellow \nhttp://192.168.200.26:4200/role/inactive";
    public static final String CONTENT_FOR_ROLE_UPDATE = "Please approve the role bellow \nhttp://192.168.200.26:4200/role/inactive";
    public static final String CONTENT_FOR_USER_UPDATE = "A user profile is updated and pending for approval of Super User. Login id is %s. Please approve the user below \n%s/user/new";
    public static final String SUBJECT_FOR_USER_APPROVAL = "New user approval request";
    public static final String CONTENT_FOR_USER_APPROVAL = "A new user is pending in Bkash EA System with login id %s.\nPlease take necessary step for this user by clicking this link bellow \n" + "%s" + "/user/new.\n\nThanks\nBkash EA Team";
    public static final String SUBJECT_FOR_USER_ACTIVATION = "Your account in Bkash EA system has been activated";
    public static final String CONTENT_FOR_USER_ACTIVATION = "We're happy to let you know that we have approved your account for Bkash EA System.\nYour login id is " + "%s and password is %s.\nReady to get started? Sign in to your account now from bellow link. \n" + "%s" + "/login \n\nThanks\nBkash EA Team";
    public static final String CONTENT_FOR_FORGET_PASSWORD = "We're happy to let you know that a new password has been created for you.\nYour login id is " + "%s and new password is %s.\n Sign in to your account now from bellow link. \n" + "%s" + "/login \n\nThanks\nBkash EA Team";
    public static final String SUBJECT_FOR_NEW_USER = "A New account created in Bkash EA System";
    public static final String SUBJECT_FOR_FORGET_PASSWORD = "A New password is created";
    public static final String CONTENT_FOR_NEW_USER = "Welcome %s,\nYour account for EA has created and login id is " + "%s and password is %s but haven't activated it yet.\nIf everything is okay then your account will activate shortly. And you will get a confirmation message.\n\nThanks\nBkash EA Team";
    public static final String INVALID_DATA_INPUT_FORMAT = "Invalid Data Input format";
    public static final String MODIFIED_INFO_HISTORY_DOES_NOT_EXIST = "Modified info history does not exist";
    public static final String MODIFIED_INFO_HISTORY_DATA_INVALID = "Modified info history data invalid";
    public static final String PERMISSION_DENIED = "Permission Denied";
    public static final String CURRENT_PASSWORD_UNMATCHED = "Current password does not match";
    public static final String NEW_PASSWORD_UNMATCHED = "New Password and Confirm Password do not match";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully";
    public static final String PASSWORD_LENGTH_TOO_SHORT = "Password length should be at least 4 digits";
    /**
     * Error message shown when Image format for upload does not match
     */
    public static final String TYPE_MISMATCH = "Only image (.jpg, .jpeg, .png) formats allowed!";
    public static final String LOGIN_ID_DOES_NOT_MATCH = "Login id does not match";
    public static final String EMAIL_DOES_NOT_MATCH = "Email does not match";
    public static final String LOGIN_ID_OR_EMAIL_DOES_NOT_MATCH = "Login id or Email does not match";
    public static final String NEW_PASSWORD_SENT = "New password is sent to mail";
    public static final String INVALID_DATE_FORMAT = "Invalid Date Format";
    public static final String DATE_NOT_FOUND = "Date Not Found";
    public static final String SUBJECT_FOR_USER_ACTIVATE = "bKash EA System User Activate";
    public static final String SUBJECT_FOR_USER_INACTIVATE = "bKash EA System User Inactivate";
    public static final String USER_INACTIVATE_MAIL_BODY = "Your account access with LoginID %s is revoked from EA Portal System. Please contact the System Administrator to reinstall your privileges.";
    public static final String USER_ACTIVATE_MAIL_BODY = "Your account privileges with LoginID %s is reinstalled. You can login using you previous credentials.";
    //Query Module API Messages
    public static final String FILTER_KEYS_FETCHED = "Successfully Fetched Filter Keys.";
    public static final String FILTER_VALUES_FETCHED = "Successfully Fetched Filter Values.";
    public static final String FILTER_KEYS_FETCH_FAILED = "Filter Keys Fetch Failed.";
    public static final String FILTER_VALUES_FETCH_FAILED = "Filter Value Fetch Failed.";
    public static final String COMMON_PROPERTIES_FETCHED = "Successfully Fetched Common Properties";
    public static final String QUERY_VIEW_LIST = "Successfully Fetched Query View List";
    public static final String TRANSACTION_ANALYSIS = "Successfully Fetched Transaction Analysis List";
    public static final String TRANSACTION_ANALYSIS_TRNX = "Successfully Fetched All Trnx Type Transaction Analysis";
    public static final String FETCH_ENQUIRY_AGAINST = "Successfully Fetched Enquiry Against";
    public static final String QUERY_LOG = "Query Log";
    public static final String RESPOND_STATUS = "Query Respond";
    public static final String SUBMIT = "SUBMIT";
    public static final String CLOSE = "CLOSE";
    public static final String SAVE = "SAVE";
    public static final String UPDATE = "UPDATE";
    public static final String SAVE_AND_SUBMIT = "SAVE AND SUBMIT";
    public static final String UPDATE_AND_SUBMIT = "UPDATE AND SUBMIT";
    public static final String UPDATE_AND_CLOSE = "UPDATE AND CLOSE";
    @Value("${email_base_url}")
    private String emailBaseUrl;


}
