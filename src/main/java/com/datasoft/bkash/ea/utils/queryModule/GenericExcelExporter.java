package com.datasoft.bkash.ea.utils.queryModule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.WordUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedCaseInsensitiveMap;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GenericExcelExporter {
    private final XSSFWorkbook workbook;
    List<LinkedCaseInsensitiveMap> procedureResult = new ArrayList<>();
    private XSSFSheet sheet;
    private String param;
    private Integer skipIndex1;
    private Integer skipIndex2;
    private Integer skipIndex3;
    private Integer skipIndex4;
    private Integer skipIndex5;
    private Integer skipIndex6;
    private Integer skipIndex7;

    @Value("${heavy-requests.file-path}")
    private String reportFilePath;

    @Value("${app-name}")
    private String appName;

    public GenericExcelExporter(List<LinkedCaseInsensitiveMap> procedureResult) {
        this.procedureResult = procedureResult;
        workbook = new XSSFWorkbook();
    }

    public GenericExcelExporter(List<LinkedCaseInsensitiveMap> procedureResult,String param) {
        this.procedureResult = procedureResult;
        workbook = new XSSFWorkbook();
        this.param=param;
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
            sheet.autoSizeColumn(columnCount);
            Cell cell = row.createCell(columnCount);
            if (value instanceof Integer) {
                cell.setCellValue(new BigDecimal((Integer) value).toString());
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            } else if (value instanceof Double) {
                cell.setCellValue(new BigDecimal((Double) value).toString());
            } else if (value instanceof BigDecimal) {
                cell.setCellValue(value.toString());
            } else if (value instanceof BigInteger) {
                cell.setCellValue(value.toString());
            }  else {
                cell.setCellValue((String) value);
            }
            cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);

        for (LinkedCaseInsensitiveMap subElement : procedureResult) {
            if (subElement.size()>0){
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                Integer index=0;
                Iterator it = subElement.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Integer columnIndx=columnCount++;
                    if((this.skipIndex1 != null && columnIndx == this.skipIndex1) ||
                    (this.skipIndex2 != null && columnIndx == this.skipIndex2) ||
                    (this.skipIndex3 != null && columnIndx == this.skipIndex3) ||
                    (this.skipIndex4 != null && columnIndx == this.skipIndex4) ||
                    (this.skipIndex5 != null && columnIndx == this.skipIndex5) ||
                    (this.skipIndex6 != null && columnIndx == this.skipIndex6) ||
                    (this.skipIndex7 != null && columnIndx == this.skipIndex7)) {
                    }else{
                        if (pair.getValue() != null) {
                            createCell(row, index, pair.getValue(), style);
                        } else {
                            createCell(row, index, null, style);
                        }
                        index++;
                    }
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
        }
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Sheet-1");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        int i = 0;
        int m = 0;
        if (Objects.nonNull(procedureResult) && procedureResult.size() > 0) {
            LinkedCaseInsensitiveMap header = procedureResult.get(0);
            Iterator it = header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("investigation")){
                    if(pair.getKey().toString().equalsIgnoreCase("account_number")){
                        this.skipIndex1=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("allegation_type")){
                        this.skipIndex2=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("total_allegation")){
                        this.skipIndex3=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("memo_number")){
                        this.skipIndex4=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("summary")){
                        this.skipIndex5=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("introduction")){
                        this.skipIndex6=i;
                    }else if(pair.getKey().toString().equalsIgnoreCase("query_id")){
                        this.skipIndex7=i;
                    }else{
                        createCell(row, m, WordUtils.capitalizeFully(pair.getKey().toString().replaceAll("_", " ")), style);
                        m++;
                    }
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("control_report") && pair.getKey().toString().equalsIgnoreCase("inv_remarks")){
                    this.skipIndex1=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("control_report") && pair.getKey().toString().equalsIgnoreCase("recommendation")){
                    this.skipIndex2=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("edd") && pair.getKey().toString().equalsIgnoreCase("account_number")){
                    this.skipIndex1=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("edd") && pair.getKey().toString().equalsIgnoreCase("cr_no")){
                    this.skipIndex2=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("query") && pair.getKey().toString().equalsIgnoreCase("remarks")){
                    this.skipIndex1=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("query") && pair.getKey().toString().equalsIgnoreCase("account_number")){
                    this.skipIndex2=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("training") && pair.getKey().toString().equalsIgnoreCase("account_number")){
                    this.skipIndex1=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("linked_between_account") && pair.getKey().toString().equalsIgnoreCase("account_number")){
                    this.skipIndex1=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("linked_between_account") && pair.getKey().toString().equalsIgnoreCase("transaction_date")){
                    this.skipIndex2=i;
                }else if(Objects.nonNull(this.param) && this.param.equalsIgnoreCase("linked_between_account") && pair.getKey().toString().equalsIgnoreCase("transaction_time")){
                    this.skipIndex3=i;
                }else{
                    createCell(row, m, WordUtils.capitalizeFully(pair.getKey().toString().replaceAll("_", " ")), style);
                    m++;
                }
                i++;
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

    public static void excelExport(Map<String,Object> dataList, HttpServletResponse response, String fileName) throws Exception {

        Workbook workBook = createWorkbook(dataList);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename="+fileName+".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workBook.write(outputStream);
        workBook.close();
        outputStream.close();
    }

    public static void excelExportToFile(Map<String,Object> dataList, String filePath) throws Exception {
        Workbook workBook = createWorkbook(dataList);
        OutputStream outputStream = Files.newOutputStream(Paths.get(filePath));
        workBook.write(outputStream);
        workBook.close();
        outputStream.close();
    }

    public static Workbook createWorkbook(Map<String, Object> data) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        List<Map<String, Object>> dataSet = (List<Map<String, Object>>) data.get("#result-set-1");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> headerColumnList = objectMapper.readValue(
                (String) data.get("headerColumn"),
                new TypeReference<List<Map<String, Object>>>() {}
        );
        List<String> headers = new ArrayList<>();
        for (Map<String, Object> header : headerColumnList) {
            headers.add((String) header.get("name"));
        }

        // Parse keySet JSON string
        List<Map<String, Object>> keySetList = objectMapper.readValue(
                (String) data.get("keySet"),
                new TypeReference<List<Map<String, Object>>>() {}
        );
        List<String> keys = new ArrayList<>();
        for (Map<String, Object> key : keySetList) {
            keys.add((String) key.get("name"));
        }
//        List<String> headers = Arrays.asList(StringUtils.splitPreserveAllTokens((String) data.get("headerColumn"), ","));
//
//        List<String> keys =  Arrays.asList(StringUtils.splitPreserveAllTokens((String) data.get("keySet"), ","));

        String reportName = data.containsKey("reportName")? data.get("reportName").toString():"Sheet-1";

        Sheet sheet = workbook.createSheet(reportName);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();//Create font
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        for (Integer index = 0; index < headers.size(); index++) {
            if (index != 0) headerCell = header.createCell(index);
            headerCell.setCellValue(headers.get(index));
            headerCell.setCellStyle(headerStyle);
        }

        CellStyle style = workbook.createCellStyle();
        for (Integer index = 0; index < dataSet.size(); index++) {
            Row row = sheet.createRow(index + 1);

            Cell cell = row.createCell(0);
            for (Integer keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
                if (keyIndex != 0) cell = row.createCell(keyIndex);
                cell.setCellValue(getValueFromDataSet(dataSet.get(index), keys.get(keyIndex)));
                cell.setCellStyle(style);
            }

        }

        int width = 30 * 256;
        for (Integer index = 0; index < 18; index++) {
            sheet.setColumnWidth(index, width);
        }
        return workbook;
    }


    public static String getValueFromDataSet(Map<String, Object> data, String param) {
        try {
            return data.get(param).toString();
        } catch (Exception e) {
        }
        return "";
    }

    public void exportToFile(String filePath) throws Exception{
        writeHeaderLine();
        writeDataLines();
        OutputStream outputStream = Files.newOutputStream(Paths.get(filePath));
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public Map<String,String> getFileProperties(){
        Map<String,String> map=new HashMap<>();
        map.put("reportFilePath",reportFilePath);
        map.put("appName",appName);
        return map;
    }
}

