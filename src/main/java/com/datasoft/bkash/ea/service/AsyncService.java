package com.datasoft.bkash.ea.service;

import com.datasoft.bkash.ea.dao.JdbcFunctionDao;
import com.datasoft.bkash.ea.utils.Constant;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AsyncService {
    @Autowired
    private JdbcFunctionDao jdbcFunctionDao;
//    @Autowired
//    private SmtpEmailService emailService;
//    @Autowired
//    private AMQPProducer amqpProducer;
    @Value("${cr-data-populate-chunk}")
    private Integer crDataPopulateChunk;

//    @Autowired
//    private NotificationService notificationService;

//    @Async
//    public Map<String, Object> rmcrAsync(Map<String, Object> spParam) {
//        try {
//            Map<String, Object> result = jdbcFunctionDao.getProcedureResult(Constant.RM_CR_EXECUTION, spParam);
//            try {
//                if (result.containsKey("issuer_mail_set")
//                        && result.containsKey("issuer_mail_body")
//                        && Objects.nonNull(result.get("issuer_mail_set"))
//                        && Objects.nonNull(result.get("issuer_mail_body"))) {
//                    emailService.sendEmail("Control Report Issue",
//                            result.get("issuer_mail_body").toString(),
//                            result.get("issuer_mail_set").toString().split(","),
//                            null, null);
//                    controlReportIssueDao.updateMailStatus(spParam.get("crIssueId"), true);
//                }
//                if (result.containsKey("notification_id_set") && Objects.nonNull(result.get("notification_id_set"))) {
//                    NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
//                    notificationResponseDto.setUserIds(result.get("notification_id_set").toString());
//                    amqpProducer.sendMessage(notificationResponseDto);
//                }
//            } catch (Exception ex) {
//                log.error(ex.getMessage(), ex.getCause());
//            }
//            return result;
//        } catch (Exception e) {
//            log.error("RMCR issue failed - unknown error {}", spParam, e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
//        }
//    }
//
//    @Async
//
//    public ResponseEntity crAsync(Map<String, Object> spParam) {
//        try {
//            Map<String, Object> result = jdbcFunctionDao.getProcedureResult(Constant.CONTROL_REPORT_EXECUTION, spParam);
//            if (result.containsKey("issuer_mail_set")
//                    && result.containsKey("issuer_mail_body")
//                    && Objects.nonNull(result.get("issuer_mail_set"))
//                    && Objects.nonNull(result.get("issuer_mail_body"))) {
//                emailService.sendEmail("Control Report Issue",
//                        result.get("issuer_mail_body").toString(),
//                        result.get("issuer_mail_set").toString().split(","),
//                        null, null);
//                controlReportIssueDao.updateMailStatus(spParam.get("crIssueId"), true);
//            }
//            if (result.containsKey("notification_id_set") && Objects.nonNull(result.get("notification_id_set"))) {
//                NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
//                notificationResponseDto.setUserIds(result.get("notification_id_set").toString());
//                amqpProducer.sendMessage(notificationResponseDto);
//            }
//            return ResponseEntity.status(HttpStatus.OK).body("Control Report Executed successful.");
//        } catch (Exception e) {
//            log.error("Control Report Executed failed - unknown error {}", e);
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
//        }
//    }
//
//    @Async
//    public void crDataPopulate(Integer crIssueId, String startDate, String endDate, Integer userId) throws ParseException {
//        Integer offset = 0;
//        Integer crId = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        List<Map<String, Object>> allTable = controlReportIssueDao.getAllTableInfoByCrIssueId(crIssueId);
//        List<String> dates = Utils.getAllDatesBetweenStartDateAndEndDate(startDate, endDate);
//        int executionCompleteCount = 0;
//        Boolean isExecutable = true;
//        for (String sDate : dates) {
//            cal.setTime(sdf.parse(sDate));
//            cal.add(Calendar.DATE, 2);
//            if (sdf.format(new Date()).compareTo(sdf.format(cal.getTime())) >= 1) {
//                controlReportIssueDao.updateIssueInfo(crIssueId, sDate);
//                for (Map<String, Object> table : allTable) {
//                    crId = (Integer) table.get("cr_id");
//                    offset = 0;
//                    Map<String, String> crActiveStatus = controlReportIssueDao.checkCRActiveOrCancelStatus(crIssueId, sDate);
//                    isExecutable = Objects.nonNull(crActiveStatus) && crActiveStatus.containsKey("executeStatus") && Objects.equals(crActiveStatus.get("executeStatus"), "Executable") && crActiveStatus.containsKey("crStatus") && !Objects.equals(crActiveStatus.get("crStatus"), "Cancel") && crActiveStatus.containsKey("isControlReportActive") && Objects.equals(crActiveStatus.get("isControlReportActive"), "Active");
//                    if (isExecutable) {
//                        controlReportIssueDao.updateTableWiseDataProcessingInfo(crIssueId, (String) table.get("design_table_id"), (String) table.get("table_name"));
//                        String datewiseInsertQry = "INSERT INTO " + (String) table.get("table_name") + " ( execute_date, " + (String) table.get("col_name") + " )";
//                        Map<String, Object> spParam = new HashMap<>();
//                        spParam.put("tableId", table.get("design_table_id"));
//                        spParam.put("issueId", crIssueId);
//                        spParam.put("startDate", sDate);
//                        spParam.put("endDate", null);
//                        Map<String, Object> result = jdbcFunctionDao.getProcedureResult(Constant.CONTROL_REPORT_DESIGN_QUERY, spParam);
//                        String datewiseQry = (result.containsKey("designedQuery")) ? (String) result.get("designedQuery") : null;
//                        if (Objects.nonNull(datewiseQry)) {
//                            if (Objects.equals("Base Table", (String) table.get("source"))) {
//                                boolean isDataAvailbleToBeExecuted = true;
//                                while (isDataAvailbleToBeExecuted) {
//                                    String datewiseQuery = "SELECT `execute_date`, " + (String) table.get("col_name") + " from (SELECT '" + sDate + "' AS `execute_date`, " + datewiseQry.substring(7, datewiseQry.length()) + " LIMIT " + crDataPopulateChunk + " OFFSET " + offset + " ) FINAL;";
//                                    List<Map<String, Object>> dataSet = controlReportIssueDao.dayAndLimitWiseDataFetchForBase(datewiseQuery);
//                                    if (dataSet.size() > 0) {
//                                        int output[] = controlReportIssueDao.dayAndLimitWiseDataInsert(datewiseInsertQry, this.selectQueryColumn(datewiseQuery), this.insertQueryColumn((String) table.get("col_name")), dataSet);
//                                    }
//                                    offset = offset + crDataPopulateChunk;
//                                    isDataAvailbleToBeExecuted = (dataSet.size() > 0) ? true : false;
//                                }
//                                Map<String, Object> accCountSpParam = new LinkedHashMap<>();
//                                accCountSpParam.put("userId", userId);
//                                accCountSpParam.put("crIssueId", crIssueId);
//                                accCountSpParam.put("crId", crId);
//                                accCountSpParam.put("ptype", "account-count");
//                                accCountSpParam.put("tableId", table.get("design_table_id"));
//                                accCountSpParam.put("tableName", table.get("table_name"));
//                                accCountSpParam.put("executeDate", sDate);
//                                jdbcFunctionDao.getProcedureResult(Constant.CONTROL_REPORT_DATEWISE_EXECUTION, accCountSpParam);
//                            } else {
//                                String datewiseQuery = "SELECT '" + sDate + "' AS `execute_date`, " + datewiseQry.substring(7, datewiseQry.length()) + ";";
//                                String insertQuery = datewiseInsertQry + " " + datewiseQuery;
//                                int output = controlReportIssueDao.dayWiseDataInsert(insertQuery);
//                            }
//                        }
//                        executionCompleteCount++;
//                    } else if (Objects.nonNull(crActiveStatus) && crActiveStatus.containsKey("isControlReportActive") && Objects.equals(crActiveStatus.get("isControlReportActive"), "Inactive")) {
//                        controlReportIssueDao.cancelCR(crIssueId);
//                    }
//
//                }
//                if (isExecutable) {
//                    Map<String, Object> spParam = new HashMap<>();
//                    spParam.put("userId", userId);
//                    spParam.put("crId", crId);
//                    spParam.put("executeDate", sDate);
//                    spParam.put("crIssueId", crIssueId);
//                    spParam.put("startDate", startDate);
//                    spParam.put("endDate", endDate);
//                    String insertQuery = jdbcFunctionDao.getFunctionResult(Constant.GET_RISK_MITIGATION_QUERY, spParam);
//                    if (Objects.nonNull(insertQuery)) {
//                        int output = controlReportIssueDao.dayWiseDataInsert(insertQuery);
//                    }
////                    controlReportIssueDao.updateCompletionStatus(crIssueId, dates.size(), executionCompleteCount);
//                }
//                controlReportIssueDao.updateIssueInfoDateAndStatus(crIssueId, sDate);
//            }
//        }
//    }
//
//    @Async
//    public void rmCrDataPopulate(Integer crIssueId, String startDate, String endDate, Integer userId, String lastDate) throws ParseException {
//        Integer offset = 0;
//        Integer crId = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        List<Map<String, Object>> allTable = controlReportIssueDao.getAllTableInfoByCrIssueId(crIssueId);
//        List<String> dates = Utils.getAllDatesBetweenStartDateAndEndDate(startDate, endDate);
//        int executionCompleteCount = 0;
//        Boolean isFrequencyEndDateAvailableInDateRange = false;
//        for (String sDate : dates) {
//            cal.setTime(sdf.parse(sDate));
//            cal.add(Calendar.DATE, 2);
//            if (sdf.format(new Date()).compareTo(sdf.format(cal.getTime())) >= 1) {
//                if (Objects.equals(sDate, lastDate)) {
//                    isFrequencyEndDateAvailableInDateRange = true;
//                }
//                controlReportIssueDao.updateIssueInfo(crIssueId, sDate);
//                for (Map<String, Object> table : allTable) {
//                    crId = (Integer) table.get("cr_id");
//                    offset = 0;
//                    controlReportIssueDao.updateTableWiseDataProcessingInfo(crIssueId, (String) table.get("design_table_id"), (String) table.get("table_name"));
//                    String datewiseInsertQry = "INSERT INTO " + (String) table.get("table_name") + " ( execute_date, " + (String) table.get("col_name") + " )";
//                    Map<String, Object> spParam = new HashMap<>();
//                    spParam.put("tableId", table.get("design_table_id"));
//                    spParam.put("issueId", crIssueId);
//                    spParam.put("startDate", sDate);
//                    spParam.put("endDate", null);
//                    Map<String, Object> result = jdbcFunctionDao.getProcedureResult(Constant.CONTROL_REPORT_DESIGN_QUERY, spParam);
//                    String datewiseQry = (result.containsKey("designedQuery")) ? (String) result.get("designedQuery") : null;
//                    if (Objects.nonNull(datewiseQry)) {
//                        if (Objects.equals("Base Table", (String) table.get("source"))) {
//                            boolean isDataAvailbleToBeExecuted = true;
//                            while (isDataAvailbleToBeExecuted) {
//                                String datewiseQuery = "SELECT execute_date, " + (String) table.get("col_name") + " from (SELECT '" + sDate + "' AS `execute_date`, " + datewiseQry.substring(7, datewiseQry.length()) + " LIMIT " + crDataPopulateChunk + " OFFSET " + offset + ") FINAL;";
//                                List<Map<String, Object>> dataSet = controlReportIssueDao.dayAndLimitWiseDataFetchForBase(datewiseQuery);
//                                if (dataSet.size() > 0) {
//                                    int output[] = controlReportIssueDao.dayAndLimitWiseDataInsert(datewiseInsertQry, this.selectQueryColumn(datewiseQuery), this.insertQueryColumn((String) table.get("col_name")), dataSet);
//                                }
//                                offset = offset + crDataPopulateChunk;
//                                isDataAvailbleToBeExecuted = (dataSet.size() > 0) ? true : false;
//                            }
//                        }
//                    }
//                    executionCompleteCount++;
//                }
//                controlReportIssueDao.updateIssueInfoDateAndStatus(crIssueId, sDate);
//            }
//        }
//        if (isFrequencyEndDateAvailableInDateRange) {
//            for (Map<String, Object> table : allTable) {
//                crId = (Integer) table.get("cr_id");
//                offset = 0;
//                controlReportIssueDao.updateTableWiseDataProcessingInfo(crIssueId, (String) table.get("design_table_id"), (String) table.get("table_name"));
//                String datewiseInsertQry = "INSERT INTO " + (String) table.get("table_name") + " ( execute_date, " + (String) table.get("col_name") + " )";
//                Map<String, Object> spParam = new HashMap<>();
//                spParam.put("tableId", table.get("design_table_id"));
//                spParam.put("issueId", crIssueId);
//                spParam.put("startDate", lastDate);
//                spParam.put("endDate", null);
//                Map<String, Object> result = jdbcFunctionDao.getProcedureResult(Constant.CONTROL_REPORT_DESIGN_QUERY, spParam);
//                String datewiseQry = (result.containsKey("designedQuery")) ? (String) result.get("designedQuery") : null;
//                if (Objects.nonNull(datewiseQry)) {
//                    String datewiseQuery = "SELECT '" + lastDate + "' AS `execute_date`, " + datewiseQry.substring(7, datewiseQry.length()) + ";";
//                    String insertQuery = datewiseInsertQry + " " + datewiseQuery;
//                    int output = controlReportIssueDao.dayWiseDataInsert(insertQuery);
//                }
//                executionCompleteCount++;
//            }
//
//            Map<String, Object> spParam = new HashMap<>();
//            spParam.put("userId", userId);
//            spParam.put("crId", crId);
//            spParam.put("executeDate", lastDate);
//            spParam.put("crIssueId", crIssueId);
//            spParam.put("startDate", startDate);
//            spParam.put("endDate", null);
//            String insertQuery = jdbcFunctionDao.getFunctionResult(Constant.GET_RISK_MITIGATION_QUERY, spParam);
//            int output = controlReportIssueDao.dayWiseDataInsert(insertQuery);
////            controlReportIssueDao.updateCompletionStatus(crIssueId, dates.size(), executionCompleteCount);
//        }
//    }

//    private String[] selectQueryColumn(String query) {
//        String[] columns = null;
//        query = query.toLowerCase();
//        if (query.contains(" from ")) {
//            String allColumns = query.substring(0, query.indexOf(" from "));
//            allColumns = allColumns.replace("select", "");
//            String[] splittedColumns = allColumns.split(" as ");
//            columns = new String[splittedColumns.length - 1];
//            String value = "";
//            for (int i = 1; i < splittedColumns.length; i++) {
//                if (splittedColumns[i].contains(",")) {
//                    value = splittedColumns[i].substring(0, splittedColumns[i].indexOf(",")).trim();
//                    value = value.replace("'", "");
//                    value = value.replace("`", "");
//                    columns[i - 1] = value;
//                } else {
//                    value = splittedColumns[i].trim();
//                    value = value.replace("'", "");
//                    value = value.replace("`", "");
//                    columns[i - 1] = value;
//                }
//            }
//        }
//        return columns;
//    }

    private String[] selectQueryColumn(String query) {
        String[] splittedColumns = null;
        query = query.toLowerCase();
        if (query.contains(" from ")) {
            String allColumns = query.substring(0, query.indexOf(" from "));
            allColumns = allColumns.replace("select", "");
            splittedColumns = allColumns.split(",");
        }
        for (int i = 0; i < splittedColumns.length; i++) {
            splittedColumns[i] = splittedColumns[i].replace("`", "").trim();
        }
        return splittedColumns;
    }

    private String[] insertQueryColumn(String query) {
        String[] columns = null;

        query = query.toLowerCase();
        String[] splittedColumns = query.split(",");
        columns = new String[splittedColumns.length + 1];
        columns[0] = "execute_date";
        for (int i = 0; i < splittedColumns.length; i++) {
            if (splittedColumns[i].contains(",")) {
                columns[i + 1] = splittedColumns[i].substring(0, splittedColumns[i].indexOf(",")).trim();
            } else {
                columns[i + 1] = splittedColumns[i].trim();
            }
        }
        return columns;
    }


    @Async
    public void processNotification(String idString) {
        try {
            List<String> ids = Arrays.asList(idString.split(",")).stream().filter(t -> !Strings.isNullOrEmpty(t)).collect(Collectors.toList());
            idString = StringUtil.join(",", ids);
            if (!idString.isEmpty()) {
                Map<String, Object> spParam = new LinkedHashMap<>();
                spParam.put("userName", idString);

                try {
                    log.info(idString);
                    Map<String, Object> procedureResult = jdbcFunctionDao.getProcedureResult(Constant.GET_ALL_NOTIFICATION, spParam);
               
                } catch (Exception e) {
                    log.error("Database Exception {}", e.getMessage());
                }
            }
        } catch (Exception ex) {
            log.error("Notification processing exception {}", ex.getMessage());
        }
    }


//    @Async
//    public void executeTrp(TrpIssueDto trpIssueDto) {
//
//        // Get the current date
//        LocalDate currentDate = LocalDate.now();
//        // Calculate the first day of the last month
//        LocalDate firstDayOfLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
//        // Calculate the last day of the last month
//        LocalDate lastDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(currentDate.minusMonths(1).lengthOfMonth());
//        // Define a date format
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        String tmcList = trpIssueDto.getTmcList().stream()
//                .map(TmcListDto::getId)
//                .map(String::valueOf)
//                .collect(Collectors.joining(","));
//
//        for (TmcListDto tmc : trpIssueDto.getTmcList()) {
//
//            if (Objects.nonNull(tmc.getDataProcessMethod()) && tmc.getDataProcessMethod().equalsIgnoreCase("aggregated")) {
//                tmcProcessor(trpIssueDto.getIncrementalId(), tmc.getId(), tmcList, firstDayOfLastMonth.format(dateFormatter), lastDayOfLastMonth.format(dateFormatter));
//                continue;
//            }
//
//            // Loop through the dates day by day
//            LocalDate startDatePointer = firstDayOfLastMonth;
//            LocalDate endDatePointer = lastDayOfLastMonth;
//
//            while (!startDatePointer.isAfter(endDatePointer)) {
//
//                String dateString = startDatePointer.format(dateFormatter);
//                tmcProcessor(trpIssueDto.getIncrementalId(), tmc.getId(), tmcList, dateString, dateString);
//                // Increment the date by one day
//                startDatePointer = startDatePointer.plus(1, ChronoUnit.DAYS);
//            }
//        }
//    }
//
//    @Async
//    public void tmcProcessor(Integer trpId, Integer tmcId, String tmcList, String startDate, String endDate) {
//        try {
//            Map<String, Object> spParam = new LinkedHashMap<>();
//
//            spParam.put("trpId", trpId);
//            spParam.put("tmcId", tmcId);
//            spParam.put("tmcList", tmcList);
//            spParam.put("startDate", startDate);
//            spParam.put("endDate", endDate);
//            jdbcFunctionDao.getProcedureResult(Constant.SP_TRP_EXECUTION, spParam);
//        } catch (Exception ex) {
//            log.error("Trp execution Failed {} {} {} {} {} {}", trpId, tmcId, tmcList, startDate, endDate, ex.getMessage());
//        }
//    }

    @Async
    public void addRecommendation(Integer userId, Integer issueId, AtomicReference<String> requestFrom, AtomicReference<String> recContent) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("objectId", issueId);
            params.put("requestFrom", requestFrom);
            params.put("jsonData", recContent.get());
            jdbcFunctionDao.getProcedureResult(Constant.INV_INS_REC, params);
        } catch (Exception e) {
            log.error("method report writing save: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
