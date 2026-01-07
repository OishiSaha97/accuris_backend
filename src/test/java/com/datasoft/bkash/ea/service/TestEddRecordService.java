//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.SearchCriteria;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.repository.UserRegionRepository;
//import com.datasoft.bkash.bkashAml360.service.edd.EddRecordService;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.*;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestEddRecordService {
//    @Autowired
//    private UserRegionRepository userRegionRepository;
//    @Autowired
//    private EddRecordService eddRecordService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//
//    @Test
//    public void testEddRegions() {
//
//        String assesseeType = "dao";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> eddRegions = eddRecordService.findAssessedEddRegions(assesseeType, fromDate, toDate);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},regions:{}", executionTime, eddRegions);
//    }
//
//    @Test
//    public void testEddUsers() {
//        String assesseeType = "dao";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//        Optional<String> regionName = Optional.of("Dhaka North");
//        Optional<String> dhAccount = Optional.of("01833328255");
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> users = eddRecordService.findAssessmentConductingEddUsers(assesseeType, fromDate, toDate, regionName,dhAccount);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},users:{}", executionTime, users);
//    }
//
//    @Test
//    @Transactional
//    public void testEdddDhs() {
//        Optional<String> regionName = Optional.of("Dhaka North");
////        Optional<String> regionName = Optional.of("");
////        Optional<String> userId = Optional.of("");
//        Optional<String> userId = Optional.of("130");
//        String assesseeType = "merchant";
//        LocalDate startDate = LocalDate.of(2020, 5, 1);
//        LocalDate endDate = LocalDate.of(2020, 5, 10);
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> dhs = eddRecordService.findEddAssessedDhs(regionName,userId,assesseeType,startDate,endDate);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime-startTime) / 1000;
//        log.info("executionTime:{},dhs:{}",executionTime,dhs);
//    }
//
//    /////  ########################  Testing for Multiple Critera of EDD Report  ########################################
//    // Input For Users:
//    // CAO List without DH
//    public List<Map<String, Object>> inputFileForEddCAOListWithoutDH() {
//        String assesseeType = "MERCHANT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 7;
//        List<String> dhAccounts = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = eddRecordService.findEddCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime,users);
//        return users;
//    }
//
//    // CAO List with Dh For : Agent
//    public List<Map<String, Object>> inputFileForEddCAOListWithDHForAgent() {
//        String assesseeType = "AGENT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = eddRecordService.findEddCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, With DH For Agent: {}", executionTime,users);
//        return users;
//    }
//
//    // CAO List with Dh For : DSO
//    public List<Map<String, Object>> inputFileForEddCAOListWithDHForDSO() {
//        String assesseeType = "DSO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = eddRecordService.findEddCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("Execution Time:{}, With DH For DSO: {}",executionTime,users);
//        return users;
//    }
//
//    // CAO List with Dh For : DAO
//    public List<Map<String, Object>> inputFileForEddCAOListWithDHForDAO() {
//        String assesseeType = "DAO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        double startTime = System.nanoTime();
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        List<Map<String, Object>> users = eddRecordService.findEddCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("Execution Time:{}, With DH For DAO: {}", executionTime,users);
//        return users;
//    }
//
//    // CAO List with Dh For : DH
//    public List<Map<String, Object>> inputFileForEddCAOListWithDHForDH() {
//        String assesseeType = "DH";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = eddRecordService.findEddCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("Execution Time:{}, With DH For DH: {}", executionTime,users);
//        return users;
//    }
//
//    // Users
//    @Test
//    @Transactional
//    public void testFindCaosForEddReportForMulitpleRegionsAndMultipleDhs() {
//        List<Map<String, Object>> users;
//
//        // CAO List without DH
////     users = inputFileForEddCAOListWithoutDH();
//
//        // CAO List with Dh For : Agent
//        users = inputFileForEddCAOListWithDHForAgent();
//
//        // CAO List with Dh For : DSO
////        users = inputFileForEddCAOListWithDHForDSO();
//
//        // CAO List with Dh For : DAO
////        users = inputFileForEddCAOListWithDHForDAO();
//
//        // CAO List with Dh For : DH
////        users = inputFileForEddCAOListWithDHForDH();
//    }
//
//
//    // Input For Dhs:
//    //1. Dh list Without CAO
//    // For Agent
//    public List<Map<String, Object>> inputFileForEdddhListWithoutCAOForAgent() {
//        String assesseeType = "AGENT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = eddRecordService.findEddDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime,dhs);
//        return dhs;
//    }
//
//    // For Dh
//    public List<Map<String, Object>> inputFileForEdddhListWithoutCAOForDh() {
//        String assesseeType = "DH";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = eddRecordService.findEddDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime,dhs);
//        return dhs;
//    }
//
//    // For Dao
//    public List<Map<String, Object>> inputFileForEdddhListWithoutCAOForDao() {
//        String assesseeType = "DAO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = eddRecordService.findEddDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime,dhs);
//        return dhs;
//    }
//
//    // For DSO
//    public List<Map<String, Object>> inputFileForEdddhListWithoutCAOForDso() {
//        String assesseeType = "DSO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = eddRecordService.findEddDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime,dhs);
//        return dhs;
//    }
//    // Dhs
//    @Test
//    @Transactional
//    public void testFindDhsForMultipleRegionsAndMultipleCaos() {
////1. Dh list Without CAO:
//        List<Map<String,Object>> dhs;
//        // For Agent
//        dhs = inputFileForEdddhListWithoutCAOForAgent();
//
//        // For Dh
////        dhs = inputFileForEdddhListWithoutCAOForDh();
//        // For Dso
////        dhs = inputFileForEdddhListWithoutCAOForDso();
//        // For Dao
////        dhs = inputFileForEdddhListWithoutCAOForDao();
//    }
//
//    // Filter Edd
//    // inputFileForEddForAgent
//    public void inputFileForEddForAgent(){
//        SearchCriteria searchCriteria = new SearchCriteria();
//
//        // AssesseeType
//        searchCriteria.setAssesseeType(AssesseeType.AGENT);
//
//        // Date Range: fromDate, toDate
//        searchCriteria.setFromDate(LocalDate.of(2020,5,12));
//        searchCriteria.setToDate(LocalDate.of(2020,6,7));
//
//        // Region: List<String> regionNames
//        List<String> regionNames = new LinkedList<>();
////        regionNames.add("Dhaka North");
//        regionNames.add("Comilla");
//        searchCriteria.setRegionNames(regionNames);
//
//        // User: List<Integer> userIds
//        List<Integer> userIds = new LinkedList<>();
//        userIds.add(133);
////        userIds.add(107);
//        searchCriteria.setUserIds(userIds);
//        // DH: List<String> dhAccounts
//        List<String> dhAccounts = new LinkedList<>();
//        dhAccounts.add("01944449800");
////        dhAccounts.add("01708420850");
//        searchCriteria.setDhAccounts(dhAccounts);
//
//        // QuestionVersion:
//        searchCriteria.setQuestionVersion("2020-R1-V-0.0.1");
//
//        // Page
//        Pageable pageable = PageRequest.of(0,10);
//        double startTime = System.nanoTime();
//        Page<Map<String, Object>> assessments = eddRecordService.filterEdd(searchCriteria,pageable);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Assessments: {}", executionTime,assessments);
//    }
//    // inputFileForEddForDh
//    // inputFileForEddForForDso
//    // inputFileForEddForDao
//    // inputFileForEddForMerchant
//    public void inputFileForEddForMerchant(){
//        SearchCriteria searchCriteria = new SearchCriteria();
//
//        // AssesseeType
//        searchCriteria.setAssesseeType(AssesseeType.MERCHANT);
//
//        // Date Range: fromDate, toDate
//        searchCriteria.setFromDate(LocalDate.of(2020,5,1));
//        searchCriteria.setToDate(LocalDate.of(2020,5,27));
//
//        // Region: List<String> regionNames
//        List<String> regionNames = new LinkedList<>();
//        regionNames.add("Dhaka North");
//        regionNames.add("Comilla");
//        searchCriteria.setRegionNames(regionNames);
//
//        // User: List<Integer> userIds
//        List<Integer> userIds = new LinkedList<>();
//        userIds.add(130);
//        userIds.add(107);
//        searchCriteria.setUserIds(userIds);
//
//        // QuestionVersion:
//        searchCriteria.setQuestionVersion("2020-MR1-V-0.0.1");
//
//        // dhs
//        List<String> dhs = null;
//        searchCriteria.setDhAccounts(dhs);
//        // Page
//        Pageable pageable = PageRequest.of(0,10);
//        double startTime = System.nanoTime();
//        Page<Map<String, Object>> assessments = eddRecordService.filterEdd(searchCriteria,pageable);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime-startTime)/Math.pow(10,9);
//        log.info("ExecutionTime:{}, Assessments: {}", executionTime,assessments);
//    }
//
//    @Test
//    @Transactional
//    public void testFilterEdd(){
//        Utils.doManualAuthentication("hisham","bKash123");
//        inputFileForEddForAgent();
//       // inputFileForEddForEddReportForMerchant();
//    }
//
//
//
//
//
//}
