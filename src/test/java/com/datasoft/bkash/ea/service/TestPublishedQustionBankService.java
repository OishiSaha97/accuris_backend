//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestPublishedQustionBankService {
//
//    @Autowired
//    private PublishedQuestionBankRepository publishedQuestionBankRepository;
//    @Autowired
//    private PublishedQuestionBankService publishedQuestionBankService;
//
//    @Test
//    public void testMap() {
//        String s = "{\"tabs\": [{\"id\": \"section-tab-\", \"name\": \"Section \", \"type\": \"section\", \"tabNo\": 1, \"target\": \"section-\", \"isActive\": true}], \"questionnaire\": [{\"id\": \"s-2b2a9853-664b-4465-8fff-848cde12206d\", \"type\": \"section\", \"questions\": [{\"id\": \"q-d920ca9b-d394-4d57-bcec-c725ae331790\", \"type\": \"question\", \"sectionNo\": 1, \"isRequired\": false, \"questionNo\": 1, \"answerTypes\": [{\"type\": \"textbox\", \"children\": [{\"value\": \"wwww\", \"answer\": \"\", \"valueBangla\": \"ww\", \"weightValue\": \"\"}]}], \"hasEvidence\": false, \"evidenceFileName\": \"\", \"previousQuestionNo\": 1, \"questionNameBangla\": \"q2\", \"questionNameEnglish\": \"q1\", \"mappedToSameOrAnotherSection\": false}, {\"id\": \"q-8c032339-7186-4dc7-81d3-3256041a5026\", \"type\": \"question\", \"sectionNo\": 1, \"isRequired\": false, \"questionNo\": 2, \"answerTypes\": [{\"name\": \"\", \"type\": \"radio\", \"answer\": \"\", \"children\": [{\"value\": \"t\", \"mappings\": [{\"type\": \"sectionQuestion\", \"condition\": \"\", \"sectionNo\": 1, \"conditions\": [], \"questionNo\": 1, \"sectionQuestionId\": \"q-56286c53-b532-40bf-ad46-e1f4d0ab150d\", \"sectionQuestionNo\": 3, \"hasConditionalMapping\": false, \"previousSectionQuestionNo\": 3}], \"hasMapping\": true, \"valueBangla\": \"\", \"weightValue\": \"4\"}, {\"value\": \"q\", \"mappings\": [], \"hasMapping\": false, \"valueBangla\": \"q\", \"weightValue\": \"1\"}]}], \"hasEvidence\": false, \"evidenceFileName\": \"\", \"previousQuestionNo\": 2, \"questionNameBangla\": \"q4\", \"questionNameEnglish\": \"q3\", \"mappedToSameOrAnotherSection\": false}, {\"id\": \"q-56286c53-b532-40bf-ad46-e1f4d0ab150d\", \"type\": \"question\", \"sectionNo\": 1, \"isRequired\": false, \"questionNo\": 3, \"answerTypes\": [{\"name\": \"\", \"type\": \"radio\", \"answer\": \"\", \"children\": [{\"value\": \"w\", \"mappings\": [], \"hasMapping\": false, \"valueBangla\": \"f\", \"weightValue\": 0}, {\"value\": \"s\", \"mappings\": [], \"hasMapping\": false, \"valueBangla\": \"d\", \"weightValue\": 0}]}], \"hasEvidence\": false, \"evidenceFileName\": \"\", \"previousQuestionNo\": 3, \"questionNameBangla\": \"q3\", \"questionNameEnglish\": \"q3\", \"mappedToSameOrAnotherSection\": true}], \"sectionNo\": 1, \"previousSectionNo\": 1, \"sectionNameBangla\": \"\", \"sectionNameEnglish\": \"\"}], \"questionnaireTitles\": [{\"titleBangla\": \"\", \"titleEnglish\": \"\"}, {\"titleBangla\": \"\", \"titleEnglish\": \"\"}, {\"titleBangla\": \"\", \"titleEnglish\": \"\"}, {\"titleBangla\": \"\", \"titleEnglish\": \"\"}]}";
//        Map<String, Object> objectMap = new Gson().fromJson(s, Map.class);
//        log.info("{}", objectMap);
//    }
//
//    // Make published Question Unpublished
//    // Use Mockito
//   /* @MockBean
//    private PublishedQuestionBankRepository mockedPublishedQuestionBankRepository;*/
//
//    @Test
//    public void testMakePublishedQuestionsUnpublishedWhichCrossedTheDeactivationDate() {
//        Utils.doManualAuthentication("hisham", "bKash123");
//        publishedQuestionBankService.makePublishedQuestionsUnpublishedWhichCrossedDeactivationDate();
//    }
//
//    @Test
//    public void setStatusTrue() {
//        Utils.doManualAuthentication("hisham", "bKash123");
//        List<Integer> ids = Collections.singletonList(7);
//        Iterable<PublishedQuestionBank> publishedQuestionBanks = publishedQuestionBankRepository.findAllById(ids);
//        publishedQuestionBanks.forEach(publishedQuestionBank -> publishedQuestionBank.setStatus(Boolean.TRUE));
//        publishedQuestionBankRepository.saveAll(publishedQuestionBanks);
//    }
//
//
//// Check date
//    @Test
//    public void testDate() throws ParseException {
//        Date today = new Date();
//
//        // Next Date
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(today);
//        calendar.add(Calendar.DATE, 1);
//        Date nextDay = calendar.getTime();
//
//        /// Previous Day
//        calendar.setTime(today);
//        calendar.add(Calendar.DATE, -1);
//        Date previousDay = calendar.getTime();
//
//        // Take only Date
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        previousDay = formatter.parse(formatter.format(previousDay));
//        log.info("Previous Day:{}",previousDay);
//        today = formatter.parse(formatter.format(today));
//        log.info("Today:{}",today);
//        nextDay = formatter.parse(formatter.format(nextDay));
//        log.info("Next Day:{}",nextDay);
//
//        if(new Date().equals(today)){
//            System.out.println("Today");
//        }
//        if(new Date().equals(previousDay)){
//            System.out.println("Previous Day");
//        }
//        if(new Date().equals(nextDay)){
//            System.out.println("Next Day");
//        }
//    }
//
//}
//
