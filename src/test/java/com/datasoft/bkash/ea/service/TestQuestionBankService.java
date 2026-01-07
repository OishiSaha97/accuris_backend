//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestQuestionBankService {
//    @Autowired
//    private QuestionBankService questionBankService;
//
//    @Test
//    public void testFindById() {
//        Integer questionBankId = 7;
//        QuestionBank questionBank = questionBankService.findById(questionBankId);
//        log.info("QuestionBank Data {} : ", questionBank);
//    }
//
//    @Test
//    @Transactional
//    public void testUpdate() {
//        Map<String, Object> map = new HashMap<>();
//        // do Manual authentication
//        manualAuthentication();
//        Integer quetionBankId = 1;
//        QuestionBank questionBank = questionBankService.findById(quetionBankId);
//        log.info("questionBank:{}", questionBank);
//        map = questionBankService.update(questionBank);
//        log.info("questionBank:{}", map);
//    }
//
//    @Test
//    @Transactional
//    public void testFindAllUnpublished() {
//        List<Map<String, Object>> maps = new LinkedList<>();
//        manualAuthentication();
//        maps = questionBankService.findAllUnpublished();
//        Map<String,Object> mapForQustionId1 = maps.stream().filter(stringObjectMap -> stringObjectMap.get("id").equals(1))
//                .findFirst().orElse(null);
//        log.info("maps:{}",maps);
//        log.info("mapForQustionId1:{}",mapForQustionId1);
//    }
//
//    public void manualAuthentication() {
//        String loginId = "dsashiful";
//        String password = "OTKsxAnK";
//        Utils.doManualAuthentication(loginId, password);
//    }
//}