package com.datasoft.bkash.ea.daoImpl;

import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("slaveJdbcTemplate")
    private JdbcTemplate slaveJdbcTemplate;
    @Autowired
    @Qualifier("amlJdbcTemplate")
    private JdbcTemplate amlJdbcTemplate;

//    private final JdbcTemplate jdbcTemplate;

    @Value("${DB_NAME}")
    private String dbName;

    @Value("${SLAVE_DB_NAME}")
    private String slaveDb;

    @Value("${AML_DB_NAME}")
    private String amlDb;
    @Value("${slave-implementation-status}")
    private Boolean slaveImplementationStatus;

//    public JdbcFunctionDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

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
    public Map<String, Object> getProcedureResultFromSlave(String procedureName, Map<String, Object> inParam) {
//        inParam.put("userId", userService.getCurrentAuthenticatedUser().getId());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(slaveJdbcTemplate).withProcedureName(procedureName).withCatalogName(slaveDb);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(inParam);
        Map<String, Object> r = jdbcCall.execute(mapSqlParameterSource);
        return r;
    }

    @Override
    public Map<String, Object> getProcedureResultFromAml(String procedureName, Map<String, Object> inParam) {
//        inParam.put("userId", userService.getCurrentAuthenticatedUser().getId());
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(amlJdbcTemplate).withProcedureName(procedureName).withCatalogName(amlDb);
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

    @Override
    public Map<String, Object> getProcedureResultForEnquiryAgainst(String procedureName, Map<String, Object> inParam) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName).withCatalogName(dbName);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(inParam);
        Map<String,Object> resultMap = jdbcCall.execute(mapSqlParameterSource);
        return resultMap;
    }
}
