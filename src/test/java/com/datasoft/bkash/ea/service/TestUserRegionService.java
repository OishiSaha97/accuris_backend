//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.UserRegion;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import com.datasoft.bkash.bkashAml360.repository.UserRegionRepository;
//import com.datasoft.bkash.bkashAml360.repository.UsersRepository;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import jakarta.transaction.Transactional;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestUserRegionService {
//    @Autowired
//    private UserRegionService userRegionService;
//    @Autowired
//    private UserRegionRepository userRegionRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//
//
//    @Test
//    public void getRegion() {
//        Integer userId = 115;
//        UserRegion userRegion = userRegionService.findCurrentDeployedRegionByUserId(userId);
//        log.info("{}", userRegion);
//    }
//
//    @Test
//    @Transactional
//    public void testUserExisting() {
//        //Find User
//        Integer userId = 109;
//        User user = usersRepository.findById(109).orElseThrow(() -> new ResourceNotFoundException(userId, ErrorCodes.U_0000));
//        //Find Date Range
//        String fromDate = "2020-03-28";
//        String toDate = "2020-03-29";
//        String pattern = "yyyy-MM-dd";
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = new SimpleDateFormat(pattern).parse(fromDate);
//            endDate = new SimpleDateFormat(pattern).parse(toDate);
//        } catch (ParseException e) {
//            throw new ResourceNotFoundException("Date not parsable", ErrorCodes.DF_0000);
//        }
//        startDate = Utils.getStartOfDay(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        endDate = Utils.getEndOfDay(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        AssesseeType assesseeType = AssesseeType.AGENT;
//        List<Assessment> assessments = assessmentRepository.findAllByAssesseeTypeAndCreatedAtBetween(assesseeType, startDate, endDate);
//        List<User> users = assessments.stream().map(Assessment::getAssessedByUser).distinct().collect(Collectors.toList());
//        Date finalStartDate = startDate;
//        Date finalEndDate = endDate;
//
//        users.forEach(
//                user1 -> {
//                    List<UserRegion> userRegionsCurrentlyAssigned = userRegionRepository.findByUserAndDeploymentStartDateLessThanEqualAndDeploymentEndDateIsNull(user1, finalEndDate);
//                    List<UserRegion> userRegionsPreviouslyAssigned = userRegionRepository.findByUserAndDeploymentStartDateGreaterThanEqualAndDeploymentEndDateLessThanEqual(user1, finalStartDate, finalEndDate);
//                    log.info("userRegions currently size:{}, current:{}, userRegions previously size:{}, previous:{}", userRegionsCurrentlyAssigned.size(), userRegionsCurrentlyAssigned, userRegionsPreviouslyAssigned.size(), userRegionsPreviouslyAssigned);
//                }
//
//        );
//
//    }
//
//    @Test
//    public void regionCurrentUserRegion() {
//        Integer userId = 115;
//        UserRegion userRegion = userRegionService.findCurrentDeployedRegionByUserId(userId);
//        log.info("UserRegion: {}", userRegion);
//    }
//
//    @Test
//    public void testFindRegionsByAssesseeTypeAndDateRange() {
//        String assesseeType = "merchant";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> regions = userRegionService.findAssessedRegions(assesseeType, fromDate, toDate);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime-startTime)/1000;
//        log.info("executionTime:{},regions:{}", executionTime,regions);
//    }
//
//
//}
//
