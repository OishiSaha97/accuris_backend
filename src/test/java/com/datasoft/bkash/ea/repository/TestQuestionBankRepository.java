//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Assessee;
//import com.datasoft.bkash.bkashAml360.model.User;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//@Slf4j
//public class TestQuestionBankRepository {
//    @Autowired
//    private QuestionBankRepository questionBankRepository;
//
//    @Test
//    public void testFindAll() {
//        Iterable<QuestionBank> questionBankList = questionBankRepository.findAll();
//        log.info("{}", questionBankList);
//    }
//
//    @Test
//    public void testFindById() {
//        Optional<QuestionBank> questionBank = questionBankRepository.findById(1);
//        log.info("{}", questionBank.get());
//    }
//
//    @Test
//    public void testSave() {
//        QuestionBank questionBank = new QuestionBank();
//        questionBank.setQuestionTitle("Shapla-Dhaka");
//        questionBank.setQuestionType("random");
//        Assessee assessee = new Assessee();
//        assessee.setId(1);
//        questionBank.setAssessee(assessee);
////        questionBank.setQuestionSet("{}");
//        User user = new User();
//        user.setId(1);
//        questionBank.setCreateBy(user);
//        questionBank.setQuestionVersion("v1");
//        questionBank = questionBankRepository.save(questionBank);
//        log.info("Inserted QuestionBank id : {}", questionBank.getId());
//    }
//
//    @Test
//    public void testUpdate() {
//        QuestionBank questionBank = new QuestionBank();
//        questionBank.setId(4);
//        questionBank = questionBankRepository.save(questionBank);
//        log.info("Inserted QuestionBank id : {}", questionBank.getId());
//    }
//}
