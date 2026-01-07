//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Assessee;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//@Slf4j
//public class TestAssesseeRepository {
//    @Autowired
//    private AssesseeRepository assesseeRepository;
//
//    @Test
//    public void testFindAll() {
//        Iterable<Assessee> assesseList = assesseeRepository.findAll();
//        log.info("{}", assesseList);
//    }
//
//    @Test
//    public void testFindById() {
//        Optional<Assessee> assesse = assesseeRepository.findById(8);
//        log.info("{}", assesse.get());
//    }
//
//    @Test
//    public void testSave() {
//        Assessee assessee = new Assessee();
////        assessee.setAssesseeType(AssesseeType.AGENT);
////        assesse.setCreateBy(1);
//        assessee = assesseeRepository.save(assessee);
//        log.info("Inserted Assesse id : {}", assessee.getId());
//    }
//
//    @Test
//    public void testMalfunctionedSave() {
//        Assessee assessee = new Assessee();
//        assessee.setAssesseeType(null);
//        assessee = assesseeRepository.save(assessee);
//        log.info("Inserted Assesse id : {}", assessee.getId());
//    }
//
//
//    @Test
//    public void testUpdate() {
//        Assessee assessee = new Assessee();
////        assessee.setAssesseeType(AssesseeType.MERCHANT);
//        assessee = assesseeRepository.save(assessee);
//        log.info("Inserted Assesse id : {}", assessee.getId());
//    }
//
//    @Test
//    public void findByAssesseeType() {
//        String value = "AGENT";
//        Optional<AssesseeType> assesseeType = Optional.of(AssesseeType.valueOf(value.toUpperCase()));
//        try {
//            Optional<Assessee> assessee1 = assesseeRepository.findByAssesseeType(assesseeType);
//            if (assessee1.isPresent()) {
//                log.info("assessee:{} ", assessee1.get());
//            }
//        } catch (Exception e) {
//            log.info("assessee:{}", (Object) null);
//        }
//
//
//
//    }
//}
