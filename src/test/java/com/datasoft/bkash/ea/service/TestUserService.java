//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.*;
//import com.datasoft.bkash.bkashAml360.model.enums.ApproveStatus;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import com.datasoft.bkash.bkashAml360.repository.UsersRepository;
//import com.datasoft.bkash.bkashAml360.utils.SmtpEmailService;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import jakarta.transaction.Transactional;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestUserService {
//
//    @Autowired
//    private Gson gson;
//    @Value("${email_base_url}")
//    private String emailBaseUrl;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private SmtpEmailService emailService;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//    @Autowired
//    private AssessmentService assessmentService;
//
//    @Test
//    public void findById() {
//        Integer userId = 5;
//        Optional<User> user = userService.findById(userId);
//        log.info("User data : {}", gson.toJson(user));
//    }
//
//    @Test
//    public void findByLoginId() {
//        String loginId = "sohag";
//        User user = userService.findByLoginId(loginId);
//        log.info("User data : {}", gson.toJson(user));
//    }
//
//    @Test
//    public void findByLoginIdAndPassword() {
//        String loginId = "sohag";
//        String pass = "1234";
//        Optional<User> user = userService.findByLoginIdAndPassword(loginId, pass);
//        log.info("User login data : {}", user);
//    }
//
//    @Test
//    public void save() {
////        Task task1 = new Task();
////        task1.setTaskName("User Modification");
////        task1.setUrl("/user/edit");
////        task1.setIsSubTask(Boolean.TRUE);
////
////        Task task2 = new Task();
////        task2.setTaskName("Active User List");
////        task2.setUrl("/user/active");
////        task2.setIsSubTask(Boolean.TRUE);
////
////        Set<Task> taskList = new HashSet<>();
////        taskList.add(task1);
////        taskList.add(task2);
////
////        Role role = new Role();
////        role.setRoleName("FCA Admin");
////        role.setStatus((short) 1);
////        role.setTasks(taskList);
////        Set<Role> roleList = new HashSet<>();
////        roleList.add(role);
////
////        User user = new User();
////        user.setStatus(UserStatus.NEW);
////        user.setRoles(roleList);
////        user.setUserType("CAO");
////        user.setUserName("RakibulIslam");
////        user.setLoginId("rakib58");
////        user.setIsCAO(true);
////        user.setEmail("msohag7860@gmail.com");
////        user.setPhoneNumber("01758696989");
//////        userService.save(user);
//    }
//
//    @Test
//    public void updateStatusEmail() {
//        userService.updateStatus(6, "ACTIVE", null);
//    }
//
//    @Test
////    @Transactional
//    public void findAll() {
//        Iterable<User> users = userService.findAll();
//        log.info("User data : {}", users);
//    }
//
//    @Test
//    public void update() {
//
////        Task task1 = new Task();
////        task1.setTaskName("User Modification");
////        task1.setUrl("/user/edit");
////        task1.setId(1);
////        task1.setIsSubTask(Boolean.TRUE);
//////        task1.setAgTask(agt);
////
////        Task task2 = new Task();
////        task2.setTaskName("Active User List");
////        task2.setUrl("/user/active");
////        task2.setId(2);
////        task2.setIsSubTask(Boolean.TRUE);
//////        task2.setAgTask(agt);
////
////        Set<Task> taskList = new HashSet<>();
////        taskList.add(task1);
////        taskList.add(task2);
////
////        Role role = new Role();
////        role.setId(1);
////        role.setRoleName("FCA Admin");
////        role.setStatus((short) 1);
////        role.setTasks(taskList);
////        Set<Role> roleList = new HashSet<>();
////        roleList.add(role);
////
////        String status = "active";
////        User user = new User();
////        user.setActive(Boolean.TRUE);
////        user.setStatus(UserStatus.valueOf(status));
////        user.setIsCAO(Boolean.TRUE);
////        user.setRoles(roleList);
////        user.setId(1);
//////        userService.update(user);
////        log.info("All {} User data : {}", status, gson.toJson(user));
//    }
//
//    @Test
//    public void testChangePassword() {
//        String oldPassword = "bkash1234";
//        String newPassword = "bkash12345";
//        String confirmPassword = "bkash12345";
//        Map<String, Object> map = new HashMap<>();
//        map.put("oldPassword", oldPassword);
//        map.put("newPassword", newPassword);
//        map.put("confirmPassword", confirmPassword);
//        Map<String, Object> map1 = userService.changePassword(map);
//        log.info("map: {}", map1);
//    }
//
//    @Test
//    public void testForgetPassword() {
//        String loginId = "shetu12345678";
//        String email = "shshetu2017@gmail.com";
//        //input map
//        Map<String, Object> inputMap = new HashMap<>();
//        inputMap.put("loginId", loginId);
//        inputMap.put("email", email);
//        //test success
//        Assert.assertEquals(userService.forgetPassword(inputMap).get("status"), Boolean.TRUE);
//
//    }
//
//    @Test
//    @Transactional
//    public void testMatchPassword() {
//        Integer userId = 79;
//        User user = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId, ErrorCodes.U_0000));
//        String encryptPass = user.getPassword();
//        Assert.assertTrue(passwordEncoder.matches("VUmp4So3", encryptPass));
//    }
//
//    @Test
//    public void encryptPassword() {
//        String pass = passwordEncoder.encode("bkash123");
//        log.info(pass);
//    }
//
////    @Test
////    public void findAllApprovalPendingUser() {
////        List<Map<String, Object>> data = userService.findAllUserFromTemp(null);
////        log.info("All Approval Pending User data: {}", data);
////    }
//
//    @Test
//    public void fetchEditHistory() {
//        Integer tempId = 452;
//        Map<String, Object> data = userService.fetchEditHistory(tempId);
//        log.info("All Approval Pending User data: {}", data);
//    }
//
//    @Test
//    public void findListOfCao() {
//        List<Integer> ids = new ArrayList<>();
//        ids.add(1);
//        ids.add(2);
//        Optional<List<Integer>> regionIdList = Optional.of(ids);
//        Set<User> data = userService.findListOfCao(Optional.empty());
//        log.info("User data: {}", data);
//        Set<User> data1 = userService.findListOfCao(regionIdList);
//        log.info("User data: {}", data1);
//    }
//
//    @Test
//    @Transactional
//    public void testFindAssessingCAOsFromAssessmentTable() {
//        String assesseeType = "dao";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//        Optional<String> regionName = Optional.of("Dhaka North");
//        Optional<String> dhAccount = Optional.of("01833328255");
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> users = userService.findAssessmentConductingCAOs(assesseeType, fromDate, toDate, regionName, dhAccount);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},users:{}", executionTime, users);
//    }
//
//    @Test
//    @Transactional
//    public void testFindAssessingCAOsFromAssessmentTableForDhAccount() {
//        // AssesseeType
//        AssesseeType assesseeType = AssesseeType.DAO;
//        // Date
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-10";
//        String pattern = "yyyy-MM-dd";
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            startDate = new SimpleDateFormat(pattern).parse(fromDate);
//            endDate = new SimpleDateFormat(pattern).parse(toDate);
//        } catch (ParseException e) {
//            throw new RuntimeException("Invalid Date format. Date not parsable");
//        }
//        if (startDate.after(endDate)) {
//            throw new RuntimeException(startDate + "is greater than " + endDate);
//        }
//        startDate = Utils.getStartOfDay(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        endDate = Utils.getEndOfDay(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        String regionName = "Dhaka North";
//        String dhAccount = "01833328255";
//        double startTime = System.currentTimeMillis();
//        List<Assessment> assessments = assessmentRepository.findAllByAssesseeTypeAndCreatedAtBetweenAndAssessedByUserIsNotNullAndUserRegion_Region_RegionNameIgnoreCaseAndApproveStatusNot(assesseeType, startDate, endDate, regionName, ApproveStatus.PENDING);
//        switch (assesseeType) {
//            case DH:
//                assessments = assessments.stream()
//                        .filter(assessment -> assessment.getAccountNumber() != null && assessment.getAccountNumber().equalsIgnoreCase(dhAccount))
//                        .collect(Collectors.toList());
//                break;
//            case DAO:
//                assessments = assessments.stream()
//                        .filter(assessment -> assessment.getDao().getDhAccountNumber() != null && assessment.getDao().getDhAccountNumber().equalsIgnoreCase(dhAccount))
//                        .collect(Collectors.toList());
//                break;
//            case AGENT:
//                assessments = assessments.stream()
//                        .filter(assessment -> assessment.getAgent().getDhMasterAcctNumber() != null && assessment.getAgent().getDhMasterAcctNumber().equalsIgnoreCase(dhAccount))
//                        .collect(Collectors.toList());
//                break;
//            case DSO:
//                assessments = assessments.stream()
//                        .filter(assessment -> assessment.getDso().getDhMasterAcctNumber() != null && assessment.getDso().getDhMasterAcctNumber().equalsIgnoreCase(dhAccount))
//                        .collect(Collectors.toList());
//                break;
//        }
//        List<User> users = assessments.stream().map(Assessment::getAssessedByUser).collect(Collectors.toList());
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},users:{}", executionTime, users);
//    }
//
//    @Test
//    @Transactional
//    public void testFindCAOSummaryReportUsers() {
//        // Region Name
//        String regionName = "Dhaka North";
//        // Date
//        LocalDate fromDate = LocalDate.of(2020, 5, 1);
//        LocalDate toDate = LocalDate.of(2020, 5, 15);
//
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> users = userService.findCAOSummaryReportUsersFromAssessmentTable(regionName, fromDate, toDate);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},users:{}", executionTime, users);
//
//    }
//
////    final  static String CONTENT_FOR_USER_APPROVAL = "A new user is pending in Bkash AML System with login id %s.\nPlease take necessary step for this user by clicking this link bellow \n"+emailBaseUrl+"/user/new.\n\nThanks\nBkash AML Team";
//   /*public static String content(){
//       return CONTENT_FOR_USER_APPROVAL;
//   }*/
//    @Test
//    public void testEmailSending() {
////      final String CONTENT_FOR_USER_APPROVAL = "A new user is pending in Bkash AML System with login id %s.\nPlease take necessary step for this user by clicking this link bellow \n"+"%s"+"/user/new.\n\nThanks\nBkash AML Team";
////        String contentForNewUserCreation = String.format(CONTENT_FOR_USER_APPROVAL,"shetu123",emailBaseUrl);
////        String contentForNewUserCreation = String.format(content(),"shetu123");
//         String contentForNewUserCreation = String.format(Strings.CONTENT_FOR_USER_APPROVAL,"shetu123",emailBaseUrl);
//        log.info("user:{}",contentForNewUserCreation);
//    }
//
//
//}
