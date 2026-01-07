//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.enums.PlanStatus;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.*;
//
//@SpringBootTest
//@Slf4j
//public class TestPlanService {
//    @Autowired
//    private PlanService planService;
//
//    @Autowired
//    private Gson gson;
//
//
//    @Test
//    public void save(){
//        List<Map<String, Object>> hashMaps = new ArrayList<>();
//
//        Map<String, Object> stringMap = new HashMap<>();
//        stringMap.put("assesseeId", 3);
//        stringMap.put("date", "2020-01-25");
//        stringMap.put("assessmentType", "RANDOM");
//        stringMap.put("id", 1);
//        stringMap.put("caoId", 3);
//
//        hashMaps.add(stringMap);
//
////        String json = "[[{caoId=3, id=1, assessmentType=RANDOM, assesseeId=3, date=2020-01-25}]]]";
////        List<Map<String, Object>> paramList = new Gson().fromJson(json, List.class);
//        Map<String, Object> map = planService.savePlan(hashMaps);
//        log.info("{}", map);
//    }
//    @Test
//    public void plans(){
//        Calendar cal=Calendar.getInstance();
//        cal.set(2022, 01, 17);
//        Date fromDate=cal.getTime();
//        cal.set(2022, 01, 19);
//        Date toDate=cal.getTime();
//        PlanStatus status=PlanStatus.EXPIRED;
//        AssesseeType assesseeType=AssesseeType.MERCHANT;
//        Page<Map<String, Object>> plans=planService.findAllByDateBetweenAndStatusAndAssessmentType(Optional.of(fromDate),
//                Optional.of(toDate),
//                Optional.empty(), Optional.empty(),
//                PageRequest.of(1, 5));
//        log.info("data size {}", plans.getSize());
//        log.info("content {}", gson.toJson(plans.getContent()));
//        log.info("=======================");
//        log.info("{}", gson.toJson(plans));
//
//
//    }
//
//}
