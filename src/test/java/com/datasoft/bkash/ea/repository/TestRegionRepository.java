//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Region;
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.UserRegion;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//import java.util.*;
//
//@SpringBootTest
//@Slf4j
//public class TestRegionRepository {
//    @Autowired
//    private RegionRepository regionRepository;
//    @Autowired
//    private UserRegionRepository userRegionRegionRepository;
//
//
//    @Test
//    @Transactional
//    public void testFindAll() {
//        Iterable<Region> regionList = regionRepository.findAll();
//        log.info("{}", regionList);
//    }
//
//
//    @Test
//    @Transactional
//    public void testFindById() {
//        Optional<Region> region = regionRepository.findById(4);
//        log.info("{}", region.get());
//    }
//
//
//    @Test
//    public void testSave() {
//        Region region = new Region();
//        //For Created by user
//        User user = new User();
//        user.setId(1);
//        region.setCreateBy(user);
//
//        region.setRegionName("test-Dhaka");
//        region = regionRepository.save(region);
//        log.info("Inserted Region id : {}", region.getId());
//    }
//
//
//    @Test
//    public void testUpdate() {
//        Region region = new Region();
//        region.setId(4);
//        User user = new User();
//        user.setId(1);
//        region.setCreateBy(user);
//        region.setRegionName("Rajshahi");
//        region = regionRepository.save(region);
//        log.info("Inserted Region id : {}", region.getId());
//    }
//
//    @Test
//    @Transactional
//    public void findByDeploymentStartDateIsNotNullAndDeploymentEndDateIsNull() {
//        List<UserRegion> userRegionsList = userRegionRegionRepository.findByDeploymentStartDateIsNotNullAndDeploymentEndDateIsNull();
//        List<Map<String, Object>> userRegionList = new ArrayList<>();
//        userRegionsList.forEach(
//                userRegion -> {
//                    if (userRegion.getUser() != null) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("userId", userRegion.getUser().getId());
//                        map.put("user_name", userRegion.getUser().getUserName());
//                        map.put("phone_number", userRegion.getUser().getPhoneNumber());
//                        map.put("email", userRegion.getUser().getEmail());
//                        map.put("designation", userRegion.getUser().getDesignation());
//                        if (userRegion.getRegion() != null) {
//                            map.put("current_deployment_region", userRegion.getRegion().getRegionName());
//                            map.put("current_deployment_date", userRegion.getDeploymentStartDate());
//                        }
//                        userRegionList.add(map);
//                    }
//                }
//        );
//        log.info("User List size: {}, Users: {}",userRegionList.size(),userRegionList);
//    }
//}
