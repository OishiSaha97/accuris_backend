//package com.datasoft.bkash.bkashAml360.service;
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
//public class TestTaskService {
//
//    @Autowired
//    private Gson json;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Test
//    public void findAll() {
////        ResponseEntity<?> usersList = taskService.findAll();
////        log.info("all task list {}", json.toJson(usersList.getBody()));
//    }
//
//    @Test
//    public void findById() {
//        Optional<Task> task = taskService.findById(1);
//        log.info("single task {}", json.toJson(task));
//    }
//
//    @Test
//    public void findAllSuperTask() {
//        Iterable<Task> tasks = taskService.findAllSuperTask();
//        log.info("all super task {}", json.toJson(tasks));
//    }
//
//    @Test
//    public void save() {
//        Task task = new Task();
//        task.setTaskName("Questionnaire List");
//        task.setIsSubTask(Boolean.TRUE);
//
//        Task task1 = new Task();
//        task1.setId(4);
////        task.setAgTask(task1);
//        task.setUrl("/questionnaire");
//        taskService.save(task);
//        log.info("Inserted task id : {}", task.getId());
//    }
//
//    @Test
//    public void update() {
//        Task task = new Task();
//        Task superTask = new Task();
//        superTask.setId(49);
//        task.setId(109);
//        task.setIsSubTask(Boolean.TRUE);
//        task.setSuperTask(superTask);
//        taskService.update(task);
//        log.info("updated task id : {}", task.getId());
//    }
//
//}
