package com.datasoft.bkash.ea.dao;

import com.datasoft.bkash.ea.dao.common.CommonDao;

import java.util.List;
import java.util.Map;

public interface OrganizationDao extends CommonDao {



    List<Map<String, Object>> findAllNameAndIdOrderByNameAsc();

    Map<String, Object> findById(Integer id);

    Map<String, Object> customFindById(Integer id);

    List<Map<String, Object>> findAllById(Integer id);

    List<Map<String, Object>> findBkash(String name);

    List<Map<String, Object>> findAllTrainingOrganization(boolean status);
}
