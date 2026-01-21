package com.datasoft.bkash.ea.utils;

import com.datasoft.bkash.ea.exception.ParseException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.datasoft.bkash.ea.utils.Utils.convertNullToEmptyString;
import static com.datasoft.bkash.ea.utils.Utils.generateLog;

@Slf4j
public class FileUtil {

    public static String convertCellToString(Cell cell) {
        String cellValue = "";
        if (CellType.STRING.equals(cell.getCellType())) {
            cellValue = cell.getStringCellValue();
        } else if (CellType.NUMERIC.equals(cell.getCellType())) {
            cellValue = "0" + cell.getNumericCellValue();
        } else if (CellType.BLANK.equals(cell.getCellType())) {
            cellValue = "";
        } else if (CellType._NONE.equals(cell.getCellType())) {
            cellValue = "";
        } else if (CellType.ERROR.equals(cell.getCellType())) {
            cellValue = "";
        } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
            cellValue = "";
        }
        return cellValue;
    }

    public static File writeInputStreamToFile(InputStream is, int bufferSize) throws IOException {
        File f = Files.createTempFile("tmp-", ".xlsx").toFile();
        try (FileOutputStream fos = new FileOutputStream(f)) {
            int read;
            byte[] bytes = new byte[bufferSize];
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return f;
        } finally {
            is.close();
        }
    }

    public static Document document(InputStream is) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            generateLog(Constant.FAIL, e.getMessage());
            throw new ParseException(e);
        }
    }

    public static NodeList searchForNodeList(Document document, String xpath) {
        try {
            return (NodeList) XPathFactory.newInstance().newXPath().compile(xpath)
                    .evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            generateLog(Constant.FAIL, e.getMessage());
            throw new ParseException(e);
        }
    }

    public static List<Map<String, Object>> getTraineeTypeNotMatchList(Map<String, Object> observation, String traineeType) {
        Map<String, Object> object = (Map<String, Object>) observation.get("response");
//        List<Observation> errorList = (List<Observation>) object.get("errorObservationList");
//        List<Observation> validList = (List<Observation>) object.get("observationList");
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            AtomicInteger atomicInteger = new AtomicInteger(1);
//            list.addAll(buildInvalidTraineeType(traineeType, validList, atomicInteger));
//            list.addAll(buildInvalidTraineeType(traineeType, errorList, atomicInteger));
        } catch (Exception ignored) {
        }
        return list;
    }




    public static List<Map<String, Object>> getEmployeeTypeNotMatchList(MultipartFile file, String employeeType) {
        List<Map<String, Object>> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            AtomicInteger atomicInteger = new AtomicInteger(1);
            workbook.getSheetAt(0).forEach(row -> {
                if (row.getRowNum() > 0) {
                    Map<String, Object> bkashEmployee = new HashMap<>();
                    if (!employeeType.trim().equalsIgnoreCase("all") && !row.getCell(9).getStringCellValue().trim().equalsIgnoreCase(employeeType.trim())) {
                        bkashEmployee.put("slNo", atomicInteger.getAndIncrement());
                        bkashEmployee.put("bkashId", row.getCell(0).getStringCellValue());
                        bkashEmployee.put("employeeName", row.getCell(1).getStringCellValue());
                        list.add(bkashEmployee);
                    }
                }
            });
        } catch (Exception ignored) {
        } finally {
            assert workbook != null;
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static Map<String, String> getHeaderMismatch(MultipartFile file, String[] headers) {
        Map<String, String> map = new HashMap<>();
        Sheet sheet;
        try {
            sheet = new XSSFWorkbook(file.getInputStream()).getSheetAt(0);

//            int rowCount = sheet.getPhysicalNumberOfRows();

//            if(rowCount>25000){
//                map.put("message", "Data limit exceeded. Data Limit is 25000");
//                return map;
//            }


        } catch (IOException e) {
            map.put("message", "Sheet Not Found");
            return map;
        }
        try {
            assert sheet != null;
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (int i = 0; i < headers.length; i++) {
                    String head = headerRow.getCell(i).getStringCellValue().replace("\n", "").replace("\r", "").replace("\\s", "");
                    if (!head.trim().equals(headers[i].trim())) {
                        map.put("message", "Invalid Excel header format");
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Invalid Excel header format");
            log.error(e.getMessage());
            map.put("Error",e.getMessage());
            return map;
        }
    }

    public static Map<String, String> getHeaderMismatch(File file, String[] headers) {
        Map<String, String> map = new HashMap<>();
        Sheet sheet;
        try {
            sheet = new XSSFWorkbook(Files.newInputStream(file.toPath())).getSheetAt(0);

//            int rowCount = sheet.getPhysicalNumberOfRows();

//            if(rowCount>25000){
//                map.put("message", "Data limit exceeded. Data Limit is 25000");
//                return map;
//            }


        } catch (IOException e) {
            map.put("message", "Sheet Not Found");
            return map;
        }
        try {
            assert sheet != null;
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (int i = 0; i < headers.length; i++) {
                    String head = headerRow.getCell(i).getStringCellValue().replace("\n", "").replace("\r", "").replace("\\s", "");
                    if (!head.trim().equals(headers[i].trim())) {
                        map.put("message", "Invalid Excel header format");
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Invalid Excel header format");
            log.error(e.getMessage());
            map.put("Error",e.getMessage());
            return map;
        }
    }





    public static List<Map<String, Object>> getErrorList(Map<String, Object> observation) {
        List<Map<String, Object>> errorList = null;
        if (observation != null && !observation.isEmpty()) {
            Map<String, Object> object = (Map<String, Object>) observation.get("response");
            errorList = (List<Map<String, Object>>) object.get("errorObservationList");
        }
        return errorList;
    }



    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for (int cellNum = 1; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            } else {
                sb.append(cell != null ? cell.getStringCellValue() : "");
            }
        }
        return sb.toString().isEmpty();
    }

    public static String getHeaderMismatch(MultipartFile file, String requestType, String uploadType) {
        Map<String, String> map = new HashMap<>();
        Sheet sheet;
        try {
            sheet = new XSSFWorkbook(file.getInputStream()).getSheetAt(0);
        } catch (IOException e) {
            return "Sheet Not Found";
        }
        try {
            assert sheet != null;
            Row headerRow = sheet.getRow(0);

            String head = headerRow
                    .getCell(0)
                    .getStringCellValue()
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\\s", "");


            // Iterate over the cells in the first row.
            boolean hasEmptyCell = false;
            Set<String> columnNames = new HashSet<>();
            int lastColumn = headerRow.getLastCellNum();

            for (int cn = 0; cn < lastColumn; cn++) {

                Cell cell = headerRow.getCell(cn);
                String columnName = convertNullToEmptyString(cell.getStringCellValue()).trim();

                if (cell == null || cell.getCellType() == CellType.BLANK) {
                    hasEmptyCell = true;
                    break;
                }

                if(convertNullToEmptyString(uploadType).equalsIgnoreCase("annexure")) {
                    if (!columnName.isEmpty() && !columnNames.add(columnName)){
                        return "Wrong file format. Please ensure unique column header. duplicate column header found: (" + columnName + ")";
                    }
                    if (columnName.trim().equalsIgnoreCase("Insight") || columnName.trim().equalsIgnoreCase("insight")) {
                        return "Wrong file format. File has additional insight column. Please remove the column and try again.";
                    }
                }
                if(convertNullToEmptyString(uploadType).equalsIgnoreCase("insight")) {
                    if (!columnName.isEmpty() && !columnNames.add(columnName)){
                        return "Wrong file format. Please ensure unique column header. duplicate column header found: (" + columnName + ")";
                    }
                    if (!columnName.trim().equalsIgnoreCase("Account Number") && !columnName.trim().equals("Insight")) {
                        return "Wrong file format. File has additional  column. Please remove the column and try again.";
                    }

                }
                if (convertNullToEmptyString(uploadType).equalsIgnoreCase("") && convertNullToEmptyString(requestType).equalsIgnoreCase("account") && cn != 0 &&  !columnName.trim().equalsIgnoreCase("Account Number" ) ) {
                    return "Wrong file format.";
                }
            }

            // check if any of the cells in the first row are empty.
            if (hasEmptyCell) {
                return "Invalid Header. Please ensure all column headers exist";
            }

            if(Objects.isNull(head)) {
                return "Wrong file format.MCR is not issued as Primary key alias name in the designed base table is not \"Account number\" if alias name is wrong in the designed base table where generation request type is \"Account Number\"";
            }else if(convertNullToEmptyString(requestType).equalsIgnoreCase("account")){
                if (!convertNullToEmptyString(head).trim().equals("Account Number")) {
                    return "Wrong file format";
                }

                if(convertNullToEmptyString(uploadType).equalsIgnoreCase("insight")){
                    String insightCol = headerRow.getCell(1).getStringCellValue().replace("\n", "").replace("\r", "").replace("\\s", "");
                    if (!convertNullToEmptyString(head).trim().equals("Account Number") || !convertNullToEmptyString(insightCol).trim().equals("Insight")) {
                        return "Wrong file format. Please ensure the uploading file is an excel file and it has 'Account Number' column at first position and 'Insight' column at second position";
                    }
                }

            }else if(convertNullToEmptyString(requestType).equalsIgnoreCase("photo id")){
                if (!convertNullToEmptyString(head).trim().equals("Photo ID")) {
                    return "Wrong file format.";
                }
                if (!convertNullToEmptyString(uploadType).equalsIgnoreCase("annexure") && !convertNullToEmptyString(uploadType).equalsIgnoreCase("insight") && (Objects.isNull(headerRow.getCell(1)) || !convertNullToEmptyString(headerRow.getCell(1).getStringCellValue()).trim().equals("Photo ID Type"))) {
                    return "Wrong file format.";
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Invalid Excel header format");
            log.error(e.getMessage());
            map.put("Error",e.getMessage());
            return "Invalid Excel header format";
        }
    }

}