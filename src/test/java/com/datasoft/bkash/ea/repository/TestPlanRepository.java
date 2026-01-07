//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.Assessee;
//import com.datasoft.bkash.bkashAml360.model.User;
//import com.datasoft.bkash.bkashAml360.model.enums.AssessmentType;
//import com.datasoft.bkash.bkashAml360.model.enums.PlanStatus;
//import com.datasoft.bkash.bkashAml360.model.exception.ErrorCodes;
//import com.datasoft.bkash.bkashAml360.model.exception.ResourceNotFoundException;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//@SpringBootTest
//@Slf4j
//public class TestPlanRepository {
//    @Autowired
//    private PlanRepository planRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private AssesseeRepository assesseeRepository;
//    @Autowired
//    private DSORepository dsoRepository;
//
//
//    @Test
//    public void testSave() {
//        Integer assesseeId = 1;
//
//        Plan plan = new Plan();
//        plan.setAssessmentType(AssessmentType.RANDOM);
//        plan.setDate(new Date());
//        plan.setStatus(PlanStatus.PLANNED);
//
//        Assessee assessee = assesseeRepository.findById(assesseeId).orElseThrow(() -> new ResourceNotFoundException(assesseeId.toString(), ErrorCodes.A_0000));
//        plan.setAssessee(assessee);
//
//        User user = usersRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException(assesseeId.toString(), ErrorCodes.A_0000));
//        plan.setCao(user);
//        plan = planRepository.save(plan);
//        log.info("Inserted planning id : {}", plan.getId());
//    }
//
//    @Test
//    public void testUpdate() {
//        Plan plan = planRepository.findById(148).get();
//        plan.setIsPublished(true);
//        plan = planRepository.save(plan);
//        log.info("Updated planning id : {}", plan.getId());
//    }
//
//    @Test
//    public void testDelete(){
//        Integer isDelete = planRepository.deleteByIdAndStatus(13, PlanStatus.PLANNED);
//        log.info("{}", isDelete);
//    }
//
//    @Test
//    public void testNull(){
//        DSO dso = dsoRepository.findById(1).get();
//        log.info("dso:{}", dso.getStatus());
//
//    }
//}
