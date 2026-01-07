//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestDHRepository {
//    @Autowired
//    private DHRepository dhRepository;
//    @Autowired
//    private DSORepository dsoRepository;
//
//
//    @Test
//    @Transactional
//    public void getDistrictList() {
//        Optional<DH> districtName = dhRepository.findById(1);
//        Set<String> namesList = districtName.get().getDso().stream().map(DSO::getDistrictName).collect(Collectors.toSet());
//        log.info("{}", namesList);
//    }
//
//
//    @Test
//    public void testDistrict() {
//        Map<String, Set<String>> stringMap = new HashMap<>();
//        Set<String> thana = new HashSet<>();
//        thana.add("Kaliganj");
//        thana.add("Dhaka");
//        stringMap.put("Thana", thana);
//        System.out.println(new Gson().toJson(stringMap));
//    }
//
//
//    @Test
//    public void testfindByDsoIdIn(){
//        Integer dsoId = 1;
//        DSO dso = dsoRepository.findById(dsoId).get();
//        DH dh = dhRepository.findByDso(dso).get();
//        log.info("dhName:{}",dh.getDhName());
//    }
//
//    @Test
//    @Transactional
//    public void testFindAllByRegionNameAndAccountNumberIgnoreCaseContainingAndDistrictNameIgnoreCaseContainingAndThanaNameIgnoreCaseContainingAndBusinessAreaIgnoreCaseContaining(){
//        String regionName = "Dhaka North";
////        Optional<String> accountNumber = Optional.of("01833328255");
//        Optional<String> accountNumber = Optional.of("");
////        String district = "GAZIPUR";
////        Optional<String> district = Optional.of("");
//        Optional<String> district = Optional.of("GAZIPUR");
////        String thana = "TONGI";
//        Optional<String> thana = Optional.of("TONGI");
//        Optional<String> area = Optional.of("gazipur");
//        Pageable pageable = PageRequest.of(0, 10);
//       Page<DH> dhPage = dhRepository.findAllByRegionNameAndAccountNumberIgnoreCaseContainingAndDistrictNameIgnoreCaseContainingAndThanaNameIgnoreCaseContainingAndBusinessAreaIgnoreCaseContaining(regionName,accountNumber,district,thana,area,pageable);
//       List<DH> dhs = dhPage.getContent();
//       log.info("dhs:{}",dhs);
//
//    }
//}
