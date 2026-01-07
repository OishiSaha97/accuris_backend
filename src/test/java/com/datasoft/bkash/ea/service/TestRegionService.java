//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.Region;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestRegionService  {
//
//    @Autowired
//    private RegionService regionService;
//
//
//
//    @Test
//    public void testFindById() {
//        Integer regionId = 4;
//        Region region = regionService.findById(regionId);
//        log.info("Region Data {} : ", region);
//    }
//
//
//    @Test
//    @Transactional
//    public void testListOfFCARegion() {
//        Iterable<Region> regionsList = regionService.getListOfFCARegion();
//        log.info("List of FCA Region: {}", regionsList);
//    }
//
//
//    @Test
//    @Transactional
//    public void testListOfCAODetails() {
//        List<Map<String, Object>> listOfCAODetails = regionService.getFCAODetailsByRegionId(7);
//        log.info("List of Unassigned Officers: {}", listOfCAODetails);
//    }
//
//
//    @Test
//    @Transactional
//    public void testListOfUnassignedOfficer() {
//        List<Map<String,Object>> unassignedOfficerList = regionService.getListOfUnassignedOfficer();
//        log.info("List of Unassigned Officers: {}", unassignedOfficerList);
//    }
//
//    @Test
//    @Transactional
//    public void testListOfAssignedOfficer() {
//        List<Map<String,Object>> assignedOfficerList = regionService.getListOfAssignedOfficer();
//        log.info("List of Assigned Officers: {}", assignedOfficerList);
//    }
//
//    @Test
//    @Transactional
//    public void testAssignListOfCao() {
//        Utils.doManualAuthentication("zahid556", "zil@ds");
//        List<Integer> userIdList = Arrays.asList(117,121,123);
//        Integer deployedbyUserId = 115;
//        Integer regionId = 7;
//        Map<String, Object> assigned = regionService.assignRegionToUnassignedOfficer(userIdList, deployedbyUserId, regionId);
//        log.info("List of Assigned Officers: {}", assigned);
//    }
//
//    /*@Test
//    @Transactional
//    public void testUnAssignListOfCao() {
//        Utils.doManualAuthentication("zahid556", "zil@ds");
//        List<Integer> userIdList = Arrays.asList(128, 134, 139);
//        Map<String, Object> unAssigned = regionService.removeAssignment(userIdList);
//        log.info("List of Assigned Officers: {}", unAssigned);
//    }*/
//
//}
