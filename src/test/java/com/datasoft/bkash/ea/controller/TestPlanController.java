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
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//public class TestPlanController {
//    @Autowired
//    private Gson gson;
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void save() throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        map.put("caoId", 1);
//        map.put("agentId", 1);
//        map.put("assesseeId", 1);
//        map.put("date", "2020-01-18");
//        map.put("status", Boolean.TRUE);
//
//        String url = "/plan/agent/save";
//        MvcResult mvcResult = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(gson.toJson(map))
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Receive from server {}", gson.toJson(content));
//        log.info("Status code is {}", status);
//    }
//}
