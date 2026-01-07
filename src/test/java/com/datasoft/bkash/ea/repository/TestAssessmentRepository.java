//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.enums.ApproveStatus;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.utils.Utils;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//import java.time.LocalDate;
//import java.util.*;
//
//import static com.datasoft.bkash.bkashAml360.model.specification.AssessmentSpecificationBuilder.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Slf4j
//public class TestAssessmentRepository {
//	@Autowired
//	private AssessmentRepository assessmentRepository;
//	@Autowired
//	private UsersRepository usersRepository;
//
//	@Test
//	@Transactional
//	public void testOldAssessment() {
////		List<Assessment> assessmentList = assessmentRepository.findAll(new Specification<Assessment>() {
////			@Override
////			public Predicate toPredicate(Root<Assessment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
////
////				Date date = new Date(LocalDate.endOfToday().minusDays(0).toEpochDay() * Utils.ONE_DAY_IN_MILLIS);
////
////				return criteriaBuilder.lessThan(root.get(Assessment_.createdAt), date);
//////				return null;
////			}
////		});
//
//		Date endOfToday = new Date(LocalDate.now().plusDays(1).toEpochDay() * Utils.ONE_DAY_IN_MILLIS);
//		Date oldDate = new Date(LocalDate.now().minusDays(5).toEpochDay() * Utils.ONE_DAY_IN_MILLIS);
//
//		String grade = "GOOD";
//		String designation = "CAO";
//		String username = "mrbaker01";
//		String question_version = "2020-AR1-V-0.0.1";
//		Specification<Assessment> specs = after(oldDate)
//				.and(before(endOfToday))
////				.and(hasGrade(grade))
////				.and(withQuestionVersion(question_version))
////				.and(withDesignation(designation))
////				.and(withCaoName(username))
//				.and(isSarStr(true));
//
//		List<Assessment> assessmentList = assessmentRepository.findAll(specs);
//		assert assessmentList.size() == 2;
//		assessmentList.forEach(assessment -> log.info("Assessment: {} [{}]", assessment.getId(), assessment.getCreatedAt()));
//	}
//
//	@Test
//	@Transactional
//	public void testFindByApproveStatus_Pending(){
//		List<Assessment> assessments = assessmentRepository.findByApproveStatusNot(ApproveStatus.PENDING);
//		log.info("assessments:{}",assessments);
//	}
//
//	@Test
//	@Transactional
//	public void testAssessmentDataForDH(){
////		User user = usersRepository.findById(130).get();
//		Optional<String> regionName = Optional.of("Dhaka North");
//		Integer assessmentUserId = 130;
//
//		AssesseeType assesseeType = AssesseeType.MERCHANT;
//		LocalDate fromDate = LocalDate.of(2020, 5, 1);
//		LocalDate toDate = LocalDate.of(2020, 5, 10);
//		Date startDate = Utils.getStartOfDay(fromDate);
//		Date endDate = Utils.getEndOfDay(toDate);
//		List<Assessment> assessments = assessmentRepository.findAllByAssesseeTypeAndCreatedAtBetweenAndAssessedByUser_IdAndUserRegion_Region_RegionNameIgnoreCaseAndApproveStatusNot(assesseeType,startDate,endDate,assessmentUserId,regionName.get(),ApproveStatus.PENDING);
//		log.info("assessments:{}",assessments);
//	}
//
//	////////////   TESTING PURPOSE ONLY
//	@Test
//	@Transactional
//	public void testFindAssessmentsBasedOnRegionNames(){
////		List<String> regionNames = Arrays.asList("Dhaka,Comilla");
//		String regionNames = "Dhaka North";
////		List<Map<String,Object>> assessments = assessmentRepository.findAssessmentsBasedOnRegionName(regionNames);
////		List<Assessment> assessments = assessmentRepository.findAssessmentsBasedOnRegionName(regionNames);
//		Set<Assessment> assessments = assessmentRepository.findAssessmentsBasedOnRegionName(regionNames);
//		log.info("assessments:{}",assessments);
//	}
//
//	@Test
//	@Transactional
//	public void testFindAssessmentsBasedOnRegionIds(){
////		List<String> regionNames = Arrays.asList("Dhaka,Comilla");
//		List<String> ids = Arrays.asList("1","2");
////		String regionIds = "1";
//		String regionIds = ids.toString().replace("[","").replace("]","");
//		log.info("RegionIds:{}",regionIds);
//		Set<Assessment> assessments = assessmentRepository.findAssessmentsBasedOnRegionIds(regionIds);
//		log.info("assessments:{}",assessments);
//	}
//
//	@Test
//	@Transactional
//	public void testFindQuestionnaires(){
//		String assesseeType = "MERCHANT";
//		List<String> regions = Arrays.asList("Dhaka North","Comilla");
//		String regionNames = regions.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",regionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		List<Map<String,Object>> questionnaries = assessmentRepository.finQuestionnaires(assesseeType,fromDate,toDate,regionNames);
//		log.info("Questionnaires:{}",questionnaries);
//	}
//
//
//	@Test
//	@Transactional
//	public void testFindCAOs(){
//		String assesseeType = "MERCHANT";
//		List<String> regions = Arrays.asList("Dhaka North","Comilla");
//		String regionNames = regions.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",regionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		Integer questionnaireId = 7;
//		List<Map<String,Object>> caos = assessmentRepository.findCAOsAndDhAccountIsNull(assesseeType,fromDate,toDate,regionNames,questionnaireId);
//		log.info("caos:{}",caos);
//	}
//
//	/// Find CAOs with DH for AssesseeType: DAO
//	@Test
//	@Transactional
//	public void testFindCAOsWithDhForDAO(){
//		String assesseeType = "DAO";
//		List<String> regionNames = Arrays.asList("Dhaka North","Comilla");
//		String convertedRegionNames = regionNames.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",convertedRegionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		Integer questionnaireId = 6;
//		List<String> dhAccounts = Arrays.asList("01833328255","01708420850");
//		String convertedDhAccounts = dhAccounts.toString().replace("[","").replace("]","");
//		List<Map<String,Object>> caos = assessmentRepository.findCAOsForDaoWithDhAccounts(assesseeType,fromDate,toDate,convertedRegionNames,questionnaireId,convertedDhAccounts);
//		log.info("caos:{}",caos);
//	}
//	/// Find CAOs with DH for AssesseeType: DSO
//	@Test
//	@Transactional
//	public void testFindCAOsWithDhForDso(){
//		String assesseeType = "DSO";
//		List<String> regionNames = Arrays.asList("Dhaka North","Comilla");
//		String convertedRegionNames = regionNames.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",convertedRegionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		Integer questionnaireId = 6;
//		List<String> dhAccounts = Arrays.asList("01833328255","01708420850");
//		String convertedDhAccounts = dhAccounts.toString().replace("[","").replace("]","");
//		List<Map<String,Object>> caos = assessmentRepository.findCAOsForDsoWithDhAccounts(assesseeType,fromDate,toDate,convertedRegionNames,questionnaireId,convertedDhAccounts);
//		log.info("caos:{}",caos);
//	}
//	/// Find CAOs with DH for AssesseeType: AGENT
//	@Test
//	@Transactional
//	public void testFindCAOsWithDhForAgent(){
//		String assesseeType = "AGENT";
//		List<String> regionNames = Arrays.asList("Dhaka North","Comilla");
//		String convertedRegionNames = regionNames.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",convertedRegionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		Integer questionnaireId = 11;
//		List<String> dhAccounts = Arrays.asList("01833328255","01708420850");
//		String convertedDhAccounts = dhAccounts.toString().replace("[","").replace("]","");
//		List<Map<String,Object>> caos = assessmentRepository.findCAOsForAgentWithDhAccounts(assesseeType,fromDate,toDate,convertedRegionNames,questionnaireId,convertedDhAccounts);
//		log.info("caos:{}",caos);
//	}
//	/// Find CAOs with DH for AssesseeType: DH
//	@Test
//	@Transactional
//	public void testFindCAOsWithDhForDh(){
//		String assesseeType = "DH";
//		List<String> regionNames = Arrays.asList("Dhaka North","Comilla");
//		String convertedRegionNames = regionNames.toString().replace("[","").replace("]","");
//		log.info("Region Names:{}",convertedRegionNames);
//		String fromDate ="2020-05-01";
//		String toDate = "2020-05-27";
//		Integer questionnaireId = 6;
//		List<String> dhAccounts = Arrays.asList("01833328255","01708420850");
//		String convertedDhAccounts = dhAccounts.toString().replace("[","").replace("]","");
//		List<Map<String,Object>> caos = assessmentRepository.findCAOsForDhWithDhAccounts(assesseeType,fromDate,toDate,convertedRegionNames,questionnaireId,convertedDhAccounts);
//		log.info("caos:{}",caos);
//	}
//
//	//  Filter CAO Summary Report
//	@Test
//	@Transactional
//	public void testFilterCAOSummaryReport(){
//		Utils.doManualAuthentication("dsashiful","OTKsxAnK");
//		String fromDate = "2020-05-01";
//		String toDate = "2020-05-27";
////		List<String> regionNames = Arrays.asList("Dhaka North", "Comilla");
//		String regionNames="Dhaka North,Comilla";
////		List<String> userIds = Arrays.asList("107","130");
//		String userIds = "107,130";
//		Integer questionnaireId = 7;
////		List<Map<String,Object>> reports = assessmentRepository.testFilterCaoSummaryReport(fromDate,toDate,regionNames,questionnaireId,userIds);
//		List<Assessment> assessments = assessmentRepository.filterCaoSummaryReport(fromDate,toDate,regionNames,questionnaireId,userIds);
//		log.info("reports:{}",assessments);
//	}
//
//
//}
