package com.datasoft.bkash.ea.dao.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommonDao {

    int changeStatus(Date updatedAt, Integer updatedByUserId, Integer id);

    int deleteById(Integer id);

    List<Map<String, Object>> findAllByStatus(boolean status);

    Page<Map<String, Object>> findAllByPage(Pageable pageable);

    Page<Map<String, Object>> findAllBySearchCriteria(String searchParam, Pageable pageable);


}
