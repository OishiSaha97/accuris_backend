//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.*;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Child;
//import com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Questionnaires;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.Question;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.*;
//import com.datasoft.bkash.bkashAml360.repository.*;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.StringUtils;
//
//import javax.transaction.Transactional;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//@Slf4j
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class TestValidationService {
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private DHRepository dhRepository;
//    @Autowired
//    private AssessmentResponseRepository assessmentResponseRepository;
//    @Autowired
//    private SectionRepository sectionRepository;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//    @Autowired
//    private ValidationService validationService;
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Test
//    public void testNullCheck() {
////        User user = new User();
//        Optional<User> userOptional = usersRepository.findById(1500);
//        User user = new User();
//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//        }
//        log.info("userId:{}", user.getId());
//    }
//
//    @Test
//    public void testEmptyDH() {
//        String dhAccount = null;
//        Optional<DH> dhOptional = dhRepository.findByAccountNumber(dhAccount);
//        DH dh = dhOptional.orElseGet(DH::new);
//        log.info("dhId:{}", dh.getId());
//    }
//
//    @Test
//    public void testEmptyList() {
////        List<Integer> integerList = Arrays.asList(1,2,3,4);
//        List<Integer> integerList = new LinkedList<>();
////        integerList.forEach(System.out::println);
//        integerList.forEach(integer -> {
//            System.out.println("OK");
//        });
//        log.info("size:{}, integers: {}", integerList.size(), integerList);
//    }
//
//    @Test
//    @Transactional
//    public void testJsonParseForAssRes_ValueColumn() {
//        String secId = "s-6f9a2b69-a171-4b85-91d6-ec97469f2ce7";
//        Section section = sectionRepository.findById(secId).orElseThrow(() -> new ResourceNotFoundException(secId, ErrorCodes.SEC_0010));
//        List<Question> questions = section.getQuestions();
//        questions.forEach(question -> {
//            List<AssessmentResponse> assessmentResponses = assessmentResponseRepository.findByQuestion(question);
//            log.info("queId:{}, asessmentResSize:{},assessmentRes:{}", question.getId(), assessmentResponses.size(), assessmentResponses);
//            assessmentResponses.forEach(response -> {
//                log.info("response:{}", response);
//                String resType = response.getValue();
//                log.info("resType:{}", resType);
//                ObjectMapper objectMapper = new ObjectMapper();
//                if (resType != null) {
//                    try {
//                        List<String> values = (List<String>) objectMapper.readValue(resType, List.class);
//                        log.info("size:{},values:{}", values.size(), values);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            });
//        });
//    }
//
//    @Test
//    public void testJsonParsing() throws IOException {
//        String dbString = "[\"10\",\"50\"]";
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<String> strings = (List<String>) (objectMapper.readValue(dbString, List.class));
//        log.info("size:{},list:{}", strings.size(), strings);
//    }
//
//    @Test
//    public void testNullDH() {
////        String dhAccount = null;
//        String dhAccount = "01782866200";
//        DH dh = new DH();
//        if (dhAccount != null) {
////            DH dh1 = dhRepository.findByAccountNumber(dhAccount).orElseGet(DH::new);
//            dh = dhRepository.findByAccountNumber(dhAccount).orElseGet(DH::new);
//            log.info("dh:{}", dh);
//        }
//        log.info("dhId: {}", dh.getId());
//    }
//
//    @Test
//    @Transactional
//    public void testPercentage() {
//        String assessmentId = "9db5d966-60d2-4ec2-8a82-86bf86556255";
//        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new ResourceNotFoundException(assessmentId, ErrorCodes.ASS_0000));
//        log.info("assessmentId:{},assessment:{}", assessment.getId(), assessment);
//        Double totalScoreInPercent = null;
//        if (assessment.getQuestionnaire() != null) {
//            Questionnaire questionnaireFromAssessment = assessment.getQuestionnaire();
//            log.info("questionnaireId:{},questionnaire:{}", questionnaireFromAssessment.getId(), questionnaireFromAssessment);
//            if (questionnaireFromAssessment.getTotalScore() != null && questionnaireFromAssessment.getTotalScore() > 0) {
//                if (assessment.getTotalScore() != null && assessment.getTotalScore() >= 0 && questionnaireFromAssessment.getTotalScore() >= assessment.getTotalScore()) {
//                    Double queTotalScore = Double.valueOf(questionnaireFromAssessment.getTotalScore());
//                    Double asTotalScore = Double.valueOf(assessment.getTotalScore());
////                    queTotalScore = 0.0;
////                    asTotalScore =1.0;
//                    totalScoreInPercent = (asTotalScore * 100) / queTotalScore;
//                    log.info("totalScoreInPercent:{}", totalScoreInPercent);
//                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
//                    totalScoreInPercent = Double.parseDouble(decimalFormat.format(totalScoreInPercent));
//                    log.info("totalScoreInPercent:{}", totalScoreInPercent);
//
//                }
//            }
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testValidationServcie() {
//        Utils.doManualAuthentication("dsashiful", "OTKsxAnK");
//        AssesseeType assesseeType = AssesseeType.AGENT;
//        LocalDate toDate = LocalDate.of(2020, 5, 1);
//        LocalDate fromDate = LocalDate.of(2020, 5, 6);
//        String questionVersion = "";
//        SearchCriteria searchCriteria = new SearchCriteria();
//        searchCriteria.setAssesseeType(assesseeType);
//        searchCriteria.setFromDate(fromDate);
//        searchCriteria.setToDate(toDate);
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Map<String, Object>> page = validationService.filterFCADetail(searchCriteria, pageable);
//        log.info("Page:{}", page);
//    }
//
//    /// Test: Regions
//    @Test
//    @Transactional
//    public void testRegions() {
//        String assesseeType = "MERCHANT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> regions = validationService.findFcaAssessmentRegions(assesseeType, fromDate, toDate);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Regions: {}", executionTime, regions);
//    }
//
//    // Test: Quesitonnaires
//    @Test
//    @Transactional
//    public void testQuestionnaires() {
//        String assesseeType = "MERCHANT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> questionnaires = validationService.findQuestionnairesForMultipleRegions(assesseeType, fromDate, toDate, regionNames);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Questionnaires: {}", executionTime, questionnaires);
//    }
//
//    // Input For Users:
//    // CAO List without DH
//    public List<Map<String, Object>> inputFileCAOListWithoutDH() {
//        String assesseeType = "MERCHANT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 7;
//        List<String> dhAccounts = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = validationService.findCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime, users);
//        return users;
//    }
//
//    // CAO List with Dh For : Agent
//    public List<Map<String, Object>> inputFileCAOListWithDHForAgent() {
//        String assesseeType = "AGENT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = validationService.findCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, With DH For Agent: {}", executionTime, users);
//        return users;
//    }
//
//    // CAO List with Dh For : DSO
//    public List<Map<String, Object>> inputFileCAOListWithDHForDSO() {
//        String assesseeType = "DSO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = validationService.findCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("Execution Time:{}, With DH For DSO: {}", executionTime, users);
//        return users;
//    }
//
//    // CAO List with Dh For : DAO
//    public List<Map<String, Object>> inputFileCAOListWithDHForDAO() {
//        String assesseeType = "DAO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        double startTime = System.nanoTime();
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        List<Map<String, Object>> users = validationService.findCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("Execution Time:{}, With DH For DAO: {}", executionTime, users);
//        return users;
//    }
//
//    // CAO List with Dh For : DH
//    public List<Map<String, Object>> inputFileCAOListWithDHForDH() {
//        String assesseeType = "DH";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<String> dhAccounts = Arrays.asList("01833328255", "01708420850");
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> users = validationService.findCaosForMulitpleRegionsAndMultipleDhs(assesseeType, fromDate, toDate, regionNames, questionnaireId, dhAccounts);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("Execution Time:{}, With DH For DH: {}", executionTime, users);
//        return users;
//    }
//
//    // Users
//    @Test
//    @Transactional
//    public void testFindCaosForMulitpleRegionsAndMultipleDhs() {
//        List<Map<String, Object>> users;
//
//        // CAO List without DH
////        users = inputFileCAOListWithoutDH();
//
//        // CAO List with Dh For : Agent
////        users = inputFileCAOListWithDHForAgent();
//
//        // CAO List with Dh For : DSO
////        users = inputFileCAOListWithDHForDSO();
//
//        // CAO List with Dh For : DAO
////        users = inputFileCAOListWithDHForDAO();
//
//        // CAO List with Dh For : DH
////        users = inputFileCAOListWithDHForDH();
//    }
//
//
//    // Input For Dhs:
//    //1. Dh list Without CAO
//    // For Agent
//    public List<Map<String, Object>> inputFiledhListWithoutCAOForAgent() {
//        String assesseeType = "AGENT";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = validationService.findDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime, dhs);
//        return dhs;
//    }
//
//    // For Dh
//    public List<Map<String, Object>> inputFiledhListWithoutCAOForDh() {
//        String assesseeType = "DH";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 11;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = validationService.findDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime, dhs);
//        return dhs;
//    }
//
//    // For Dao
//    public List<Map<String, Object>> inputFiledhListWithoutCAOForDao() {
//        String assesseeType = "DAO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = validationService.findDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime, dhs);
//        return dhs;
//    }
//
//    // For DSO
//    public List<Map<String, Object>> inputFiledhListWithoutCAOForDso() {
//        String assesseeType = "DSO";
//        String fromDate = "2020-05-01";
//        String toDate = "2020-05-27";
//        List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//        Integer questionnaireId = 6;
//        List<Integer> userIds = new LinkedList<>();
//        // Method execution time:
//        double startTime = System.nanoTime();
//        List<Map<String, Object>> dhs = validationService.findDhsForMultipleRegionsAndMulitpleCaos(assesseeType, fromDate, toDate, regionNames, questionnaireId, userIds);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Without DH: {}", executionTime, dhs);
//        return dhs;
//    }
//
//    // Dhs
//    @Test
//    @Transactional
//    public void testFindDhsForMultipleRegionsAndMulitpleCaos() {
////1. Dh list Without CAO:
//        List<Map<String, Object>> dhs;
//        // For Agent
////        dhs = inputFiledhListWithoutCAOForAgent();
//
//        // For Dh
////        dhs = inputFiledhListWithoutCAOForDh();
//        // For Dso
////        dhs = inputFiledhListWithoutCAOForDso();
//        // For Dao
////        dhs = inputFiledhListWithoutCAOForDao();
//    }
//
//    // Filter FCA Details
//    // inputFileForFCADetailsForAgent
//    public void inputFileForFCADetailsForAgent() {
//        SearchCriteria searchCriteria = new SearchCriteria();
//
//        // AssesseeType
//        searchCriteria.setAssesseeType(AssesseeType.AGENT);
//
//        // Date Range: fromDate, toDate
//        searchCriteria.setFromDate(LocalDate.of(2020, 5, 1));
//        searchCriteria.setToDate(LocalDate.of(2020, 5, 27));
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
//        // DH: List<String> dhAccounts
//        List<String> dhAccounts = new LinkedList<>();
//        dhAccounts.add("01833328255");
//        dhAccounts.add("01708420850");
//        searchCriteria.setDhAccounts(dhAccounts);
//
//        // QuestionVersion:
//        searchCriteria.setQuestionVersion("2020-AR1-V-0.0.4");
//
//        // Page
//        Pageable pageable = PageRequest.of(0, 10);
//        double startTime = System.nanoTime();
//        Page<Map<String, Object>> assessments = validationService.filterFCADetail(searchCriteria, pageable);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Assessments: {}", executionTime, assessments);
//    }
//
//    // inputFileForFCADetailsForDh
//    // inputFileForFCADetailsForDso
//    // inputFileForFCADetailsForDao
//    // inputFileForFCADetailsForMerchant
//    public void inputFileForFCADetailsForMerchant() {
//        SearchCriteria searchCriteria = new SearchCriteria();
//
//        // AssesseeType
//        searchCriteria.setAssesseeType(AssesseeType.MERCHANT);
//
//        // Date Range: fromDate, toDate
//        searchCriteria.setFromDate(LocalDate.of(2020, 5, 1));
//        searchCriteria.setToDate(LocalDate.of(2020, 5, 27));
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
//        Pageable pageable = PageRequest.of(0, 10);
//        double startTime = System.nanoTime();
//        Page<Map<String, Object>> assessments = validationService.filterFCADetail(searchCriteria, pageable);
//        double endTime = System.nanoTime();
//        double executionTime = (endTime - startTime) / Math.pow(10, 9);
//        log.info("ExecutionTime:{}, Assessments: {}", executionTime, assessments);
//    }
//
//    @Test
//    @Transactional
//    public void testFilterFCADetails() {
//        Utils.doManualAuthentication("dsashiful", "OTKsxAnK");
//        // inputFileForFCADetailsForAgent();
//        inputFileForFCADetailsForMerchant();
//    }
//
//    @Test
//    @Transactional
//    public void convertIntegerListToString() {
//        List<Integer> userIdList = Arrays.asList(1, 2, 3);
//        String joinedList = userIdList.stream().map(String::valueOf).collect(Collectors.joining(","));
//        log.info("Joined List:{}", joinedList);
//    }
//
//    @Test
//    public void testPendingAssessmentCount() {
//        List<Map<String, Object>> data = validationService.pendingAssessmentCount();
//        log.info(String.valueOf(data));
//    }
//
//    @Test
//    @Transactional
//    public void testFindValidationByAssessment() {
//        Assessment assessment = assessmentRepository.findById("17ec7faf-5cd1-401d-8566-d4ebd4417281").orElseGet(Assessment::new);
//        String assessmentId = assessment.getId();
//        Validation validation = validationService.findByAssessment(assessment);
//        String remarks = validation.getRemark();
//        log.info("remarks:{}", remarks);
//    }
//
//    @Test
//    public void findEddFeedBacks() {
//        ControlReport controlReport1 = new ControlReport();
//        controlReport1.setAccountNumber("cr1");
//        controlReport1.setFeedback("feedback1");
//
//        ControlReport controlReport2 = new ControlReport();
//        controlReport2.setAccountNumber("cr2");
//        controlReport2.setFeedback("");
//
//        List<ControlReport> controlReports = Arrays.asList(controlReport1, controlReport2);
//        Assessment assessment = new Assessment();
//        assessment.setId("a1");
//        assessment.setControlReport(controlReports);
//        String eddFeedBack = validationService.findEddFeedBacks(assessment);
//        log.info("edd feed back:{}", eddFeedBack);
//    }
//
//    @Test
//    public void testAddAnswerTypeDetailKeyOnAnswerTypeObjectOfQuestionnaireObject() {
//        // Assessment Id which has answer template of Range type
//        String assessmentId = "df2d09bd-643f-4b30-ad44-de1edfc3b971";
//        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new RuntimeException("Assessment Not found"));
//        Questionnaire questionnaire = assessment.getQuestionnaire();
//
//        // Get the QuestionBank Json
//        QuestionBank questionBank = questionnaire.getPublishedQuestionBank().getQuestionBank();
//        String json = questionBank.getQuestionSet();
//        try {
//            Questionnaires frontEndQuestionnaire = new ObjectMapper().readValue(json, Questionnaires.class);
//            if (frontEndQuestionnaire != null && frontEndQuestionnaire.getQuestionnaire() != null && !frontEndQuestionnaire.getQuestionnaire().isEmpty()) {
//                List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Questionnaire> fSections = frontEndQuestionnaire.getQuestionnaire();
//                if (fSections != null && !fSections.isEmpty()) {
//                    for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Questionnaire fSection : fSections) {
//                        List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Question> questions = fSection.getQuestions();
//                        for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Question question : questions) {
//                            List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.AnswerType> answerTypes = question.getAnswerTypes();
//                            for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.AnswerType answerType : answerTypes) {
//                                String type = answerType.getType();
//                                if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("range")) {
//                                    List<Child> children = answerType.getChildren();
//                                    if(children !=null && !children.isEmpty()){
//                                        String rangeType = children.get(0).getRangeType();
//                                        log.info("rangeType:{}", rangeType);
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testAnswerTypeDetailsAdditionToASpecificQuestion(){
//        String assessmentId = "df2d09bd-643f-4b30-ad44-de1edfc3b971";
//        Assessment assessment = assessmentRepository.findById(assessmentId).orElseThrow(() -> new RuntimeException("Assessment Not found"));
//        Questionnaire questionnaire = assessment.getQuestionnaire();
//        questionnaire = addAnswerTypeDetailsToEachQuestionOfRangeType(questionnaire);
//        log.info("questionnaire:{}",questionnaire);
//    }
//
//    // 1. create a method which takes Questionnaire and traverses through each Question and call the following methods and returns the Questionnaire
//    public Questionnaire addAnswerTypeDetailsToEachQuestionOfRangeType(Questionnaire questionnaire){
//        List<Section> sections = questionnaire.getSections();
//        if(sections != null && !sections.isEmpty()){
//            for(Section section:sections){
//                List<Question> questions = section.getQuestions();
//                if(questions != null && !questions.isEmpty()){
//                    for(Question question:questions){
//                        if(question != null && !StringUtils.isEmpty(question.getId())){
//                            AnswerTemplate answerTemplate =  question.getAnswerTemplate();
//                            if(answerTemplate != null && answerTemplate.getAnswerType() != null && answerTemplate.getAnswerType().equals(AnswerType.RANGE)){
//                                String rangeType = findRangeTypeOfQuestion(questionnaire,question);
//                                   if(!StringUtils.isEmpty(rangeType)){
//                                    question.setAnswerTemplate(convertAnswerTemplateToAnswerTemplateDTO(answerTemplate,rangeType));
//                                   }
//
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//        return questionnaire;
//    }
//
//    // 2. Create a method which will take questionnaire and question objects and returns the rangeType
//    public String findRangeTypeOfQuestion(Questionnaire backEndQuestionnaire,Question backEndQuestion){
//        String rangeType = "";
//        QuestionBank questionBank = backEndQuestionnaire.getPublishedQuestionBank().getQuestionBank();
//        String json = questionBank.getQuestionSet();
//        try {
//            Questionnaires frontEndQuestionnaire = new ObjectMapper().readValue(json, Questionnaires.class);
//            if (frontEndQuestionnaire != null && frontEndQuestionnaire.getQuestionnaire() != null && !frontEndQuestionnaire.getQuestionnaire().isEmpty()) {
//                List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Questionnaire> fSections = frontEndQuestionnaire.getQuestionnaire();
//                if (fSections != null && !fSections.isEmpty()) {
//                    for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Questionnaire fSection : fSections) {
//                        List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Question> questions = fSection.getQuestions();
//                        for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.Question question : questions) {
//                            if(question.getId().equalsIgnoreCase(backEndQuestion.getId())){
//                                List<com.datasoft.bkash.bkashAml360.model.frontendquestionarie.AnswerType> answerTypes = question.getAnswerTypes();
//                                for (com.datasoft.bkash.bkashAml360.model.frontendquestionarie.AnswerType answerType : answerTypes) {
//                                    String type = answerType.getType();
//                                    if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("range")) {
//                                        List<Child> children = answerType.getChildren();
//                                        if(children !=null && !children.isEmpty()){
//                                            rangeType = children.get(0).getRangeType();
//                                            log.info("rangeType:{}", rangeType);
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return rangeType;
//    }
//    // 3. Create a method which will send the answerType of the question and the rangeType and returns the answerTemplate Dto
//    public AnswerTemplateDTO convertAnswerTemplateToAnswerTemplateDTO(AnswerTemplate answerTemplate,String answerTypeDetails) {
//        AnswerTemplateDTO answerTemplateDTO = new AnswerTemplateDTO();
//            answerTemplateDTO.setId(answerTemplate.getId());
//            answerTemplateDTO.setAnswerType(answerTemplate.getAnswerType());
//            answerTemplateDTO.setOptions(answerTemplate.getOptions());
//            answerTemplateDTO.setCreatedBy(answerTemplate.getCreatedBy());
//            answerTemplateDTO.setUpdatedBy((answerTemplate.getUpdatedBy()));
//            answerTemplateDTO.setCreatedAt(answerTemplate.getCreatedAt());
//            answerTemplateDTO.setUpdatedAt((answerTemplate.getUpdatedAt()));
//            // set answerTypeDetails
//            answerTemplateDTO.setAnswerTypeDetails(answerTypeDetails);
//
//        return answerTemplateDTO;
//    }
//
//    @Test
//    @Transactional
//    public void testSettingAnswerTemplate() {
//        // find question by id
//        String questionId = "q-f979470b-d614-46ae-8bd8-f83d9a98c961";
//        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
//     /*   String answerTemplateId = "9868e6e7-fc23-44aa-824a-94f94b2ccc2d";
//        AnswerTemplate answerTemplate =  assessmentRepository.findAnswerTemplateById(answerTemplateId);*/
//        AnswerTemplate answerTemplate = question.getAnswerTemplate();
//        String answerTypeDetails = "date";
//        AnswerTemplateDTO answerTemplateDTO = convertAnswerTemplateToAnswerTemplateDTO(answerTemplate,answerTypeDetails);
//        log.info("answer template Dto:{}", answerTemplateDTO);
//        question.setAnswerTemplate(answerTemplateDTO);
//        log.info("question:{}",question);
//    }
//
//
//}
