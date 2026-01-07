//package com.datasoft.bkash.bkashAml360.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestAgentRepository {
//
//    @Autowired
//    private AgentRepository agentRepository;
//    @Test
//    public void save() {
//        Agent agent =new Agent();
//        agent.setAccountNumber("88017535558666");
//        agent.setAgentCode("5000008");
//        agent.setBusinessArea("Ctg");
//        agent.setOrganizationTin("125500027");
//        agent.setRegionName("Ctg");
//
//        agent.setAgentName("Sohag");
//        agent.setShopName("Ctg Telecom");
//        agent.setDistrictName("Ctg");
//        agent.setThanaName("Ctg Sadar");
//        agent.setAccountStatus(Boolean.TRUE);
//        agent.setStatus(Boolean.TRUE);
//        agent.setOperatorStatus(Boolean.TRUE);
//        agent.setDiscontinueStatus(Boolean.FALSE);
//        agent.setVatRegistrationNo("2005256");
//        agentRepository.save(agent);
//        log.info("Inserted agent id : {} ", agent.getId());
//    }
//
//    @Test
//    public void update(){
//        Agent agent = agentRepository.findById(1).get();
//        agentRepository.save(agent);
//    }
//
//    @Test
//    public void getData(){
//        Iterable<Agent> agentList = agentRepository.findAllByDhMasterAcctNumber("01839597856");
//        log.info("{}", agentList);
//    }
//}
