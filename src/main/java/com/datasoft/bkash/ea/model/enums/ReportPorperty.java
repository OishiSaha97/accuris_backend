package com.datasoft.bkash.ea.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum ReportPorperty {

    QUESTIONNAIRE {
        @Override
        public Map<String, Object> execute() {
            Map<String, Object> list=new HashMap<>();
            list.put("ReportName", "Assessment_form");
            list.put("PDFReportPathCons", "/reports/questionnaire/Questionnaire.jasper");
            list.put("DocReportPath", "/reports/questionnaire/QuestionnaireForDoc.jasper");
            list.put("SUBREPORT_DIR", "classpath:/reports/questionnaire/");
            list.put("PATH_IMG", "classpath:/image/");
            return list;
        }
    };
    public abstract Map<String, Object> execute();
}
