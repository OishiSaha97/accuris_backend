//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Role;
//import com.datasoft.bkash.bkashAml360.model.Task;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//
//@SpringBootTest
//@Slf4j
//public class TestRoleRepository {
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    public void findAll() {
//        Iterable<Role> usersList = roleRepository.findAll();
//        log.info("All role {}", usersList);
//    }
//
//    @Test
//    public void findById() {
//        Optional<Role> role = roleRepository.findById(1);
//        log.info("Single Role data : {}", role);
//    }
//
//    @Test
//    public void save() {
//        short status = 1;
//        Role role=new Role();
//        role.setRoleName("FCA Admin");
//        role.setStatus(status);
//        //role.setCreateBy(role);
//
//        Set<Task> tasks=new HashSet<>();
//        Task task=new Task();
//        task.setIsSubTask(Boolean.FALSE);
//        task.setTaskName("User Creation");
//        tasks.add(task);
//
////        role.setTasks(tasks);
//        role=roleRepository.save(role);
//        log.info("Inserted role id : {}", role.getId());
//    }
//
//
//
//}
