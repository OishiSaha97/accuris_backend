//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.repository.UsersRepository;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestAssessmentService {
//    @Autowired
//    private AssessmentService assessmentService;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Test
//    public void testFindAllPending() {
//        List<String> dhs = assessmentService.findAllDhByAssessee(Optional.of("Dhaka"), AssesseeType.AGENT.name(), LocalDate.of(2020, 4, 10), null);
//        log.info("Pending Assessments Data {} : ", dhs);
//    }
//
//    @Test
//    public void testFindAssessedDHs() {
//
//    }
//
//    @Test
//    @Transactional
//    public void testFindAssessedDhs() {
////        Optional<String> regionName = Optional.of("Dhaka North");
//        Optional<String> regionName = Optional.of("");
//        Optional<String> userId = Optional.of("");
////        Optional<String> userId = Optional.of("130");
//        String assesseeType = "merchant";
//        LocalDate startDate = LocalDate.of(2020, 5, 1);
//        LocalDate endDate = LocalDate.of(2020, 5, 10);
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> dhs = assessmentService.findAssessedDhs(regionName, userId, assesseeType, startDate, endDate);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},dhs:{}", executionTime, dhs);
//    }
//
//    @Test
//    @Transactional
//    public void testFindAssessedQuestionnaireVersionsForCAOSummaryReport() {
//        LocalDate fromDate = LocalDate.of(2020, 5, 1);
//        LocalDate toDate = LocalDate.of(2020, 5, 10);
//        String regionName = "Dhaka North";
//
//        String userId = "130";
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> qustionnaries = assessmentService.findCAOSummaryReportQuestionnaireVersions( fromDate, toDate, regionName, userId);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},questionnaires:{}", executionTime, qustionnaries);
//    }
//
//    @Test
//    @Transactional
//    public void testCaoSummaryReportOld(){
//        Utils.doManualAuthentication("dsashiful","OTKsxAnK");
//        LocalDate fromDate = LocalDate.of(2020,5,1);
//        LocalDate toDate = LocalDate.of(2020,5,27);
//        Integer caoId =130;
//        Optional<String> questionVersion = Optional.of("2020-MR1-V-0.0.1");
//        List<Map<String,Object>> reports = assessmentService.caoAssessmentSummaryReport(fromDate,Optional.of(toDate),caoId,questionVersion);
//        log.info("reports:{}",reports);
//    }
//
//}
