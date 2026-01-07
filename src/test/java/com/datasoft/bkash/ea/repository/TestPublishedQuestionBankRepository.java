//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.User;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//import java.util.Optional;
//
//@SpringBootTest
//@Slf4j
//public class TestPublishedQuestionBankRepository {
//    @Autowired
//    private PublishedQuestionBankRepository publishedQuestionBankRepository;
//
//
//    @Test
//    public void testFindAll() {
//        Iterable<PublishedQuestionBank> publishedQuestionBankList = publishedQuestionBankRepository.findAll();
//        log.info("{}", publishedQuestionBankList);
//    }
//
//
//    @Test
//    public void testFindById() {
//        Optional<PublishedQuestionBank> publishedQuestionBank = publishedQuestionBankRepository.findById(1);
//        log.info("{}", publishedQuestionBank.get());
//    }
//
//
//    @Test
//    public void testSave() {
//        PublishedQuestionBank publishedQuestionBank = new PublishedQuestionBank();
//        User user = new User();
//        user.setId(1);
//        publishedQuestionBank.setDeactiveAt(new Date());
//        //publishedQuestionBank.setEffectiveDate(new Date());
//        publishedQuestionBank.setStatus(Boolean.TRUE);
//        publishedQuestionBank.setMemoInfo("memo-1");
//        publishedQuestionBank.setPublishedBy(user);
//        publishedQuestionBank = publishedQuestionBankRepository.save(publishedQuestionBank);
//        log.info("Inserted PublishedQuestionBank id : {}", publishedQuestionBank.getId());
//    }
//
//
//    @Test
//    public void testUpdate() {
//        PublishedQuestionBank publishedQuestionBank = new PublishedQuestionBank();
//        publishedQuestionBank.setId(4);
//        User user = new User();
//        user.setId(1);
//        publishedQuestionBank = publishedQuestionBankRepository.save(publishedQuestionBank);
//        log.info("Inserted PublishedQuestionBank id : {}", publishedQuestionBank.getId());
//    }
//
//
//}
