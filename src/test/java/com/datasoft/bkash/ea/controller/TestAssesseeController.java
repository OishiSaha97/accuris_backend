//package com.datasoft.bkash.bkashAml360.controller;
//
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
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//public class TestAssesseeController {
//    @Autowired
//    private Gson gson;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testFindById() throws Exception {
//        String assesseId = "8";
//        String url = "/assesse/info?id=" + assesseId;
//        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Received from server {}", content);
//    }
//
//}
