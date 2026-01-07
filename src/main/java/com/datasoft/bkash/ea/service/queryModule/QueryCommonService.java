package com.datasoft.bkash.ea.service.queryModule;

import com.datasoft.bkash.ea.model.queryModule.EnquiryAgainstReqDto;
import com.datasoft.bkash.ea.model.queryModule.QueryCommonReqDto;
import com.datasoft.bkash.ea.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface QueryCommonService {
    ApiResponse fetchList(Map<String, Object> params);

    ApiResponse queryInitialData(List<QueryCommonReqDto> queryCommonReqDtos) throws IllegalAccessException;

    ApiResponse addEnquiryAgainst(List<EnquiryAgainstReqDto> enquiryAgainstReqDtos);

    ApiResponse enquiryHistoryCheck(List<QueryCommonReqDto> queryCommonReqDtos, String accountNumber, String photoId, String searchParam, String type, String queryIds, String tin, String tradeLicense, Integer userId) throws IllegalAccessException;

    ApiResponse uploadEnquiryAgainst(MultipartFile file) throws IOException;

//    ApiResponse respondStatus(QueryRespondStatusDto queryRespondStatusDto);

    void queryViewPdf(String param, Integer id, HttpServletResponse response);

    ApiResponse historyCheckRemarkAdd(Map<String,Object> remarkData, HttpServletRequest request);

    ApiResponse victimList(String param, String clid, String accountNumber, HttpServletRequest request);

    ApiResponse executeUnlockRecord(Map<String, Object> params);
}
