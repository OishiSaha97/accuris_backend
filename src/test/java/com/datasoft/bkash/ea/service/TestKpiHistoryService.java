//package com.datasoft.bkash.bkashAml360.service;
//
//
//import com.datasoft.bkash.bkashAml360.model.User;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.*;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestKpiHistoryService {
//
//    @Autowired
//    private KPIRepository kpiRepository;
//
//    @Autowired
//    private KpiHistoryService kpiHistoryService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private KPIService kpiService;
//
//    @Test
//    public void testKPIHistorySave() {
//        User user = userService.findByLoginId("zahid556");
//        List<KPI> deletableKpiData = kpiRepository.findByUser(user);
//
//        List<Map<String, Object>> auditKpi = new ArrayList<>();
//        deletableKpiData.forEach(kpi -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", kpi.getId());
//            map.put("regionName", kpi.getRegion().getRegionName());
//            map.put("assesseeType", kpi.getAssessee() != null ? kpi.getAssessee().getAssesseeType() : null);
//            map.put("userId", kpi.getUser() != null ? kpi.getUser().getId() : null);
//            map.put("month", kpi.getMonth());
//            map.put("year", kpi.getYear());
//            map.put("isValid", kpi.getIsValid());
//            map.put("isPublished", kpi.getIsPublished());
//            map.put("isRevised", kpi.getIsRevised());
//            map.put("target", kpi.getTarget());
//            map.put("targetEdd", kpi.getTargetEDD());
//            map.put("targetRandom", kpi.getTargetRandom());
//            map.put("userRegionId", kpi.getUserRegion() != null ? kpi.getUserRegion().getId() : null);
//
//            auditKpi.add(map);
//
//        });
//
//        Map<String, Object> response = kpiHistoryService.save(user, auditKpi);
//        log.info("Response : {}", response);
//    }
//
//    @Test
//    public void deleteInactiveUserKpiTest() {
//        User user = userService.findByLoginId("zahid556");
//        Map<String, Object> response = kpiService.deleteInactiveUserKpi(user);
//        log.info("Response : {}", response);
//    }
//
//}
