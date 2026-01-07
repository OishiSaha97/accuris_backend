//package com.datasoft.bkash.bkashAml360.controller;
//
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.enums.UserStatus;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//public class TestUserController {
//    @Autowired
//    private Gson gson;
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    public void findById() throws Exception {
//        String userId = "5";
//        String url = "/user/findById/" + userId;
//        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Receive data from server {} and status is {}", content, status);
//    }
//
//    @Test
//    public void signin() throws Exception {
//        User user=new User();
//        user.setStatus(UserStatus.valueOf("ACTIVE"));
//        user.setActive(Boolean.TRUE);
//        user.setDesignation("Developer");
//        user.setLoginId("shohag");
//        user.setPassword("123");
//        String url = "/user/signin";
//        MvcResult mvcResult = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(gson.toJson(user))
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Receive from server {}", gson.toJson(content));
//        log.info("Status code is {}", status);
//    }
//
//    @Test
//    public void verifyUser() throws Exception {
//        String userId = "1";
//        String url = "/user/findById/" + userId;
//        MvcResult mvcResult = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Receive from server {}", content);
//    }
//
//    @Test
//    public void findByIsCAO() throws Exception {
//        String userId = "1";
//        String url = "/user/findByIsCAO";
//        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Receive from server {}", content);
//    }
//}
