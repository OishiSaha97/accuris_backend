//package com.datasoft.bkash.bkashAml360.repository;
//
//import com.datasoft.bkash.bkashAml360.model.questionnaire.*;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class TestQuestionnaireRepository {
//
//	@Autowired
//	private QuestionnaireRepository questionnaireRepository;
//	@Autowired
//	private SectionRepository sectionRepository;
//
//	@Test
////	@Transactional
//	// TODO: 2/13/20 Parent Entity id is not saved in Child entity DB
//	public void save() {
//
//		Questionnaire questionnaire = new Questionnaire();
//
//		Section sectionA = new Section();
//		log.info("-------------------- Section A -----------------------");
//		sectionA.setHeader("Section A");
//		sectionA.setMaxScore(10);
//
//		// QUESTION 1 BEGIN
//		Question question = new Question();
//		question.setQuestionNo(1);
//		question.setHasEvidence(true);
//		question.setQuestionNameBangla("Question 1 in Bengali");
//		question.setQuestionNameEnglish("Question 1 in English");
//		question.setIsRequired(true);
//		AnswerTemplate answerTemplate = new AnswerTemplate();
//		answerTemplate.setAnswerType(AnswerType.RADIO);
//		QuestionOptions option1 = new QuestionOptions();
//		option1.setValue("Y");
//		option1.setWeightage(4);
//		QuestionOptions option2 = new QuestionOptions();
//		option2.setValue("N");
//		option2.setWeightage(10);
//		answerTemplate.addOptions(option1, option2);
//		question.setAnswerTemplate(answerTemplate);
//		log.info("Question 1 -> {}", question);
//		// QUESTION 1 END
//		// QUESTION 2 BEGIN
//		Question question2 = new Question();
//		question2.setQuestionNo(2);
//		question2.setHasEvidence(true);
//		question2.setQuestionNameBangla("Question 2 in Bengali");
//		question2.setQuestionNameEnglish("Question 2 in English");
//		question2.setIsRequired(true);
//		AnswerTemplate answerTemplate2 = new AnswerTemplate();
//		answerTemplate2.setAnswerType(AnswerType.CHECKBOX);
//		QuestionOptions option3 = new QuestionOptions();
//		option3.setValue("1");
//		option3.setWeightage(0);
//		QuestionOptions option4 = new QuestionOptions();
//		option4.setValue("2");
//		option4.setWeightage(10);
//		answerTemplate2.addOptions(option3, option4);
//		question2.setAnswerTemplate(answerTemplate2);
//		log.info("Question 2 -> {}", question2);
//		// QUESTION 2 END
//		sectionA.addQuestions(question, question2);
//		log.info("section A -> {}", sectionA);
//
//		Section sectionB = new Section();
//		log.info("-------------------- Section B -----------------------");
//		sectionB.setHeader("Section B");
//		sectionB.setMaxScore(10);
//
//		// QUESTION 1 BEGIN
//		Question questionB1 = new Question();
//		questionB1.setQuestionNo(1);
//		questionB1.setHasEvidence(true);
//		questionB1.setQuestionNameBangla("Question 1 in Bengali");
//		questionB1.setQuestionNameEnglish("Question 1 in English");
//		questionB1.setIsRequired(true);
//		AnswerTemplate answerTemplateB1 = new AnswerTemplate();
//		answerTemplateB1.setAnswerType(AnswerType.RADIO);
//		QuestionOptions optionB1ai = new QuestionOptions();
//		optionB1ai.setValue("Y");
//		optionB1ai.setWeightage(4);
//		QuestionOptions optionB1aii = new QuestionOptions();
//		optionB1aii.setValue("N");
//		optionB1aii.setWeightage(10);
//		answerTemplateB1.addOptions(optionB1ai, optionB1aii);
//		questionB1.setAnswerTemplate(answerTemplateB1);
//		log.info("Question 1 -> {}", questionB1);
//		// QUESTION 1 END
//		// QUESTION 2 BEGIN
//		Question questionB2 = new Question();
//		questionB2.setQuestionNo(2);
//		questionB2.setHasEvidence(true);
//		questionB2.setQuestionNameBangla("Question 2 in Bengali");
//		questionB2.setQuestionNameEnglish("Question 2 in English");
//		questionB2.setIsRequired(true);
//		AnswerTemplate answerTemplateB2 = new AnswerTemplate();
//		answerTemplateB2.setAnswerType(AnswerType.CHECKBOX);
//		QuestionOptions optionB2a = new QuestionOptions();
//		optionB2a.setValue("1");
//		optionB2a.setWeightage(0);
//		QuestionOptions optionB2b = new QuestionOptions();
//		optionB2b.setValue("2");
//		optionB2b.setWeightage(10);
//		answerTemplateB2.addOptions(optionB2a, optionB2b);
//		questionB2.setAnswerTemplate(answerTemplateB2);
//		log.info("Question 1 -> {}", questionB1);
//		// QUESTION 2 END
//		sectionB.addQuestions(questionB1, questionB2);
//		log.info("section B -> {}", sectionB);
//		questionnaire.addSections(sectionA, sectionB);
//        questionnaireRepository.save(questionnaire);
//        log.info("questionnaire saved as {}", questionnaire);
////        log.info("section saved as {}", questionnaire.getSections());
////        questionnaire.getSections().stream().forEach(section -> {
////	        log.info("question saved as {} & parent questionnaire is {}", section.getQuestions(), section.getQuestionnaire());
////	        section.getQuestions().forEach(question1 -> {
////		        log.info("Question of section {} is: {}", section.getId(), question1.getAnswerTemplate());
////	        });
////        });
//		log.info("Complete");
//		Questionnaire newQ = questionnaireRepository.findById(questionnaire.getId()).orElse(new Questionnaire());
//		List<Section> sections = newQ.getSections();
//		List<Section> sectionsFromSR = sectionRepository.findAllByQuestionnaire(newQ);
//		log.info("Fetched Sections from questionnaire: {}", sections);
//		log.info("Fetched Sections: {}", sectionsFromSR);
//
//	}
//
//	@Test
//	public void findById() {
//		String id = "b840ebb8-756c-43ac-a49b-ed604bc9c756";
//		Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
//		log.info("{}", questionnaire.get());
//	}
//
//}
//
