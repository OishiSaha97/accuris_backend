package com.datasoft.bkash.ea.dao;

import java.util.List;
import java.util.Map;

public interface AmlEvidenceDao {
    List<Map<String, Object>> getFilesByEddId(String queryId);
}
