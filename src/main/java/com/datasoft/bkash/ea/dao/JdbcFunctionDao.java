package com.datasoft.bkash.ea.dao;

import java.util.Map;

public interface JdbcFunctionDao {

    String getFunctionResult(String functionName, Map<String, Object> param);
    Map<String, Object> getProcedureResult(String procedureName, Map<String, Object> inParam);
    Map<String, Object> getProcedureResultFromSlave(String procedureName, Map<String, Object> inParam);
    Map<String, Object> getProcedureResultFromAml(String procedureName, Map<String, Object> inParam);
    Map<String, Object> getProcedureResultSpetialCase(String procedureName, Map<String, Object> inParam);
    Map<String, Object> getProcedureResultForEnquiryAgainst(String procedureName, Map<String, Object> inParam);
}
