package com.datasoft.bkash.ea.model.exception;

import com.datasoft.bkash.ea.model.Strings;

public enum ErrorCodes {

    A_0000(Strings.ASSESSEE_NOT_FOUND), U_0000(Strings.USER_NOT_FOUND), P_0000(Strings.PLAN_NOT_FOUND), R_0000(Strings.REGION_NOT_FOUND), Q_0000(Strings.QUESTION_NOT_FOUND), Q_0001(Strings.QUESTIONNAIRE_NOT_FOUND), PQ_0000(Strings.PUBLISHED_QUESTION_NOT_FOUND), T_0000(Strings.TASK_NOT_FOUND), DSOTR_0000(Strings.DSO_TR_NOT_FOUND), DH_0000(Strings.DH_NOT_FOUND), DA_0000(Strings.DAO_NOT_FOUND), DSO_0000(Strings.DSO_NOT_FOUND), IA_0010(Strings.INVALID_ACTION_CUSTOM_MESSAGE),
    MIH_0000(Strings.MODIFIED_INFO_HISTORY_DOES_NOT_EXIST), SEC_0010(Strings.SECTION_ID_NOT_FOUND), Q_0002(Strings.QUESTION_OPTION_NOT_FOUND), CR_0001(Strings.CONTROL_REPORT_NOT_FOUND), AC_001(Strings.ACCOUNT_NUMBER_NOT_FOUND), ASS_0000(Strings.ASSESSMENT_NOT_FOUND), KPI_0000(Strings.KPI_NOT_FOUND), IAS(Strings.INVALID_APPROVE_STATUS), I_0000(Strings.TYPE_MISMATCH), AG_0000(Strings.AGENT_DOES_NOT_EXIST),
    RO_0000(Strings.ROLE_NOT_FOUND),
    RO_1111(Strings.ROLE_NAME_ALREADY_EXIST),
    UL_1111(Strings.LOGIN_ID_ALREADY_EXIST),
    MER_0000(Strings.MERCHANT_NOT_FOUND),
    IM_0000(Strings.IMAGE_NOT_FOUND),
    PD_0000(Strings.PERMISSION_DENIED),
    DF_0000(Strings.INVALID_DATE_FORMAT),
    D_0000(Strings.DATE_NOT_FOUND)
    ;

    private String message;

    ErrorCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
