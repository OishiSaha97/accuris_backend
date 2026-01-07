package com.datasoft.bkash.ea.responseFilter.service;

import com.datasoft.bkash.ea.response.ApiResponse;
import com.datasoft.bkash.ea.responseFilter.dto.ResponseFilterDto;
import com.datasoft.bkash.ea.responseFilter.dto.SseResponseDto;
import com.datasoft.bkash.ea.service.*;
import com.datasoft.bkash.ea.service.queryModule.*;
import com.datasoft.bkash.ea.utils.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class ResponseFilterServiceImpl implements ResponseFilterService {


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Map<String, SseEmitter> connections = new HashMap<>();

    @Value("${heavy-requests.file-path}")
    private String filePath;
    @Value("${heavy-requests.timeout}")
    private String timeout;

    @Async
    @Override
    public void request(ResponseFilterDto response) {
        try {
            Thread.sleep(1000); // Important
        } catch (InterruptedException ignored) {
        }
        ApiResponse info = null;
        try {
            info = getInfo(response);
            if (Objects.isNull(info)) {
                info = new ApiResponse(500, "No Data Found", null);
            }
        } catch (Exception e) {
            info = new ApiResponse(500, e.getMessage(), e.getCause());
        }
       // amqpProducer.sendResponse(SseResponseDto.builder().apiResponse(info).generatedId(response.getGeneratedId()).loginId(response.getLoginId()).build());

    }

    @Override
    public ResponseFilterDto prepareRequest(ResponseFilterDto responseFilterDto) {
//        responseFilterDto.setUserId(userService.getCurrentUserId());
        responseFilterDto.setLoginId(SecurityContextHolder.getContext().getAuthentication().getName());
        return responseFilterDto;
    }

    @Override
    public void sendResponse(SseResponseDto sseResponseDto, boolean retry) {
        if (connections.containsKey(sseResponseDto.getGeneratedId())) {
            SseEmitter emitter = connections.get(sseResponseDto.getGeneratedId());
            try {
                emitter.send(SseEmitter.event().name(sseResponseDto.getLoginId()).data(sseResponseDto.getApiResponse()));
                emitter.complete();
            } catch (Exception exception) {
                try {
                    Thread.sleep(1000);
                    emitter = connections.get(sseResponseDto.getGeneratedId());
                    emitter.send(SseEmitter.event().name(sseResponseDto.getLoginId()).data(sseResponseDto.getApiResponse()));
                    emitter.complete();
                } catch (Exception ignored) {
                }
            } finally {
                connections.remove(sseResponseDto.getGeneratedId());
            }
        } else {
            if (retry) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.sendResponse(sseResponseDto, false);
            }
        }
    }

//    @Override
//    public SseEmitter subscribe(String id) {
//        SseEmitter sseEmitter = new SseEmitter(Long.valueOf(timeout));
//        connections.put(id, sseEmitter);
//        return sseEmitter;
//    }

    @Override
    public SseEmitter subscribe(String id) {
        SseEmitter sseEmitter = new SseEmitter();
        connections.put(id, sseEmitter);

        scheduler.schedule(() -> {
            closeConnection(id);
        }, Long.valueOf(timeout), TimeUnit.MILLISECONDS);

        return sseEmitter;
    }
    public void closeConnection(String id) {
        SseEmitter sseEmitter = connections.remove(id);
        if (sseEmitter != null) {
            sseEmitter.complete();
        }
    }
    @Override
    public void download(String id, String type, HttpServletResponse response) {
        String filePath = this.filePath + id + "." + type;
        InputStream inputStream = null;
        File file = new File(filePath);
        try {
            if (file.exists() && file.isFile()) {
                String contentType = "";
                if (Objects.equals(type, "xlsx")) {
                    contentType = "application/vnd.ms-excel";
                } else if (Objects.equals(type, "csv")) {
                    contentType = "application/csv";
                } else if (Objects.equals(type, "zip")) {
                    contentType = "application/octet-stream";
                } else {
                    contentType = "application/pdf";
                }
                response.setContentType(contentType);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Report." + type);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentLengthLong(file.length());
                int read = -1;
                byte[] limit = new byte[1024];
                inputStream = Files.newInputStream(Paths.get(filePath));
                while ((read = inputStream.read(limit)) != -1) {
                    response.getOutputStream().write(limit, 0, read);
                }
                try {
                    inputStream.close();
                    response.getOutputStream().close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex.getCause());
                }
                FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
                if (Objects.nonNull(response.getOutputStream())) {
                    response.getOutputStream().close();
                }
            } catch (Exception ignored) {
            }
        }
    }
    private ApiResponse getInfo(ResponseFilterDto response) {
//        try {
//            if (Objects.equals(response.getType(), TRANSACTION_ANALYSIS)) {
//                return investigationService.transactionAnalysis(Objects.isNull(response.getHistoryCheckDto()) ? new HistoryCheckDto() : response.getHistoryCheckDto(), response.getUserId());
//            } else if (Objects.equals(response.getType(), HISTORY_CHECK)) {
//                try {
//                    HistoryCheckDto historyCheckDto = Objects.isNull(response.getHistoryCheckDto()) ? new HistoryCheckDto() : response.getHistoryCheckDto();
//                    return queryCommonService.enquiryHistoryCheck(
//                            historyCheckDto.getQueryCommonReqDtoList(),
//                            historyCheckDto.getAccountNumber(),
//                            historyCheckDto.getPhotoId(),
//                            historyCheckDto.getSearchParam(),
//                            historyCheckDto.getType(),
//                            historyCheckDto.getQueryIds(),
//                            historyCheckDto.getTin(),
//                            historyCheckDto.getTradeLicense(),
//                            response.getUserId()
//                    );
//                } catch (IllegalAccessException ignore) {
//
//                }
//            } else if (Objects.equals(response.getType(), ACCOUNT_QUERY_DATA)) {
//                return accountQueryService.getAccountInfo(response.getConfigParams(), response.getServletRequest(), response.getUserId());
//            } else if (Objects.equals(response.getType(), HISTORY_DATA_REPORT)) {
//                return dataUploadService.fetchReport(response.getParam(), response.getUserId());
//            }
//            else if (Objects.equals(response.getType(), INVESTIGATION_REPORT_WRITING_LIST)) {
//                return investigationService.writeReportList(
//                        response.getMap().get("queryId"),
//                        response.getMap().get("param"),
//                        response.getMap().get("type"),
//                        response.getMap().get("accountNumber"),
//                        response.getMap().get("photoId"),
//                        response.getMap().get("tag"),
//                        response.getServletRequest(),
//                        response.getUserId()
//                );
//            }
//            else if (Objects.equals(response.getType(), QUERY_ENQUIRY_AGAINST)) {
//                return queryCommonService.addEnquiryAgainst(response.getEnquiryAgainstReqDtos());
//            }  else if (Objects.equals(response.getType(), ACCOUNT_QUERY_UPLOAD_EXCEL)) {
//                return accountQueryService.accountQueryUploadedFileProcess(response.getApiResponse(), response.getUserId());
//            } else if (Objects.equals(response.getType(), RECOMMENDATION_UPLOAD_EXCEL)) {
//                return recommendationService.recommendationUploadedFileProcess(response.getApiResponse(), response.getUserId());
//            } else if (Objects.equals(response.getType(), INVESTIGATION_IRM_ATTACHMENT)) {
//                return investigationService.investigationIRMAttachment(response.getParam());
//            } else if (Objects.equals(response.getType(), RECOMMENDATION_DOWNLOAD_EXCEL)) {
//                return recommendationService.exportRecommendationList(response.getConfigParams(), response.getGeneratedId(), response.getUserId());
//            } else if (Objects.equals(response.getType(), INVESTIGATION_DOWNLOAD_GENERIC_EXCEL)) {
//                return investigationService.generateGenericReport(response.getHistoryCheckDto(), response.getUserId(), response.getGeneratedId());
//            } else if (Objects.equals(response.getType(), INVESTIGATION_DYNAMIC_TRANSACTION_EXCEL)) {
//                return investigationService.exportDynamicTransactionTypeExcel(response.getHistoryCheckDto(), response.getUserId(), response.getGeneratedId());
//            } else if (Objects.equals(response.getType(), QUERY_REPORT)) {
//                return queryReportService.generateReport(response.getParams(), response.getUserId(), response.getGeneratedId());
//            } else if (Objects.equals(response.getType(), ACCOUNT_QUERY_DOWNLOAD_PDF)) {
//                return accountQueryService.downloadPdf(response.getParamList(), response.getUserId(), response.getGeneratedId());
//            } else if (Objects.equals(response.getType(), INVESTIGATION_MONITOR_LIST_DOWNLOAD)) {
//                return investigationService.downloadMonitorList(response.getConfigParams(), response.getUserId(), response.getGeneratedId());
//            } else if (Objects.equals(response.getType(), APPROVAL_LIST)) {
//                return approvalService.list(response.getConfigParams(), response.getRequestUri(), response.getUserId());
//            } else if (Objects.equals(response.getType(), WORKFLOW_APPROVER_LIST)) {
//                return approvalWorkflowService.approverList(
//                        response.getEventId(),
//                        response.getConfigParams(),
//                        response.getSearchParam(),
//                        response.getOrderParam(),
//                        response.getOrderType(),
//                        response.getPageable(),
//                        response.getRequestUri(),
//                        response.getUserId());
//            }else if (Objects.equals(response.getType(), USERLIST)) {
//                Object pid = response.getConfigParams().getPid();
//                if(pid != null && !(pid instanceof String && ((String) pid).trim().isEmpty())){
//                    return userService.downloadUserList(response.getConfigParams(), response.getUserId(), Constant.SP_GET_USER_DETAIL,response.getResponse());
//                }
//                else {
//                    return userService.downloadUserList(response.getConfigParams(), response.getUserId(), Constant.SP_GET_USER_LIST,response.getResponse());
//                }
//           }

//        } catch (Exception e) {
//            log.error("Error Message =>{}, Error Reason => {}, Stacktrace => ", e.getMessage(), e.getCause(), e);
//        }
        return null;
    }
}
