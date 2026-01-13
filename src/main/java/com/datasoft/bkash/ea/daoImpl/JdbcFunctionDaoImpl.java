package com.datasoft.bkash.ea.daoImpl;

import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcFunctionDaoImpl implements JdbcFunctionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Value("${DB_NAME}")
    private String dbName;



    @Override
    public String getFunctionResult(String functionName, Map<String, Object> param) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withFunctionName(functionName).withCatalogName(dbName);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(param);
        return jdbcCall.executeFunction(String.class, mapSqlParameterSource);
    }

    @Override
    public Map<String, Object> getProcedureResult(String procedureName, Map<String, Object> inParam) {
//        inParam.put("userId", userService.getCurrentAuthenticatedUser().getId());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName).withCatalogName(dbName);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(inParam);
        Map<String, Object> r = jdbcCall.execute(mapSqlParameterSource);
        return r;
    }



    @Override
    public Map<String, Object> getProcedureResultSpetialCase(String procedureName, Map<String, Object> inParam) {
//        inParam.put("userId", userService.getCurrentAuthenticatedUser().getId());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName).withCatalogName("bkash_aml360_dev");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(inParam);
        Map<String, Object> r = jdbcCall.execute(mapSqlParameterSource);
        return r;
    }

}
