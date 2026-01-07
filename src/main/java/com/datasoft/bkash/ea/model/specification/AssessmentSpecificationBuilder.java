//package com.datasoft.bkash.bkashAml360.model.specification;
//
//import com.datasoft.bkash.bkashAml360.model.*;
//import com.datasoft.bkash.bkashAml360.model.enums.ApproveStatus;
//import com.datasoft.bkash.bkashAml360.model.enums.AssesseeType;
//import com.datasoft.bkash.bkashAml360.model.enums.AssessmentType;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.Questionnaire;
//import com.datasoft.bkash.bkashAml360.model.questionnaire.Questionnaire_;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.Path;
//import javax.persistence.criteria.Predicate;
//import java.util.Date;
//import java.util.List;
//
//public class AssessmentSpecificationBuilder {
//
//
//    public static Specification<Assessment> after(Date fromDate) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(Assessment_.assessmentStartDate), fromDate);
//    }
//
//    public static Specification<Assessment> inclusiveAfter(Date fromDate) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Assessment_.assessmentStartDate), fromDate);
//    }
//
//    public static Specification<Assessment> before(Date toDate) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Assessment_.assessmentStartDate), toDate);
//    }
//
//    public static Specification<Assessment> isAssessmentType(AssessmentType assessmentType) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.assessmentType), assessmentType);
//    }
//
//    public static Specification<Assessment> isApprovedStatus(ApproveStatus approveStatus) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.approveStatus), approveStatus);
//    }
//
//    public static Specification<Assessment> isAssesseeType(AssesseeType assesseeType) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.assesseeType), assesseeType);
//    }
//
//    public static Specification<Assessment> hasAssesseeTypeList(List<AssesseeType> assesseeNameList) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<AssesseeType> assesseeTypePath = root.get(Assessment_.assesseeType);
//            return assesseeTypePath.in(assesseeNameList);
//        };
//    }
//
//    public static Specification<Assessment> withregionList(List<Region> regionList) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<UserRegion> userRegionPath = root.get(Assessment_.userRegion);
//            Path<Region> regionPath = userRegionPath.get(UserRegion_.region);
//            return regionPath.in(regionList);
//        };
//    }
//
//    //todo: Check properly
//    public static Specification<Assessment> hasMultipleRegions(List<String> regionNames) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<UserRegion> userRegionPath = root.get(Assessment_.userRegion);
//            Path<Region> regionPath = userRegionPath.get(UserRegion_.region);
//            Path<String> assessmentRegionNames = regionPath.get(Region_.regionName);
//            return assessmentRegionNames.in(regionNames);
//        };
//    }
//
//    public static Specification<Assessment> hasRegion(String regionName) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<UserRegion> userRegionPath = root.get(Assessment_.userRegion);
//            Path<Region> regionPath = userRegionPath.get(UserRegion_.region);
//            Path<String> assessedRegionName = regionPath.get(Region_.regionName);
//            return criteriaBuilder.equal(assessedRegionName, regionName);
//        };
//    }
//
//    public static Specification<Assessment> isEdd(Boolean flag) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> flag ? criteriaBuilder.isEmpty(root.get(Assessment_.controlReport)) : criteriaBuilder.isNotEmpty(root.get(Assessment_.controlReport));
//    }
//
//    public static Specification<Assessment> hasEddFlag(Boolean flag) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.ASSESSMENT_TYPE), AssessmentType.EDD);
//    }
//    public static Specification<Assessment> isImr(Boolean flag) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.isIMR), flag);
//    }
//
//    public static Specification<Assessment> isSarStr(Boolean flag) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.isSarStr), flag);
//    }
//
//    public static Specification<Assessment> hasEvidence(Boolean flag) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.hasEvidence), flag);
//    }
//
//    public static Specification<Assessment> hasGrade(String grading) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.grade), grading);
//    }
//
//    public static Specification<Assessment> withQuestionVersion(String questionVersion) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<Questionnaire> questionnaire = root.get(Assessment_.questionnaire);
//            Path<String> version = questionnaire.get(Questionnaire_.questionVersion);
//            return criteriaBuilder.equal(version, questionVersion);
//        };
//    }
//
//    public static Specification<Assessment> withCaoName(String name) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.assessedByUser);
//            Path<String> caoName = user.get(User_.userName);
//            return criteriaBuilder.equal(caoName, name);
//        };
//    }
//
//    public static Specification<Assessment> withCaoNameList(List<String> caoNames) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.assessedByUser);
//            Path<String> caoName = user.get(User_.userName);
//            return caoName.in(caoNames);
////			return criteriaBuilder.equal(caoName, name);
//        };
//    }
//
//    public static Specification<Assessment> withUserId(Integer userId) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.ASSESSED_BY_USER);
//            Path<Integer> id = user.get(User_.id);
//            return criteriaBuilder.equal(id, userId);
//        };
//    }
//
//    //todo: Check properly
//    public static Specification<Assessment> hasMultipleUserIds(List<Integer> userIds) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.ASSESSED_BY_USER);
//            Path<Integer> assessmentUserIds = user.get(User_.id);
//            return assessmentUserIds.in(userIds);
//        };
//    }
//
//    public static Specification<Assessment> withDesignation(String designation) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.assessedByUser);
//            Path<String> caoDesignation = user.get(User_.designation);
//            return criteriaBuilder.equal(caoDesignation, designation);
//        };
//    }
//
//    public static Specification<Assessment> withAssessedByUserIn(List<User> users) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<User> user = root.get(Assessment_.assessedByUser);
//            return user.in(users);
//        };
//    }
//
//    public static Specification<Assessment> hasTotalScoreBetween(Integer min, Integer max) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<Integer> totalScore = root.get(Assessment_.totalScore);
//            return criteriaBuilder.between(totalScore, min, max);
//        };
//    }
//
//    public static Specification<Assessment> withDhAccount(String dhAccount) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Path<DH> dh = root.get(Assessment_.DH);
//            Path<String> dhAccountNumber = dh.get(DH_.accountNumber);
//            return criteriaBuilder.equal(dhAccountNumber, dhAccount);
//        };
//    }
//
//    public static Specification<Assessment> hasDhAccount(AssesseeType assesseeType, String dhAccount) {
//
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Predicate predicate = null;
//            if (assesseeType.equals(AssesseeType.DH)) {
//                Path<DH> dh = root.get(Assessment_.DH);
//                Path<String> dhAccountNumber = dh.get(DH_.accountNumber);
//                predicate = criteriaBuilder.equal(dhAccountNumber, dhAccount);
//            } else if (assesseeType.equals(AssesseeType.DSO)) {
//                Path<DSO> dsoPath = root.get(Assessment_.DSO);
//                Path<DH> dhPath = dsoPath.get(DSO_.DH);
//                Path<String> dhAccountNumber = dhPath.get(DH_.accountNumber);
//                predicate = criteriaBuilder.equal(dhAccountNumber, dhAccount);
//            } else if (assesseeType.equals(AssesseeType.DAO)) {
//                Path<DAO> daoPath = root.get(Assessment_.DAO);
//                Path<DH> dhPath = daoPath.get(DAO_.DH);
//                Path<String> dhAccountNumber = dhPath.get(DH_.accountNumber);
//                predicate = criteriaBuilder.equal(dhAccountNumber, dhAccount);
//            } else if (assesseeType.equals(AssesseeType.AGENT)) {
//                Path<Agent> agentPath = root.get(Assessment_.AGENT);
//                Path<String> dhAccountNumber = agentPath.get(Agent_.DH_MASTER_ACCT_NUMBER);
//                predicate = criteriaBuilder.equal(dhAccountNumber, dhAccount);
//            }
//            return predicate;
//        };
//    }
//
//    // todo: Check properly
//    public static Specification<Assessment> hasMultipleDhAccounts(AssesseeType assesseeType, List<String> dhAccounts) {
//
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Predicate predicate = null;
//            if (assesseeType.equals(AssesseeType.DH)) {
//                Path<DH> dh = root.get(Assessment_.DH);
//                Path<String> assessmentDhAccounts = dh.get(DH_.accountNumber);
//                predicate = assessmentDhAccounts.in(dhAccounts);
//            } else if (assesseeType.equals(AssesseeType.DSO)) {
//                Path<DSO> dsoPath = root.get(Assessment_.DSO);
//                Path<DH> dhPath = dsoPath.get(DSO_.DH);
//                Path<String> assessmentDhAccounts = dhPath.get(DH_.accountNumber);
//                predicate = assessmentDhAccounts.in(dhAccounts);
//            } else if (assesseeType.equals(AssesseeType.DAO)) {
//                Path<DAO> daoPath = root.get(Assessment_.DAO);
//                Path<DH> dhPath = daoPath.get(DAO_.DH);
//                Path<String> assessmentDhAccounts = dhPath.get(DH_.accountNumber);
//                predicate = assessmentDhAccounts.in(dhAccounts);
//            } else if (assesseeType.equals(AssesseeType.AGENT)) {
//                Path<Agent> agentPath = root.get(Assessment_.AGENT);
//                Path<String> assessmentDhAccounts = agentPath.get(Agent_.DH_MASTER_ACCT_NUMBER);
//                predicate = assessmentDhAccounts.in(dhAccounts);
//            }
//            return predicate;
//        };
//    }
//
//    public static Specification<Assessment> scoreGreaterThan(Integer minScore) {
//        return (Specification<Assessment>) (root, query, critriaBuilder) -> critriaBuilder.greaterThanOrEqualTo(root.get(Assessment_.totalScore), minScore);
//    }
//
//    public static Specification<Assessment> scoreLessThan(Integer maxScore) {
//        return (Specification<Assessment>) (root, query, critriaBuilder) -> critriaBuilder.lessThanOrEqualTo(root.get(Assessment_.totalScore), maxScore);
//    }
//
//    public static Specification<Assessment> hasAccountNumber(String accountNumber) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.accountNumber), accountNumber);
//    }
//
//    public static Specification<Assessment> hasAssessmentUniqueIdentifier(String assessmentUniqueIdentifier) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Assessment_.assessmentUniqueIdentifier), assessmentUniqueIdentifier);
//    }
////	public static Specification<Assessment> fromRegion(String regionName) {
////		return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
////			Path<User> user = root.get(Assessment_.assessedByUser);
////			Join<UserRegion, User> o = root.join(User_.id);
////			Path<UserRegion> userRegion = root.get(User_.)
////			Path<String> version = questionnaire.get(Questionnaire_.questionVersion);
////			return criteriaBuilder.equal(version, questionVersion);
////		};
////	}
//
//    public static Specification<Assessment> withoutApproveStatusPending() {
//        return (Specification<Assessment>) (root, query, critriaBuilder) -> critriaBuilder.notEqual(root.get(Assessment_.approveStatus), ApproveStatus.PENDING);
//    }
//
//    public static Specification<Assessment> hasAssesseeName(AssesseeType assesseeType, String assesseeName) {
//        return (Specification<Assessment>) (root, query, criteriaBuilder) -> {
//            Predicate predicate = null;
//            if (assesseeType.equals(AssesseeType.MERCHANT)) {
//                Path<Merchant> merchantPath = root.get(Assessment_.merchant);
//                Path<String> merchantName = merchantPath.get(Merchant_.merchantName);
//                predicate = criteriaBuilder.like(merchantName, assesseeName);
//            } else if (assesseeType.equals(AssesseeType.DH)) {
//                Path<DH> dhPath = root.get(Assessment_.dh);
//                Path<String> dhName = dhPath.get(DH_.dhName);
//                predicate = criteriaBuilder.like(dhName, assesseeName);
//            } else if (assesseeType.equals(AssesseeType.DAO)) {
//                Path<DAO> daoPath = root.get(Assessment_.dao);
//                Path<String> daoName = daoPath.get(DAO_.daoName);
//                predicate = criteriaBuilder.like(daoName, assesseeName);
//            } else if (assesseeType.equals(AssesseeType.DSO)) {
//                Path<DSO> dsoPath = root.get(Assessment_.dso);
//                Path<String> dsoName = dsoPath.get(DSO_.dsoName);
//                predicate = criteriaBuilder.like(dsoName, assesseeName);
//            } else if (assesseeType.equals(AssesseeType.AGENT)) {
//                Path<Agent> agentPath = root.get(Assessment_.agent);
//                Path<String> agentName = agentPath.get(Agent_.agentName);
//                predicate = criteriaBuilder.like(agentName, assesseeName);
//            }
//            return predicate;
//        };
//    }
//
//
//}
