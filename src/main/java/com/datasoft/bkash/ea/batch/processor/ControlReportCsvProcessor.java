package com.datasoft.bkash.ea.batch.processor;

import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import com.datasoft.bkash.ea.model.queryModule.ConfigParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ControlReportCsvProcessor {

    private final JdbcFunctionDao jdbcFunctionDao;
    private final ConfigParams configParams;
    private List<Map<String, Object>> results = new ArrayList<>();
    private Map<String, Object> spParams;
    private final String procedureName;
    public final List<String> files = new ArrayList<>();
    private final Integer userId;
    private final Integer batchSize;
    private Integer currentRow = 0;
    private final String baseDir;
    boolean isHeaderSet = false;
    String currentFile = "";
    List<String> headers = new ArrayList<>();
    List<String> columnHeaders = new ArrayList<>();
    public ControlReportCsvProcessor(JdbcFunctionDao jdbcFunctionDao, String procedureName, Integer batchSize, String baseDir,ConfigParams configParams,Integer userId, Map<String, Object> spParams){
        this.jdbcFunctionDao = jdbcFunctionDao;
        this.procedureName = procedureName;
        this.batchSize = batchSize;
        this.baseDir = baseDir;
        this.configParams = configParams;
        this.spParams = spParams;
        this.userId = userId;
    }

    public String run() {
        executeBatch();
        if(files.isEmpty()){
            return null;
        } else {
            ZipOutputStream zipOutput = null;
            try {
                String zipPath = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".zip");
                zipOutput = new ZipOutputStream(Files.newOutputStream(Paths.get(baseDir + zipPath)));
                for(int part = 0; part < files.size(); part++){
                    FileInputStream fileInputStream = null;
                    try {
                        File file = new File(baseDir+files.get(part));
                        fileInputStream = new FileInputStream(file);
                        zipOutput.putNextEntry(new ZipEntry("Part_"+(part+1)+".csv"));
                        zipOutput.write(IOUtils.toByteArray(fileInputStream));
                        zipOutput.closeEntry();
                        fileInputStream.close();
                        FileUtils.forceDelete(file);
                    } catch (Exception e){
                        log.error(e.getMessage(), e.getCause());
                        if(Objects.nonNull(fileInputStream)) fileInputStream.close();
                    }
                }
                zipOutput.close();
                return zipPath;
            } catch (Exception e) {
                log.error(e.getMessage(), e.getCause());
                if(Objects.nonNull(zipOutput)) {
                    try {
                        zipOutput.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return null;
    }


    public void executeBatch(){
        while(true){
            Map<String,Object> inParam=this.spParams;
            inParam.put("paramLimit", batchSize);
            inParam.put("paramOffset", currentRow);
            try{
                HashMap<String, Object> resultMap = new HashMap<>();
                Map<String,Object> procedureResult = jdbcFunctionDao.getProcedureResultFromSlave(procedureName, inParam);
                String result;

                if(procedureResult.containsKey("sequenceOfList")) {
                    if (!procedureResult.get("sequenceOfList").toString().isEmpty()) {
                        result = procedureResult.get("sequenceOfList").toString();
                        String[] resultSize = result.split("~");
                        for (int i = 0; i < resultSize.length; i++) {
                            resultMap.put(resultSize[i], procedureResult.get("#result-set-" + (i + 1)));
                        }
                    }
                }else{
                    resultMap.put("data", procedureResult.get("#result-set-1"));
                }

                if(!isHeaderSet){
                    if(procedureResult.containsKey("sequenceOfList")) {
                        headers = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("visibleValue").toString()).collect(Collectors.toList());
                        columnHeaders = ((List<Map<String, Object>>) resultMap.get("headers")).stream().map(map -> map.get("actualValue").toString()).collect(Collectors.toList());
                    }else{
                        headers=Arrays.asList(((String)procedureResult.get("headerColumn")).split(","));
                        columnHeaders=Arrays.asList(((String)procedureResult.get("keySet")).split(","));
                    }
                    isHeaderSet = true;
                }
                results = (List<Map<String, Object>>) resultMap.get("data");
                if(results.isEmpty()){
                    break;
                }
                this.makeCSV();
            } catch (Exception e){
                log.error(e.getMessage(), e.getCause());
                break;
            }
        }
    }

    public Map<String,Object> prepareParam(ConfigParams configParams){
        try {
            String param = configParams.getParam();
            if (Objects.nonNull(configParams.getParam()) && Objects.equals(configParams.getParam().toLowerCase(), "risk mitigation")) {
                param = "Risk Mitigation Download";
            }
            Map<String, Object> spParam = new LinkedHashMap<>();
            spParam.put("crIssueId", configParams.getCrIssueId());
            spParam.put("crId", configParams.getCrId());
            spParam.put("tableId", configParams.getTableId());
            spParam.put("param", param);
            spParam.put("userId", this.userId);
            spParam.put("executedDate", configParams.getExecutedDate());
            spParam.put("searchParam", configParams.getSearchParam());
            spParam.put("orderParam", configParams.getOrderParam());
            spParam.put("orderType", configParams.getOrderType());
            spParam.put("queryString", configParams.getQueryString());
            spParam.put("fieldName", configParams.getFieldName());
            spParam.put("isFilterValueString", "");
            return spParam;
        }catch (Exception e){
            log.error("Error Message =>{}, Error Reason =>{}, Stacktrace>{}",e.getMessage(),e.getCause(),e);
            return null;
        }
    }

    private String cleanCSV(String header) {
        return header.replace(",", ";");
    }

    private void makeCSV() throws IOException {
        currentFile = UUID.randomUUID().toString().replace("-", "").toUpperCase().concat(".csv");
        FileWriter csvWriter = new FileWriter(baseDir + currentFile, false);
        StringBuilder csvHeader = new StringBuilder();
        for(String header : headers){
            csvHeader.append(cleanCSV(header)).append(",");
        }
        csvWriter.append(csvHeader.toString());
        csvWriter.append("\n");
        for(Map<String, Object> result : results){
            for(String header : columnHeaders){
                csvWriter.append("\"").append((Objects.nonNull(result.get(header)) ? result.get(header).toString() : "")).append("\"").append(",");
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        files.add(currentFile);
        currentRow += results.size();
        results.clear();
    }
}
