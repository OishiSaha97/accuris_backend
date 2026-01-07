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
//import javax.transaction.Transactional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Slf4j
//public class TestRegionController {
//    @Autowired
//    private Gson gson;
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    public void testFindAll() {
//
//    }
//
//
//    @Test
//    public void testFindById() throws Exception {
//        String regionId = "4";
//        String url = "/region/info?id=" + regionId;
//        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        String content = mvcResult.getResponse().getContentAsString();
//        log.info("Received from server {}", content);
//    }
//
//
//    @Test
//    public void testSave() {
//
//    }
//
//
//    public void testUpdate() {
//
//    }
//}
