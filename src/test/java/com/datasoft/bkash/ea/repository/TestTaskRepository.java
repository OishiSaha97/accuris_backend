//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Task;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//
//@SpringBootTest
//@Slf4j
//public class TestTaskRepository {
//
//    @Autowired
//    private Gson gson;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Test
//    public void findAll() {
//        Iterable<Task> tasks = taskRepository.findAll();
//        log.info("All task {}", gson.toJson(tasks));
//    }
//
//    @Test
//    public void findById() {
//        Optional<Task> task = taskRepository.findById(1);
//        log.info("task >> {}", gson.toJson(task.get()));
//    }
//
//    @Test
//    public void save() {
//        Task task=new Task();
//        task.setIsSubTask(Boolean.FALSE);
//        task.setTaskName("User Management");
////        task.setAgTask(task);
//        task=taskRepository.save(task);
//        log.info("Inserted task id : {}", task.getId());
//    }
//
//    @Test
//    public void update() {
//        Task task=new Task();
//        task.setId(1);
//        task.setIsSubTask(Boolean.FALSE);
//        task.setTaskName("User Management");
////        task.setAgTask(task);
//        task=taskRepository.save(task);
//        log.info("updated task id : {}", task.getId());
//    }
//
//
//
//}
