//package com.datasoft.bkash.bkashAml360.repository;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@SpringBootTest
//public class TestAgentTransactionRepository {
//
//    @Autowired
//    private AgentRepository agentRepository;
//
//    @Test
//   public void findLast60DayAmountReceived(){
////       List<AgentTransaction> agentTransactionList = agentRepository.findByAccountNumber("01925252520").get().getAgentTransactions().stream().filter(agentTransaction -> agentTransaction.getB2bLastReceivedAmount()!=null).collect(Collectors.toList());
//       List<BigDecimal> agentTransactionList = agentRepository.findByAccountNumber("01925252520").get().getAgentTransactions().stream().filter(agentTransaction -> agentTransaction.getB2bLastReceivedAmount()!=null).map(agentTransaction -> agentTransaction.getB2bLastReceivedAmount()).collect(Collectors.toList());
//        /*Collections.sort(agentTransactionList);
//        BigDecimal sum = new BigDecimal(0);
//        if(agentTransactionList.size()<=60){
//         for (BigDecimal temp:agentTransactionList){
//           sum = sum.add(temp);
//         }
//        }*/
//        log.info("count:{} ",agentTransactionList.size());
//    }
//
//    @Test
//    public void findSumOfLast60DaysB2bLastDayAmountSentDate() throws ParseException {
//        Agent agent = agentRepository.findByAccountNumber("01925252520").get();
///*
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
////        String startDate = simpleDateFormat.format("2020-01-15 21:51:04");
//        Date startDate = simpleDateFormat.parse("2020-01-25 18:20:26");
//        Date endDate = simpleDateFormat.parse("2020-01-15 21:51:04");
//        List<AgentTransaction> agentTransactionList = agentTransactionRepository.findByB2bLastDayAmountSentBetween(startDate,endDate);
//        log.info("agent:{}",agent.getAgentName());
//        log.info("size:{}",agentTransactionList.size());*/
//
//        Calendar calendar = Calendar.getInstance();
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        calendar.setTime(simpleDateFormat.parse("2020-01-25 18:20:26"));
//        calendar.add(Calendar.DATE,-60);
//        Date endDate = calendar.getTime();
//        log.info("endDate:{}",endDate);
//    }
//}
