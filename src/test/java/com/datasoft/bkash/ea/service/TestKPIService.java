//package com.datasoft.bkash.bkashAml360.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//
//@SpringBootTest
//@Slf4j
//@RunWith(SpringRunner.class)
//public class TestKPIService {
//
//    @Autowired
//    private KPIService kpiService;
//    @Autowired
//    private KPIRepository kpiRepository;
//
//    @Test
//    public void deleteKpi() {
//        Map<String, Object> map = kpiService.deleteKPIByIdAndUserId(69, 11);
//        log.info("{}", map);
//    }
//
//    @Test
//    public void testKPI() {
//        Integer caoId = 24;
//        Integer regionId = 1;
//        Integer year = 2020;
//        List<Map<String, Object>> kpiIterable = kpiRepository.findByYearAndRegionAndUserMonthly(year, regionId, caoId);
//        log.info("KPI :{}", kpiIterable);
//    }
//
//    @Test
//    public void testKPIService() {
//        Integer caoId = 24;
//        Integer regionId = 1;
//        Integer year = 2020;
//        List<Map<String, Object>> list = kpiService.findByAssesseeAndYearAndRegionAndUser(Optional.empty(), year, regionId, caoId);
//        log.info("list: {}", list);
//    }
//
//    @Test
//    public void testlastYearCAO() {
//        Integer assesseeId = 1;
//        Integer regionId = 1;
//        Integer year = 2021;
//        Boolean status = Boolean.FALSE;
//        List<Map<String, Object>> kpis = kpiRepository.findLastYearCaoBasedOnKPIStatus(assesseeId, year, regionId, status);
//        kpis.forEach(map -> {
//            log.info("id:{}", map.get("id"));
//        });
//    }
//
//    @Test
//    public void testKpiSummary() {
//        /**
//         * SET @year = 2020;
//         * SET @regionId = 4;
//         * SET @assesseeId = 1;
//         * SET @fromMonth = "APRIL";
//         * SET @toMonth = "JULY";
//         */
//
//        Integer assesseeId = 1;
//        Integer regionId = 4;
//        Integer year = 2020;
//        String fromMonth = "APRIL";
//        String toMonth = "JULY";
//        List<Map<String, Object>> data = kpiService.KpiSummary(Optional.empty(), Optional.empty(), year, Optional.of(fromMonth), Optional.of(toMonth));
//        log.info("OnlyFor Year: {}", data);
//
//        List<Map<String, Object>> filterData = kpiService.KpiSummary(Optional.of(assesseeId), Optional.of(regionId), year, Optional.of(fromMonth), Optional.of(toMonth));
//        log.info("FilterData: {}", filterData);
//
//    }
//
//    @Test
//    public void testPlanTopKpiInfo() {
//        String month = "APRIL";
//        Integer year = 2020;
//        Map<String, Object> data = kpiService.findKpiSummaryByRegionAndYearMonth(year, month);
//        log.info("OnlyFor Year: {}", data);
//
//    }
//
//}
