//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.questionnaire.AnswerTemplate;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.AnswerType;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.Question;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.Section;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestQuestionnaireService {
//
//    @Autowired
//    private QuestionnaireService questionnaireService;
//    @Autowired
//    private PublishedQuestionBankService publishedQuestionBankService;
//    @Autowired
//    private AssessmentResponseRepository assessmentResponseRepository;
//    @Autowired
//    private AssessmentRepository assessmentRepository;
//
//    @Test
//    public void save() {
//        //question
//        Question question1 = new Question();
//        question1.setQuestionNameBangla("Bengali Question 1");
//        question1.setQuestionNameEnglish("English Question 1");
//
//        Question question2 = new Question();
//        question2.setQuestionNameBangla("Bengali Question 2");
//        question2.setQuestionNameEnglish("English Question 2");
//
//        //answer template
//        AnswerTemplate at1 = new AnswerTemplate();
//        at1.setAnswerType(AnswerType.CHECKBOX);
////        at1.add(question1);
//
//        AnswerTemplate at2 = new AnswerTemplate();
//        at2.setAnswerType(AnswerType.CHECKBOX);
////        at2.add(question2);
//
////        question1.setAnswerTemplates(Collections.singleton(at1));
////        question2.setAnswerTemplates(Collections.singleton(at2));
//        //section
//        Section section1 = new Section();
//        section1.setSectionNameEnglish("Section 1");
////        section1.setSectionNo(1);
////
////        Section section2 = new Section();
////        section2.setSectionNameEnglish("Section 2");
////        section2.setSectionNo(2);
////        section2.setQuestions(Collections.singleton(question1));
////
////        question1.setSection(section2);
////        Mapping mapping = new Mapping();
////        mapping.setQuestion(question1);
////        mapping.setAnswerTemplate(at1);
////        at1.setMappings(Collections.singleton(mapping));
////        //questionnaire
////        Questionnaire questionnaire = new Questionnaire();
////        questionnaire.setSections(Collections.singleton(section1));
////        section1.setQuestionnaire(questionnaire);
////        questionnaireService.save(questionnaire);
//    }
//
//
//    @Transactional
//    @Test
//    public void test() {
//        Iterable<PublishedQuestionBank> publishedQuestionBank = publishedQuestionBankService.findAll();
//        try {
//            for (PublishedQuestionBank publishedQuestionBank1 : publishedQuestionBank) {
//                if (publishedQuestionBank1.getId() == 84) {
//                    QuestionBank questionBank = publishedQuestionBank1.getQuestionBank();
//                    questionnaireService.saveQuestionnaire(questionBank.getQuestionSet(), publishedQuestionBank1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void findById() {
//        Iterable<AssessmentResponse> assessmentResponse = assessmentResponseRepository.findAll();
//        assessmentResponse.forEach(assessmentResponse1 -> {
//            getAnswerList(assessmentResponse1);
//        });
//    }
//
//
//    private void getAnswerList(AssessmentResponse assessmentResponse) {
//        if (assessmentResponse.getValue().equals("[\"\"]")) {
//            assessmentResponse.setValue(null);
//        }
//        assessmentResponseRepository.save(assessmentResponse);
//    }
//
//    @Test
//    public void getQuestionnaireVersions() {
//        List<Map<String, Object>> data = questionnaireService.getQuestionnaireVersions();
//        log.info("data, {}", data);
//    }
//
//
//    @Test
//    @Transactional
//    public void testFindAssessedQuestionnaireVersions() {
//        String assesseeType = "merchant";
//        LocalDate fromDate = LocalDate.of(2020, 5, 1);
//        LocalDate toDate = LocalDate.of(2020, 5, 10);
//        String regionName = "Dhaka North";
//
//        //Optional
//        Optional<String> userId = Optional.of("130");
////        Optional<String> userId = Optional.empty();
//        Optional<String> dhAccount = Optional.of("01833328255");
////        Optional<String> dhAccount = Optional.empty();
//        double startTime = System.currentTimeMillis();
//        Map<String, Object> qustionnaries = questionnaireService.findAssessedQuestionnaireVersions(assesseeType, fromDate, toDate, regionName, userId, dhAccount);
//        double endTime = System.currentTimeMillis();
//        double executionTime = (endTime - startTime) / 1000;
//        log.info("executionTime:{},questionnaires:{}", executionTime, qustionnaries);
//    }
//
//
//
//}
