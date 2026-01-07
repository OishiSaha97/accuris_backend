//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.transaction.Transactional;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.*;
//
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestReportService {
//    @Autowired
//    private ReportService reportService;
//    @Autowired
//    private Utils utils;
//    // Test: Cao Summary Questionnaire
//    @Test
//    @Transactional
//    public void testQuestionnaires() {
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> questionnaires = reportService.findCaoSummaryQuestionnaires(fromDate, toDate, regionNames);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Questionnaires: {}", executionTime, questionnaires);
//    }
//
//    // Test: Cao Summary Users
//    @Test
//    @Transactional
//    public void testSummaryUsers() {
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 7;
//        List<String> dhAccounts = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = reportService.findCaoSummaryUsers(fromDate, toDate, regionNames);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Summary Users: {}", executionTime, users);
//    }
//
//    /// Test: Filter CAO Summary Report
//    @Test
//    @Transactional
//    public void testCaoSummaryReport(){
//        Utils.doManualAuthentication("dsashiful","OTKsxAnK");
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North","Comilla");
//        List<Integer> userIds = Arrays.asList(107,130);
//        Integer questionnaireId = 7;
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String,Object>> reports = reportService.filterCaoSummaryReport(fromDate,toDate,regionNames,userIds,questionnaireId);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Summary reports: {}", executionTime, reports);
//    }
//
//    @Test
//    public void testUserMapList(){
//        // map1
//        Map<String,Object> map1 = new HashMap<>();
//
//                // map2
//        Map<String,Object> map2 = new HashMap<>();
//    }
//
//    @Test
//    public void testEddExcel() throws IOException {
///*
//* Path path = Paths.get("/path/to/the/file.txt");
//String name = "file.txt";
//String originalFileName = "file.txt";
//String contentType = "text/plain";
//byte[] content = null;
//try {
//    content = Files.readAllBytes(path);
//} catch (final IOException e) {
//}
//* */
//
///*
//* MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(new File("/home/admin/test.xlsx")));
//* */
//        String tempFilePath = "C:\\\\\\\\Users\\\\\\\\Arafat\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\Official\\\\\\\\25-08-20\\\\\\\\Compiled_10_regions_EDD_Sample_Data1_modifying.xlsx";
//       /* Path path = Paths.get(tempFilePath);
//        byte[] content = null;
//        try {
//        content = Files.readAllBytes(path);
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
////        String filePath = "F:\\\\\\\\Sohag\\\\\\\\Data\\\\\\\\Edd\\\\\\\\edd - data_total_200.xlsx";
//        File file = new File(tempFilePath);
//        MultipartFile multipartFile = new MockMultipartFile("test.xlsx", new FileInputStream(file));
//       Map<String,Object> controlReports = reportService.readControlReportExcelData(multipartFile);
//       log.info("controlReports:{}",controlReports);
//    }
//
//    @Test
//    public void testEddExcelUpload() throws IOException {
////        String tempFilePath = "C:\\\\\\\\Users\\\\\\\\Arafat\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\Official\\\\\\\\25-08-20\\\\\\\\Compiled_10_regions_EDD_Sample_Data1_modifying.xlsx";
//        String tempFilePath = "C:\\\\\\\\Users\\\\\\\\Arafat\\\\\\\\OneDrive\\\\\\\\Desktop\\\\\\\\Official\\\\\\\\25-08-20\\\\\\\\Document 10.docx";
//       /* Path path = Paths.get(tempFilePath);
//        byte[] content = null;
//        try {
//        content = Files.readAllBytes(path);
//        }catch (Exception e){
//            e.printStackTrace();
//        }*/
////        String filePath = "F:\\\\\\\\Sohag\\\\\\\\Data\\\\\\\\Edd\\\\\\\\edd - data_total_200.xlsx";
//        Utils.doManualAuthentication("Hisham","bKash123");
//        File file = new File(tempFilePath);
//        MultipartFile multipartFile = new MockMultipartFile("test.xlsx",file.getName(), "text/plain", new FileInputStream(file));
//        Map<String,Object> controlReports = reportService.uploadEddExcel(multipartFile);
//        log.info("controlReports:{}",controlReports);
//    }
//}
