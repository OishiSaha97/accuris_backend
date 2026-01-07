//package com.datasoft.bkash.bkashAml360.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//import java.util.Set;
//
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestDhService {
//    @Autowired
//    private DHService dhService;
//
//    @Test
//    public void testfindById() {
//        String assesseeType = "Agent";
//        Set<String> dh = dhService.findById("1", assesseeType);
//        log.info("{}", dh);
//    }
//
//    @Test
//    @Transactional
//    public void testDhDistrictList() {
//        dhService.dhDistrictList();
//
//    }
//
//    @Test
//    @Transactional
//    public void testDhThanaList() {
//        Optional<String> disName = Optional.of("Bogra");
//        dhService.dhThanaList(disName);
//    }
//
//    @Test
//    @Transactional
//    public void testDhAreaList() {
//        Optional<String> disName = Optional.of("Bogra");
//        Optional<String> thanaName = Optional.of("Bogra Sadar");
//        dhService.dhAreaList(disName,thanaName);
//    }
//
//
////    @Test
////    @Transactional
////    public void testFindDHList() {
////        String assesseeType = "DH";
////        Optional<String> district = Optional.ofNullable("Bogra");
////        Optional<String> dhAccount = Optional.empty();
////        Optional<String> thana =  Optional.ofNullable("Bogra Sadar");
////        Optional<String> area =  Optional.ofNullable("Bogra");
////        Pageable pageable = PageRequest.of(0, 10);
////        dhService.findDHList(assesseeType, dhAccount, district, thana, area, pageable);
////
////    }
//
//    @Test
//    @Transactional
//    public void testFindThanListByIdAndDistrictName() {
//        String accNo = "01833328255";
//        String assesseeType = "AGENT";
//
//        Set<String> data = dhService.findThanListByIdAndDistrictName(accNo, Optional.empty(), assesseeType);
//        log.info("Thana List: {}", data);
//    }
//
//}
