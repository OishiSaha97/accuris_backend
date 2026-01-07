package com.datasoft.bkash.ea.service.queryModule;

import com.datasoft.bkash.ea.response.ApiResponse;

import java.util.List;

public interface RemarkService {
    ApiResponse getRemark(String type,Integer id);
}
