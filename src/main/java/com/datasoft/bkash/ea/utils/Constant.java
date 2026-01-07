package com.datasoft.bkash.ea.utils;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final String DUPLICATE_KEY_MESSAGE = "The name '%s' that you have entered already exist!";
    public static final List<String> EXCEL_SUPPORTED_MIME_TYPES = Arrays.asList("application/vnd.ms-excel", "application/msexcel", "application/x-msexcel", "application/x-ms-excel", "application/x-excel",
            "application/x-dos_ms_excel", "application/xls", "application/xlsx", "xlsx", "application/x-xls", "application/octet-stream", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final String CSV_SUPPORTED_MIME_TYPE = "text/csv";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";


    /**
     * SP LIST
     *
     * @author Mahadi Hasan Joy
     * @Since 09-03-2021
     */
    public static final String PLANNING_DH_LIST = "sp_get_dhlist";
    public static final String PLANNING_DSO_LIST = "sp_get_dsolist";
    public static final String SP_GET_TRANSACTION_TYPE = "sp_qry_get_transaction_type";
    public static final String SP_GET_CPS_TRANSACTION_TYPE = "sp_qry_get_cps_transaction_type";
    public static final String SP_GET_MAPPED_TRANSACTION_TYPE = "sp_inv_get_mapping_transaction_type";
    public static final String SP_GET_MAPPING_TRANSACTION_TYPE = "sp_inv_upd_mapping_transaction_type";
    public static final String SP_GET_MAPPING_TRANSACTION_TYPE_TRX = "sp_inv_upd_mapping_transaction_type_trx";

    public static final String QUERY_INITIAL_SP = "sp_qry_get_query_initial_data";
    public static final String RECOMMENDATION_DETAILS_SP = "sp_rec_get_recommendation_details";
    public static final String RECOMMENDATION_REASSIGN_LIST_SP = "sp_rec_implementation_reassign_list";
    public static final String QUERY_COMMON_SP = "sp_qry_get_list";
    public static final String QUERY_ENQUIRY_AGAINST = "sp_qry_get_enquiry_against";
    public static final String QUERY_GET_VIEW_LIST = "sp_qry_get_query_view";
    public static final String QUERY_UPDATE_INVESTIGATION_STATUS = "sp_qry_upd_investigation_status";
    public static final String RECOMMENDATION_TRANSFER = "sp_transfer_rec_type";
    public static final String QUERY_UPDATE_APPROVAL_STATUS = "sp_apr_upd_approval_status";
    public static final String GET_ALL_NOTIFICATION = "sp_qry_get_notification";
    public static final String GET_GENERATE_NOTIFICATION = "sp_generate_notification";
    public static final String QUERY_ENQUIRY_AGAINST_HISTORY_CHECK = "sp_qry_get_enquiry_hostory_check";
    public static final String QUERY_ENQUIRY_AGAINST_FILE_UPLOAD = "sp_qry_enquiry_against_file_upload";
    public static final String QUERY_HISTORY_CHECK_REMARK_ADD = "sp_qry_ins_history_check_remark";
    public static final String VICTIM_FRAUDER_LIST = "sp_qry_get_victim_fraudster_list";
    public static final String INVESTIGARION_VIEW_LIST = "sp_qry_get_investigation_request";
    public static final String INVESTIGARION_GET_REQ = "sp_qry_get_ongoing_investigation";
    public static final String INVESTIGARION_MONITOR_LIST = "sp_qry_get_monitor_investigation";
    public static final String ASSIGNED_INVESTIGATION_LIST = "sp_qry_get_assigned_investigation";
    public static final String INVESTIGARION_OFFICER_LIST = "sp_qry_get_investigation_officer";
    public static final String EA_INVESTIGARION_OFFICER_LIST = "sp_qry_get_investigation_ea_officer";
    public static final String REASSIGN_CAO_LIST = "sp_qry_get_reassign_cao_list";
    public static final String APPROVAL_MEMO_ISSUER_LIST = "sp_apr_get_approvel_memo_issuer";
    public static final String INVESTIGARION_WRITE_REPORT_LIST = "sp_inv_get_report_writing_list";
    public static final String INVESTIGARION_TRANSACTION_ANALYSIS = "sp_inv_get_transaction_analysis";
    public static final String TRANSACTION_ANALYSIS_GET_ALL_TRNX = "sp_inv_get_all_trnx_type_transaction_analysis";
    //    public static final String  INVESTIGARION_TRANSACTION_ANALYSIS= "sp_inv_get_transaction_analysis_0616";
    public static final String INVESTIGARION_DETAIL_IRM = "sp_inv_get_investigation_details_irm";
    public static final String INVESTIGARION_DETAIL_FILTER_KEY = "sp_inv_get_investigation_details_filter_key";
    public static final String INVESTIGARION_DETAIL_FILTER_VALUE = "sp_inv_get_investigation_details_filter_value";
    public static final String APPROVAL_GET_QUERY_LIST = "sp_apr_get_approval_list";
    public static final String INVESTIGARION_DETAILS_VIEW = "sp_inv_get_investigation_details_view";
    public static final String SP_RECOMMENDATION_DETAILS = "sp_recommendation_details";
    public static final String APPROVAL_MEMO_List = "sp_apr_get_approval_memo_list";
    public static final String DELETE_MEMO = "sp_apr_del_approval_memo";
    public static final String INVESTIGATION_CHECK_INVOLVEMENT = "sp_inv_get_assigned_check";
    public static final String INV_INS_REC = "sp_inv_ins_recommendation";
    public static final String RECOMMENDATION_LIST = "sp_inv_get_recommendation_list";
    public static final String UPDATE_RECOMMENDATION = "sp_inv_upd_recommendation";
    public static final String HISTORY_DATA_UPLOAD_REPORT = "sp_qry_history_upload_check";
    public static final String TEAK_CHECK = "sp_qry_team_check";
    public static final String REPORT_LIST = "sp_qry_report_list";
    public static final String REPORT_DATA = "sp_qry_gen_all_report";
    public static final String REVERTED_IRM_LIST = "sp_qry_reverted_irm_list";
    public static final String SP_UNLOCK_RECORD = "sp_kill_txn_thread_id";
    public static final String ACCOUNT_QUERY = "sp_get_account_query";
    public static final String ACCOUNT_QUERY_MONITOR_LIST = "sp_qry_get_account_qry_monitor_list";
    public static final String ACCOUNT_QUERY_LIST = "sp_qry_get_account_qry_list";
    public static final String ACCOUNT_QUERY_Enquiry_HISTORY_LIST = "sp_qry_get_account_qry_enquiry_list";
    public static final String ACCOUNT_QUERY_PDF_DATA = "sp_qry_get_account_qry_pdf_data";
    public static final String ACCOUNT_HISTORY_CHECK_PDF_DATA = "sp_get_account_history_check_pdf_data";
    public static final String ACCOUNT_QUERY_RECORD_ADD = "sp_qry_ins_account_query_record";
    public static final String APPROVAL_MEMO_TYPE_LIST = "sp_get_approval_memo_type";
    public static final String APPROVAL_WORKFLOW_LIST = "sp_apwf_get_approval_workflow_list";

    public static final String APPROVER_LIST = "sp_apwf_get_approver_list";

    public static final String APPROVER_STATUS_CHANGE = "sp_apwf_upd_approver_status";
    public static final String APPROVAL_WORKFLOW_CONFIG_DATA = "sp_apwf_config_data";
    /*=================================== Approval Workflow End=====================================*/


    // Formatting Type
    public static final String DATE_FORMAT = "yyyy-MM-dd";


    // SQL Tokens
    public static final String AND = " AND ";
    public static final String LIKE = " LIKE ";
    public static final String OR = " OR ";
    public static final String DRAFT = "Draft";
    public static final String TEMPORARY = "Temporary";
    public static final String SP_GET_USER_LIST = "sp_get_user_list";
    public static final String SP_GET_USER_DETAIL = "sp_get_user_detail";
    public static final String SP_GEO_DIVISION_LIST = "sp_geo_division_list";

    public static final String SP_ORGANIZATION_DIVISION_LIST = "sp_organization_division_list";
    public static final String SP_GEO_DISTRICT_LIST = "sp_geo_district_list";
    public static final String SP_GEO_THANA_LIST = "sp_geo_thana_list";

    public static final String SP_INVOLVEMENT_TYPE_LIST = "sp_involvement_type_list";
    public static final String SP_INCIDENT_CATEGORY_LIST = "sp_incident_category_list";
    public static final String SP_QRY_CATEGORY_LIST = "sp_qry_category_list";
    public static final String SP_ENQUIRY_REQUIREMENT_LIST = "sp_enquiry_requirement_list";
    public static final String SP_INCIDENT_TYPE_LIST = "sp_incident_type_list";
    public static final String SP_GET_CONFIG_SUB_LIST = "sp_get_config_sub_list";
    public static final String SP_GET_TRANSACTION_HISTORY_DATA = "sp_get_transaction_history_data";
    public static final String SP_ORGANIZATION_DEPARTMENT_LIST = "sp_organization_department_list";
    public static final String SP_ORGANIZATION_TEAM_LIST = "sp_organization_team_list";

    public static final String SP_ORGANIZATION_LIST = "sp_organization_list";
    public static final String SP_ORGANIZATION_SUB_UNIT_LIST = "sp_organization_sub_unit_list";

    public static final String SP_ORG_UNIT_LIST = "sp_org_unit_list";
    public static final String SP_ORGANIZATION_WING = "sp_organization_wing_list";
    public static final String SP_QRY_GET_RECOMMENDATION_TYPE = "sp_qry_get_recommendation_type";
    public static final String SP_GET_TXN_CONFIG_DATA= "sp_get_txn_config_data";
    public static final String SP_ENQUIRY_AGAINST_DATA_UPLOAD= "sp_enquiry_against_data_upload";
    public static final String SP_UPLOAD_RECOMMENDATION_CONFIG= "sp_recommendation_upload_data";
    public static final String SP_QRY_GET_ENQUIRY_AGAINST= "sp_qry_get_enquiry_against";
    public static final String SP_QUERY_LOG_LIST= "sp_query_log_list";
    public static final String SP_QRY_GET_RECOMMENDATION= "sp_qry_get_recommendation";
    public static final String SP_QUERY_MONITOR_REASSIGN_LIST= "sp_query_monitor_reassign_list";
    public static final String SP_GET_ROLE_LIST = "sp_get_role_list";
    public static final String SP_SOURCE_TYPE_LIST = "sp_source_type_list";
    public static final String SP_GET_FILTER_KEY = "sp_qry_get_filter_key";
    public static final String SP_GET_ROLE_APPROVAL_LIST = "sp_get_role_approval_list";
    public static final String SP_GET_USER_APPROVAL_LIST = "sp_get_user_approval_list";
    public static final String APPROVAL_MEMO_DETAILS = "sp_apr_get_approval_memo";
    public static final String SP_INV_CATEGORY_LIST= "sp_inv_category_list";
    public static final String SP_GET_INV_SLA_LIST= "sp_get_inv_sla_list";
    public static final String SP_GET_INV_HOLIDAY_LIST= "sp_get_inv_holiday_list";

    public static final String SP_GET_TRANSACTION_HISTORY_DETAIL_DATA = "sp_get_transaction_history_detail_data";
    public static final String SP_ENTITY_TYPE_LIST = "sp_entity_type_list";
    public static final String SP_BUSINESS_TYPE_LIST = "sp_business_type_list";
    public static final String SP_PRODUCT_TYPE_LIST = "sp_product_type_list";
    public static final String SP_UNIVERSAL_ENTITY_CONFIG = "sp_universal_entity_config";
    public static final String SP_GET_UNMAPPED_BUSINESS_ACCOUNT_LIST = "sp_get_unmapped_business_account_list";
    public static final String SP_GET_INVESTIGATION_INFO = "sp_get_investigation_info";
    public static final String SP_UPD_MANUAL_MAP_ENTITY_ACCOUNT = "sp_upd_manual_map_entity_account";
    public static final String SP_GET_UNMAPPED_PRODUCT_LIST = "sp_get_unmapped_product_list";
    public static final String SP_GET_UNMAPPED_HISTORY_INFO = "sp_get_unmapped_history_info";
    public static final String SP_GET_UNMAPPED_ENTITY_ACCOUNT_LIST = "sp_get_unmapped_entity_account_list";
    public static final String SP_GET_UNMAPPED_COMBINATION_ACCOUNT = "sp_get_unmapped_combination_account";
    public static final String SP_GET_INV_ACCOUNT_SUMMARY_DATA = "sp_get_inv_account_summary_data";
    public static final String SP_UPLOAD_INVESTIGATION_CONFIG = "sp_investigation_upload_data";
    public static final String SP_EXPORT_TRANSCATION_ANALYSIS= "sp_export_transaction_analysis";
    public static final String SP_TRANSCATION_INWARD_OUTWARD_ANALYSIS= "sp_transaction_inward_outward_analysis";
    public static final String SP_TRANSCATION_SERIES_ANALYSIS= "sp_transaction_series_analysis";

    public static final String SP_GET_REMOTE_TRANSACTION_ANALYSIS= "sp_get_remote_transaction_analysis";
    public static final String SP_GET_INV_TRANSACTION_ANALYSIS_DATA= "sp_get_inv_transaction_analysis_data";
    public static final String SP_GET_TRANSACTION_LINK_ANALYSIS= "sp_get_transaction_link_analysis";
    public static final String SP_GET_BUSINESS_TRANSACTION_ANALYSIS= "sp_get_business_transaction_analysis";
    public static final String SP_GET_HISTORY_CHECK_ANALYSIS= "sp_get_history_check_analysis";
    public static final String SP_TRANSACTION_ACCOUNT_REGISTERING_ANALYSIS= "sp_transaction_account_registering_analysis";

    public static final String SP_UPLOAD_TRANSACTION_HISTORY = "sp_upload_transaction_history";
    public static final String SP_GET_APPLICATION_LOG_LIST = "sp_application_log_list";
    public static final String SP_GET_INV_REPORT_WRITING_DATA = "sp_get_inv_report_writing_data";
    public static final String SP_GET_INVESTIGATION_CONFIG_LIST = "sp_get_investigation_config_list";

}
