//package com.datasoft.bkash.bkashAml360.service;
//
//import com.datasoft.bkash.bkashAml360.model.Role;
//import com.datasoft.bkash.bkashAml360.model.Task;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@SpringBootTest
//@Slf4j
//public class TestRoleService {
//
//    @Autowired
//    private Gson gson;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Test
//    public void findById() {
//        Integer userId = 11;
//        Role user = roleService.findById(userId);
//        log.info("User data : {}", user);
//    }
//
//    @Test
//    public void findByStatus() {
//        short status = 0;
//        List<Role> user = roleService.findByStatus(status);
//        log.info("Role status {} and  Role data size : {}", status, user.size());
//    }
//
//    @Test
//    @Transactional
//    public void save() {
////        short status = 1;
////        Role role=new Role();
////        role.setRoleName("KYC Receiver");
////        role.setStatus(status);
////        //role.setCreateBy(1);
////
////        Set<Task> tasks=new HashSet<>();
////        Task task=new Task();
////        task.setId(1);
////        tasks.add(taskService.findById(1).get());
////
//////        role.setTasks(tasks);
////
//////        roleService.save(role);
////        log.info("All {} Role data : {}", status, gson.toJson(role));
//    }
//
//    //updateStatus
//
//
//}
