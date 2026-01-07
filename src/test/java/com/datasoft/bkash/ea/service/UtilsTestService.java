//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import com.datasoft.bkash.bkashAml360.repository.UsersRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.StringUtils;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class UtilsTestService {
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Test
//    public void setPassword(){
//        Integer userId = 107;
//        User user = usersRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException(userId.toString(), ErrorCodes.U_0000));
//        String newPass = "bkash123";
//        String encryptedPass = encryptPassword(newPass);
//        user.setPassword(encryptedPass);
//        user = usersRepository.save(user);
//        log.info("user:{}",user);
//        Assert.assertTrue(passwordEncoder.matches(newPass, user.getPassword()));
//    }
//
//    @Test
//    public void setPasswordForAllUsers(){
//        List<User> users = usersRepository.findAllByPassword("");
//        log.info("Users:{}",users);
//        String newPass = "bkash123";
//        String encryptedPass = encryptPassword(newPass);
//        users.forEach(user -> {
//            user.setPassword(encryptedPass);
//            user = usersRepository.save(user);
//            log.info("user:{}",user);
//        });
//    }
//
//    public String encryptPassword(String newPassword) {
//        String pass = passwordEncoder.encode(newPassword);
//        log.info(pass);
//        return pass;
//    }
//
//    @Test
//    @Transactional
//    public void testMatchPassword(){
//        String loginId = "hisham";
//        String guessPassword = "bKash123";
//        User user = usersRepository.findByLoginId(loginId).orElseThrow(() -> new ResourceNotFoundException(loginId,ErrorCodes.U_0000));
//        if(StringUtils.isEmpty(user.getPassword())){
//            throw new RuntimeException("Password does not exist for User having loginId: "+loginId);
//        }
//        Assert.assertTrue(passwordEncoder.matches(guessPassword,user.getPassword()));
//    }
//}
