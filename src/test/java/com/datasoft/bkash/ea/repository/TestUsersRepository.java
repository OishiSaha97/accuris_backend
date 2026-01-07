//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Role;
//import com.datasoft.bkash.bkashAml360.model.Task;
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.enums.UserStatus;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//
//@SpringBootTest
//@Slf4j
//public class TestUsersRepository {
//    @Autowired
//    private UsersRepository usersRepository;
//
//    @Test
//    public void save() {
//        Task task = new Task();
//        task.setTaskName("User Creation");
//        task.setUrl("/user/add");
//        task.setCreatedAt(new Date());
//        Set<Task> taskList = new HashSet<>();
//        taskList.add(task);
//
//        Role role = new Role();
//        role.setRoleName("Role_Admin");
//        role.setStatus((short) 1);
////        role.setTasks(taskList);
//        role.setCreatedAt(new Date());
//        Set<Role> roleList = new HashSet<>();
//        roleList.add(role);
//
//        User user = new User();
//        user.setActive(Boolean.TRUE);
//        user.setDesignation("Software Engineer");
//        user.setPhoneNumber("01916170061");
//        user.setRoles(roleList);
//        user.setIsCAO(Boolean.TRUE);
//        user.setStatus(UserStatus.valueOf("ACTIVE"));
//        User user2 = new User();
//        user2.setId(1);
////        user.setCreatedAt(new Date());
//
//        user = usersRepository.save(user);
//        log.info("Inserted user id : {}", user.getId());
//    }
//
//    @Test
//    public void update() {
//        User user = usersRepository.findById(1).get();
//        user.setLoginId("masum6");
//        user.setUserName("masum6");
//        user.setPassword("aaaaa6");
//        usersRepository.save(user);
//    }
//
//
//    @Test
//    @Transactional
//    public void findAll() {
//        Iterable<User> usersList = usersRepository.findAll();
//        log.info("{}", usersList);
//    }
//
//    @Test
//    public void findAllPage() {
//        PageRequest pageRequest = PageRequest.of(0, 1);
//        Optional<User> usersList = usersRepository.findByUserName("qa_user");
//        log.info("{}", usersList);
//    }
//
//    @Test
//    public void findByName() {
//        Optional<User> user = usersRepository.findByUserNameAndPassword("qa_user", "aaaaa");
//        log.info("{}", user);
//    }
//
//    @Test
//    @Transactional
//    public void findByLoginId() {
//        Optional<User> user = usersRepository.findByLoginIdAndPassword("a", "a");
//        log.info("{}", user.get());
//    }
//
//    @Test
//    @Transactional
//    public void findByIsCAO() {
//        Iterable<User> user = usersRepository.findByIsCAO(Boolean.TRUE);
//        log.info("{}", user);
//    }
//
//    @Test
//    @Transactional
//    public void testfindAllUserByUserTypeAndRegionId() {
////        Iterable<User> usersList = usersRepository.findAllByIsCAOAndRegionId(Boolean.TRUE,7);
//        Iterable<User> usersList = null;
//        log.info("number of CAO: {}", usersList.spliterator().getExactSizeIfKnown());
//    }
//
//}
