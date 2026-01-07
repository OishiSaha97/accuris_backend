//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.Assessee;
//import com.datasoft.bkash.bkashAml360.repository.AssesseeRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestAssesseeService {
//    @Autowired
//    private AssesseeService assesseeService;
//    @Autowired
//    private AssesseeRepository assesseeRepository;
//
//
//    @Test
//    public void testFindById() {
//        Integer assesseId = 5;
//        Assessee assessee = assesseeService.findById(assesseId);
//        log.info("Assesse Data {} : ", assessee);
//    }
//
//    @Test
//    public void testAsseseList() {
//        List<Assessee> assessees = assesseeService.findAll();
//        log.info("{}", assessees);
//    }
//
//    @Test
//    public void testAsseseList1() {
//        Iterable<Assessee> assessees = assesseeRepository.findAll();
//        log.info("{}", assessees);
//    }
//
//    @Test
//    public void splitting() {
//        String d = "test|masum";
//        String[] test = d.split("\\|");
//        log.info(Arrays.toString(test));
//    }
//}
