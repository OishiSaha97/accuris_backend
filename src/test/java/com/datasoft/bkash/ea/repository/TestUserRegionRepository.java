//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.UserRegion;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class TestUserRegionRepository {
//    @Autowired
//    private UserRegionRepository userRegionRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//
//    @Test
//    @Transactional
//    public void testCurrentUser() {
//        //User
//        Integer userId = 107;
//        User user = usersRepository.findById(userId).get();
//        //Date Range
//        String fromDate = "2020-03-26";
//        String toDate = "2020-03-29";
//        String pattern = "yyyy-MM-dd";
//        String assesseeType = "agent";
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = new SimpleDateFormat(pattern).parse(fromDate);
//            endDate = new SimpleDateFormat(pattern).parse(toDate);
//        } catch (ParseException e) {
//            throw new ResourceNotFoundException("Date not parsable", ErrorCodes.DF_0000);
//        }
//
//        //Assessee
//        AssesseeType assesseeTypeStrict = null;
//        try {
//            assesseeTypeStrict = AssesseeType.valueOf(assesseeType.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            log.info("assesseeType:{}", assesseeType);
//            throw new ResourceNotFoundException(null, ErrorCodes.A_0000);
//        }
//        List<Assessment> assessments = assessmentRepository.findAllByAssesseeTypeAndCreatedAtBetween(assesseeTypeStrict, startDate, endDate);
//        List<User> users = assessments.stream().map(Assessment::getAssessedByUser).distinct().collect(Collectors.toList());
//        Date finalStartDate = startDate;
//        Date finalEndDate = endDate;
//        users = users.stream()
//                .filter(tempUser ->
//                                userRegionRepository.existsByUserAndDeploymentStartDateGreaterThanEqualAndDeploymentEndDateLessThanEqual(tempUser,finalStartDate,finalEndDate) ||
//                        userRegionRepository.existsByUserAndDeploymentStartDateBetweenAndDeploymentEndDateIsNull(tempUser, finalStartDate, finalEndDate)).collect(Collectors.toList());
//
//        log.info("users:{}",users);
//    }
//
//    @Test
//    public void testCurrentRegion(){
//        Integer regionId = 4;
//        Integer userId = 115;
//        UserRegion userRegion = userRegionRepository.findByRegionIdAndUserIdAndDeploymentEndDateIsNull(regionId, userId);
//        log.info("UserRegion: {}", userRegion);
//    }
//
//    @Test
//    public void testEddRegions(){
//        String assesseeType = "dao";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//        List<Map<String,Object>> eddRegions = userRegionRepository.findEddRegionsByAssesseeTypeAndDateRange(assesseeType,fromDate,toDate);
//        log.info("EDD Regions:{}",eddRegions);
//    }
//}
