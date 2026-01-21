package com.datasoft.bkash.ea.utils;

import com.datasoft.bkash.ea.response.ApiResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.apache.poi.ss.util.CellUtil.createCell;

@Component
@Slf4j
public class Utils {
    @Value("${server.servlet.context-path}")
    private String contextPath;



    public static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private static final Short OFFSET_ONE_DAY = 1;
    private static final ZoneId DEFAULT_TIMEZONE_ID = ZoneId.systemDefault();
    private static AtomicLong uniqueIdentifier = new AtomicLong(new Date().getTime());


    public static void writeInJson(Field field, Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, IllegalAccessException {
        field.setAccessible(true);
        provider.defaultSerializeField(field.getName(), field.get(value), jgen);
    }

    public static String getRandomAlphaNumeric() {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, StandardCharsets.UTF_8);
        StringBuilder r = new StringBuilder();
        int n = 8; //character length
        String alphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", "");// remove all spacial char
        for (int k = 0; k < alphaNumericString.length(); k++) {
            if (Character.isLetter(alphaNumericString.charAt(k)) && (n > 0) || Character.isDigit(alphaNumericString.charAt(k)) && (n > 0)) {
                r.append(alphaNumericString.charAt(k));
                n--;
            }
        }
        return r.toString();
    }


    /**
     * Create a response object with arbitrary number of properties
     *
     * @param status  true or false to indicate success
     * @param objects always of length which is a multiple of 2, the first object in every pair is the key and should be string
     *                the second of the pair should be the object
     * @return the resultant response
     */
    public static Map<String, Object> toResponseAsMap(Boolean status, Object... objects) {
        Map<String, Object> map = new HashMap<>();
        assert objects.length % 2 == 0;
        map.put("status", status);
        for (int i = 0; i < objects.length; i += 2) {
            map.put((String) objects[i], objects[i + 1]);
        }
        return map;
    }

    String getCamelcase(String text) {

        String result = "";
        String[] tokens = text.split(" ");
        int tokensLen = tokens.length;
        for (int i = 0; i < tokensLen; i++) {
            String token = tokens[i];
            if (i == 0) result = token.toLowerCase();
            else
                result += token.substring(0, 1).toUpperCase() + token.substring(1).toLowerCase();
        }
        if (result.equals("registration/JoiningDate")) {
            return "registrationDate";
        } else if (result.equals("no.OfParticipants")) {
            return "totalParticipants";
        } else if (result.equals("no.OfAttendee")) {
            return "totalAttendee";
        }
        return result;
    }

    public static void titleStyle(Workbook wb, Row row) {

        CellStyle style = wb.createCellStyle();
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        row.getCell(0).setCellStyle(style);
    }

    public static void makeRowBold(Workbook wb, Row row) {

        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
        }
    }

    public ResponseEntity<?> generateExcelReport(String contentName, List<Map<String, Object>> dto) {

        try {
            List<String> headers = Arrays.asList(dto.get(0).get("columnName").toString().split(","));
            List<String> keys = headers.stream().map(this::getCamelcase).collect(Collectors.toList());
            try (SXSSFWorkbook workbook = new SXSSFWorkbook(1)) {
                Sheet sheet = workbook.createSheet("Sheet 01");
                sheet.setDefaultColumnWidth(headers.stream().max(Comparator.comparingInt(String::length)).get().length());
                int rowNum = 0;
                int rowLength = headers.size();
                Row row = sheet.createRow(rowNum);

                // Title
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.size() - 1));
                createCell(row, 0, dto.get(0).get("reportName").toString());
                titleStyle(workbook, row);
                rowNum++;

                // Headers
                row = sheet.createRow(rowNum);
                for (int headerCellNo = 0; headerCellNo < rowLength; headerCellNo++) {
                    createCell(row, headerCellNo, headers.get(headerCellNo));
                }
                makeRowBold(workbook, row);
                rowNum++;

                // Data
                for (Map<String, Object> rowIterator : dto) {
                    row = sheet.createRow(rowNum);
                    for (int cellNo = 0; cellNo < rowLength; cellNo++) {
                        String cellValue = String.valueOf(rowIterator.get(keys.get(cellNo)));
                        if (!cellValue.equals("null")) createCell(row, cellNo, cellValue);
                        else createCell(row, cellNo, "");
                    }
                    rowNum++;
                }

                // Export
                try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
                    workbook.write(stream);
                    workbook.dispose();
                    String fileName = dto.get(0).get("reportName").toString().concat(".xlsx");
                    return ResponseEntity
                            .created(null)
                            .header("Content-Disposition", "attachment; filename=" + fileName)
                            .body(new ByteArrayResource(stream.toByteArray()));
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    public static String formatDateForReport(Date date) {
        if (date != null) {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        }
        return "";
    }

    public static String formatTime24HrFormatForReport(Date date) {
        if (date != null) {
            return new SimpleDateFormat("HH:mm:ss").format(date);
        }
        return "";
    }

    public static String formatDateForUserLists(Date date) {
        return date != null ? new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss a").format(date) : "";
    }

    public static Date stringToDate(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            String arr[] = strDate.split("-");
            int intValue = Integer.parseInt(arr[0]);
            if (intValue >= 1970 && intValue <= 2037) {
                date = df.parse(strDate);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    public static Date stringToTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    public static Date convertDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return new Date(timestamp.getTime());
        }
        return null;
    }

    public static Boolean getStatus() {
        return true;
    }

    public static Date getDate() {
        return new Date();
    }

    public static Page<Map<String, Object>> getPage(List<Map<String, Object>> list, Pageable pageable) {
        long totalRow = 0;
        if (!list.isEmpty()) {
            totalRow = Long.parseLong(String.valueOf(list.get(0).get("totalRow")));
        }
        return new PageImpl<>(list, pageable, totalRow);
    }

    public static ApiResponse getApiResponseByPage(Page<?> list) {
        if (!list.getContent().isEmpty()) {
            return new ApiResponse(HttpStatus.OK.value(), "data fetch successfully", list);
        }
        return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Results found", list);
    }

    public static ApiResponse getApiResponse(List<Map<String, Object>> list) {
        if (list != null && !list.isEmpty()) {
            generateLog(Constant.SUCCESS, "total " + list.size() + " row fetch successfully");
            return new ApiResponse(HttpStatus.OK.value(), "data fetch successfully", list);
        }
        generateLog(Constant.FAIL, "No Data Found");
        return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Results found", new ArrayList<>());
    }

    public static ApiResponse prepareCommonListResponse(ArrayList<?> data) {
        if (data == null) {
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Data Found", null);
        }
        if (data.size() == 1) {
            String firstString = data.get(0).toString();
            String[] array = firstString.split("=");
            if (array.length > 0 && array[1].replace("}", "").equals("null")) {
                String value = firstString.split("=")[1].replace("}", "");
                return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Data Found", null);
            } else {
                generateLog(Constant.SUCCESS, "total " + data.size() + " row fetch successfully");
                return new ApiResponse(HttpStatus.OK.value(), "Successfully Fetch Result", data);
            }
        }
        if (data.size() > 0) {
            generateLog(Constant.SUCCESS, "total " + data.size() + " row fetch successfully");
            return new ApiResponse(HttpStatus.OK.value(), "Successfully Fetch Result", data);
        } else {
            generateLog(Constant.FAIL, "No Data Found");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Data Found", null);
        }
    }

    public static ApiResponse prepareCommonObjectResponse(Map<String, Object> data) {
        if (data != null && data.size() > 0) {
            generateLog(Constant.SUCCESS, "fetch successfully");
            return new ApiResponse(HttpStatus.OK.value(), "Successfully Fetch Result", data);
        } else {
            generateLog(Constant.FAIL, "No Data Found");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Data Found", null);
        }
    }


    public static MapSqlParameterSource getMapSqlParamSource(String searchText) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("searchText", "%" + searchText.toLowerCase() + "%");
        return param;
    }

    public static Boolean convertIntegerToBoolean(Integer value) {
        if (value == 1) {
            return true;
        } else if (value == 0) {
            return false;
        } else {
            return null;
        }
    }

    public static <T> T nvl(T value) {
        if (value == null) {
            return null;
        } else {
            return value;
        }
    }

    public static ApiResponse convertStringToJson(String result) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = null;
        List<Map<String, String>> dataList = new ArrayList<>();
        if (null == result || result.equals("") || result.equalsIgnoreCase("null")) {
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "No Result Found", dataList);
        } else {
            JSONObject jsonObject = new JSONObject("{ \"result\":" + result + "}");
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    map = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), Map.class);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                dataList.add(map);
            }
            return new ApiResponse(HttpStatus.OK.value(), "Successfully Fetch Result", dataList);
        }
    }

    public static void generateLog(String status, String message) {
        if (status.equals(Constant.SUCCESS)) {
            log.info("===========================REQUEST START HERE=================================");
            log.info("Request status => {}", Constant.SUCCESS);
            log.info("Final response message => {}", message);
            log.info("===========================REQUEST ENDS HERE=================================");
        } else {
            log.error("===========================REQUEST START HERE=================================");
            log.error("Request status => {}", Constant.FAIL);
            log.error("Final response message => {}", message);
            log.error("===========================REQUEST ENDS HERE=================================");
        }
    }

    public static String formatDateInddMMyyyyFormat(Date date) {
        if (date != null) {
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        }
        return "";
    }

    public static String addSingleQuote(String string) {
        return "'" + string + "'";
    }

    public static String appendRegionNameAndDistrictNameAndThanaNameAndBuisnessArea(String assesseeType, String region, Optional<String> district, Optional<String> thana, Optional<String> area) {
        StringBuilder whereCondition = new StringBuilder("");
        // Add region
        if (assesseeType.equalsIgnoreCase("merchant"))
            whereCondition.append(Constant.AND + "business_region=").append(Utils.addSingleQuote(region));
        else {
            if (!assesseeType.equalsIgnoreCase("dh") && !assesseeType.equalsIgnoreCase("dso"))
                whereCondition.append(Constant.AND);
            whereCondition.append("region_name=").append(Utils.addSingleQuote(region));
        }

        // Add searching parameters
        if (district.isPresent() && !StringUtils.isEmpty(district.get())) {
            whereCondition.append(Constant.AND + "district_name=").append(Utils.addSingleQuote(district.get()));
        }
        if (thana.isPresent() && !StringUtils.isEmpty(thana.get())) {
            whereCondition.append(Constant.AND + "thana_name=").append(Utils.addSingleQuote(thana.get()));
        }
        if (area.isPresent() && !StringUtils.isEmpty(area.get())) {
            whereCondition.append(Constant.AND + "business_area=").append(Utils.addSingleQuote(area.get()));
        }
        return whereCondition.toString();
    }

    public static String getValueFromDataSet(Map<String, Object> data, String param) {
        try {
            return data.get(param).toString();
        } catch (Exception e) {
        }
        return "";
    }

    public static String addStartTimeOfDay(String fromDate) {
        return fromDate + " 00:00:00";
    }

    public static String addEndTimeOfDay(String fromDate) {
        return fromDate + " 23:59:59";
    }

    public static boolean isValidFilePath(String filePath) {
        return Objects.nonNull(filePath) && filePath.contains("..");
    }

    public static List<String> getAllDatesBetweenStartDateAndEndDate(String startDate, String endDate) throws ParseException {
        if (Objects.isNull(startDate)) {
            return new ArrayList<>();
        }
        endDate = (Objects.isNull(endDate) ? startDate : endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        int dayAdd = 1;
        List<String> dates = new ArrayList<>();
        boolean stopExecution = true;
        dates.add(sdf.format(cal.getTime()));
        while (endDate.compareTo(startDate) >= 1 && stopExecution) {
            cal.add(Calendar.DATE, dayAdd);
            stopExecution = Objects.equals(endDate, sdf.format(cal.getTime())) ? false : true;
            dates.add(sdf.format(cal.getTime()));
        }
        return dates;
    }

    public static String getNextDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date stDate = sdf.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(stDate);
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }

    public static <T> T convertNullToEmptyString(T value) {
        if (value == null) {
            return (T) "";
        } else {
            return value;
        }
    }

    public static String readAccountNumber(Cell cell) {
        if (Objects.nonNull(cell)) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                Double value = cell.getNumericCellValue();
                String strNumber = String.format("%.0f", value);
                if (Objects.nonNull(strNumber) && strNumber.length() == 10) {
                    strNumber = "0".concat(strNumber);
                }
                return strNumber;
            }
        }
        return null;
    }

    public static String convertCellValueToString(Cell cell) {
        if (Objects.nonNull(cell)) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                Double value = cell.getNumericCellValue();
                String strNumber = String.format("%.0f", value);
                return strNumber;
            }
        }
        return null;
    }
}
