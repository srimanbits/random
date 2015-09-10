package com.ggk.hrms.review.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.dao.ReviewActionLogDAO;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.service.ProjectService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewCompetencyVO;
import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewHeaderVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;
import com.ggk.hrms.review.ui.vo.ReviewSummaryVO;

@Service("reviewAuditService")
public class ReviewAuditServiceImpl implements ReviewAuditService {

	Logger log = Logger.getLogger(ReviewAuditServiceImpl.class);
	@Resource
	private ReviewActionLogDAO reviewActionLogDAO;

	@Resource
	private ProjectService projectService;
	
	@Resource 
	private RatingService ratingService;
	
	@Resource 
	private ProficiencyLevelService proficiencyLevelService;


	private Map<Integer, String> projectDropDown;
	private Map<Integer, String> ratingDropDown;
	private Map<Integer, String> proficiencyLevelDropDown;

	private void createProjectDropDown() {
		projectDropDown = projectService.getProjectsDropDown();
	}
	private void createRatingDropDown() {
		ratingDropDown = ratingService.getRatingsDropDown();
	}
	
	private void createProficiencylevelDropDown(){
		proficiencyLevelDropDown=proficiencyLevelService.getProficiencyLevelDropDown();
		
	}

	@Override
	public List<ReviewActionLog> getReviewActionLog(Class clazz,
			Object initialObject, Object updatedObject,
			ReviewFormInfoVO reviewFormInfoVO) {

		List<ReviewActionLog> reviewActionLogs = new ArrayList<ReviewActionLog>();

		String reviewFeildGroupName = null;
		ReviewFeildGroupType reviewFeildGroupType = null;
		Class mainClass = clazz;

		final Map<ReviewFeildGroupType, Map<String, String>> auditFeilds = getAuditFeilds();

		final HashSet<Class<?>> WRAPPER_TYPES = getWrapperTypes();

		if (initialObject == null && updatedObject == null) {

		}
		if (initialObject == null && updatedObject != null) {

			Object updatedValue = null;
			reviewFeildGroupName = getReviewFeildGroupName(updatedObject);
			reviewFeildGroupType = getReviewFiledGroupType(updatedObject);

			ReviewActionLog reviewActionLog = createReviewActionLog(
					reviewFormInfoVO, "ADD", reviewFeildGroupType,
					reviewFeildGroupName, null, null, null, null);
			reviewActionLogs.add(reviewActionLog);

			/*
			 * System.out.println(reviewFeildGroupType + " : " +
			 * reviewFeildGroupName + ": is Added");
			 */

			Method[] methods = clazz.getDeclaredMethods();
			Map<String, String> entityAuditFeilds = auditFeilds
					.get(reviewFeildGroupType);

			Field[] fields = clazz.getDeclaredFields();
			for (Field feild : fields) {

				if (entityAuditFeilds.containsKey(feild.getName())
						&& (!(feild.getName().equals("objectiveName")
								|| feild.getName().equals("isApproved") || feild
								.getName().equals("goalComment")))) {

					try {
						feild.setAccessible(true);
					} catch (SecurityException e) {
						// System.out.println("SecurityException");
						continue;

					}
					try {
						if (feild.getName().equals("projectIds")) {// special
							updatedValue = feild.get(updatedObject);
							if (getActionLogForProjects(
									reviewFeildGroupName,
									null,
									(List<Integer>) updatedValue,
									reviewFormInfoVO) != null) {
								reviewActionLogs
										.add(getActionLogForProjects(
												reviewFeildGroupName,
												 null,
												(List<Integer>) updatedValue,
												reviewFormInfoVO));
							}
							continue;
						}
						else {
							updatedValue = feild.get(updatedObject);
						}
					} catch (IllegalArgumentException e) {
						// System.out.println("Illegal Argument exception.");
					} catch (IllegalAccessException e) {
						// System.out.println("Illegal Access exception.");

					}
					if (updatedValue != null) {

						if (feild.getName().equals("targetCompletionDate")
								|| feild.getName().equals("achievmentDate")) {
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"dd-MM-yyyy");
							updatedValue = dateFormat
									.format((java.util.Date) updatedValue);

						}
						String fieldName = entityAuditFeilds.get(feild
								.getName());

						ReviewActionLog reviewActionLog2 = createReviewActionLog(
								reviewFormInfoVO, "ADD", reviewFeildGroupType,
								reviewFeildGroupName, fieldName,
								updatedValue.toString(),
								updatedValue.toString(), null);
						reviewActionLogs.add(reviewActionLog2);
					}

				}

			}

		}

		if (initialObject != null && updatedObject == null) {

			reviewFeildGroupName = getReviewFeildGroupName(initialObject);
			reviewFeildGroupType = getReviewFiledGroupType(initialObject);
			ReviewActionLog reviewActionLog = createReviewActionLog(
					reviewFormInfoVO, "DELETE", reviewFeildGroupType,
					reviewFeildGroupName, null, null, null, null);
			reviewActionLogs.add(reviewActionLog);
		}
		try {
			if (initialObject != null && updatedObject != null) {

				reviewFeildGroupName = getReviewFeildGroupName(initialObject);
				reviewFeildGroupType = getReviewFiledGroupType(initialObject);

				Method[] methods = clazz.getDeclaredMethods();
				Map<String, String> entityAuditFeilds = auditFeilds
						.get(reviewFeildGroupType);
				Field[] fields = clazz.getDeclaredFields();
				for (Field feild : fields) {
					if (entityAuditFeilds.containsKey(feild.getName())) {
						try {
							feild.setAccessible(true);
						} catch (SecurityException e) {
							continue;
						}
						try {
							feild.setAccessible(true);
						} catch (SecurityException e) {
							continue;
						}
						Object initialValue = feild.get(initialObject);
						Object updatedValue = feild.get(updatedObject);
						if (initialValue != null && updatedValue != null) {
							ReviewActionLog reviewActionLog = null;
							if (!feild.getName().equals("projectIds") && initialValue.equals(updatedValue)) {
								continue;
							} else {
								if (WRAPPER_TYPES.contains(initialValue
										.getClass())) {

									if (feild.getName().equals("projectIds")) {// special
										if (getActionLogForProjects(
												reviewFeildGroupName,
												(List<Integer>) initialValue,
												(List<Integer>) updatedValue,
												reviewFormInfoVO) != null) {
											reviewActionLogs
													.add(getActionLogForProjects(
															reviewFeildGroupName,
															(List<Integer>) initialValue,
															(List<Integer>) updatedValue,
															reviewFormInfoVO));
										}
										continue;
									}

									if (feild.getName().equals("isApproved")) {// special effect for not applicable field
										reviewActionLog = createReviewActionLog(
												reviewFormInfoVO, "UPDATE",
												reviewFeildGroupType,
												reviewFeildGroupName, null,
												null, null, null);
										if (initialValue.toString().equals("0")
												&& updatedValue.toString()
														.equals("1")) {

											reviewActionLog
													.setUserCommentText("Appriaser( "
															+ reviewFormInfoVO
																	.getCurrentAppraiser()
															+ " )Approved '"
															+ reviewFeildGroupName
															+ "' Objective");

										} else if (initialValue.toString()
												.equals("1")
												&& updatedValue.toString()
														.equals("0")) {

											reviewActionLog
													.setUserCommentText("Appriaser("
															+ reviewFormInfoVO
																	.getCurrentAppraiser()
															+ ") Moved  Approved '"
															+ reviewFeildGroupName
															+ "' Objective to Disapproved for appraise("
															+ reviewFormInfoVO
																	.getAppraise()
															+ ")");
										}
										reviewActionLogs.add(reviewActionLog);
										continue;
									}
									// code for targetCompletiondate and
									// Achievement date

									if (feild.getName()
											.equals("achievmentDate")
											|| feild.getName().equals(
													"targetCompletionDate")) {

										SimpleDateFormat dateFormat = new SimpleDateFormat(
												"dd-MM-yyyy");
										initialValue = dateFormat
												.format((java.util.Date) initialValue);
										updatedValue = dateFormat
												.format((java.util.Date) updatedValue);
									}
									// adding code for rating notes.
									if (feild.getName().equals("appraiseRatingId")||feild.getName().equals("appraiserRatingId")) {
										// special effect
										if (ratingDropDown == null) {
											createRatingDropDown();
										}
										 // for project dropdown

										updatedValue = ratingDropDown.get((Integer) feild
												.get(updatedObject));
										initialValue = ratingDropDown
												.get((Integer) feild
														.get(initialObject));
									}
									
									if (feild.getName().equals("expectedProficiencyLevelId")) {// special
										// effect
										if (proficiencyLevelDropDown == null) {
											createProficiencylevelDropDown();
										} 
										// for project dropdown

										updatedValue = proficiencyLevelDropDown.get((Integer) feild
												.get(updatedObject));
										initialValue = proficiencyLevelDropDown
												.get((Integer) feild
														.get(initialObject));
									}

									String fieldName = entityAuditFeilds
											.get(feild.getName());
									reviewFeildGroupName = getReviewFeildGroupName(initialObject);
									reviewActionLog = createReviewActionLog(
											reviewFormInfoVO, "UPDATE",
											reviewFeildGroupType,
											reviewFeildGroupName, fieldName,
											initialValue.toString(),
											updatedValue.toString(), null);
									reviewActionLogs.add(reviewActionLog);
								}

								else {

									reviewActionLogs.addAll(getReviewActionLog(
											initialValue.getClass(),
											initialValue, updatedValue,
											reviewFormInfoVO));
								}
							}
						}
						if (initialValue != null && updatedValue == null) {

							ReviewActionLog reviewActionLog = null;

							if (WRAPPER_TYPES.contains(initialValue.getClass())) {

								if (feild.getName().equals("projectIds")) {// special
									if (getActionLogForProjects(
											reviewFeildGroupName,
											(List<Integer>) initialValue,
											(List<Integer>) updatedValue,
											reviewFormInfoVO) != null) {
										reviewActionLogs
												.add(getActionLogForProjects(
														reviewFeildGroupName,
														(List<Integer>) initialValue,
														(List<Integer>) updatedValue,
														reviewFormInfoVO));
									}
									continue;
								}
								// adding code for rating notes.
								if (feild.getName().equals("appraiseRatingId")||feild.getName().equals("appraiserRatingId")) {// special
									// effect
									if (ratingDropDown == null) {
										createRatingDropDown();
									} // for
										// project
									// dropdown
									initialValue = ratingDropDown
											.get((Integer) feild
													.get(initialObject));
								}
								
								if (feild.getName().equals("achievmentDate")
										|| feild.getName().equals(
												"targetCompletionDate")) {

									SimpleDateFormat dateFormat = new SimpleDateFormat(
											"dd-MM-yyyy");
									initialValue = dateFormat
											.format((java.util.Date) initialValue);

								}
								
								if (feild.getName().equals("expectedProficiencyLevelId")) {// special
									// effect
									if (proficiencyLevelDropDown == null) {
										createProficiencylevelDropDown();
									} // for
										// project
									// dropdown
									initialValue = proficiencyLevelDropDown
											.get((Integer) feild
													.get(initialObject));
								}

								String fieldName = entityAuditFeilds.get(feild
										.getName());
								reviewFeildGroupName = getReviewFeildGroupName(initialObject);
								reviewActionLog = createReviewActionLog(
										reviewFormInfoVO, "UPDATE",
										reviewFeildGroupType,
										reviewFeildGroupName, fieldName,
										initialValue.toString(), "NO VALUE",
										null);
								reviewActionLogs.add(reviewActionLog);
							}

							else {

								reviewActionLogs.addAll(getReviewActionLog(
										initialValue.getClass(), initialValue,
										updatedValue, reviewFormInfoVO));

							}

						}
						if (initialValue == null && updatedValue != null) {

							if (WRAPPER_TYPES.contains(updatedValue.getClass())) {

								if (feild.getName().equals("projectIds")) {// special
									if (getActionLogForProjects(
											reviewFeildGroupName,
											(List<Integer>) initialValue,
											(List<Integer>) updatedValue,
											reviewFormInfoVO) != null) {
										reviewActionLogs
												.add(getActionLogForProjects(
														reviewFeildGroupName,
														(List<Integer>) initialValue,
														(List<Integer>) updatedValue,
														reviewFormInfoVO));
									}
									continue;
								}

								if (feild.getName().equals("achievmentDate")
										|| feild.getName().equals(
												"targetCompletionDate")) {

									SimpleDateFormat dateFormat = new SimpleDateFormat(
											"dd-MM-yyyy");
									updatedValue = dateFormat
											.format((java.util.Date) updatedValue);

								}
								
								if (feild.getName().equals("appraiseRatingId")||feild.getName().equals("appraiserRatingId")) {// special
									// effect
									if (ratingDropDown == null) {
										createRatingDropDown();
									} // for
										// project
									// dropdown
									updatedValue = ratingDropDown
											.get((Integer) feild
													.get(updatedObject));
								}
								
								if (feild.getName().equals("expectedProficiencyLevelId")) {// special
									// effect
									if (proficiencyLevelDropDown == null) {
										createProficiencylevelDropDown();
									} // for
										// project
									// dropdown

									updatedValue = proficiencyLevelDropDown.get((Integer) feild
											.get(updatedObject));
								}

								String fieldName = entityAuditFeilds.get(feild
										.getName());
								reviewFeildGroupName = getReviewFeildGroupName(initialObject);
								ReviewActionLog reviewActionLog = createReviewActionLog(
										reviewFormInfoVO, "UPDATE",
										reviewFeildGroupType,
										reviewFeildGroupName, fieldName,
										"NO VALUE", updatedValue.toString(),
										null);
								reviewActionLogs.add(reviewActionLog);
							}

							else {

								reviewActionLogs.addAll(getReviewActionLog(
										updatedValue.getClass(), initialValue,
										updatedValue, reviewFormInfoVO));

							}

						}
						if (initialValue == null && updatedValue == null) {
							continue;
						}

					}
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return reviewActionLogs;

	}

	public List<ReviewActionLog> getReviewActionLog(Class clazz,
			List initialList, List updatedList,
			ReviewFormInfoVO reviewFormInfoVO) {
		List<ReviewActionLog> reviewActionLogs = new ArrayList<ReviewActionLog>();
		
		if(clazz==ReviewObjective.class){
			
			createProjectDropDown();
		}

		int loopCounter = 0;
		if ((initialList == null || initialList.size() == 0)
				&& (updatedList != null && ((List) updatedList).size() > 0)) {

			loopCounter = updatedList.size();
			Collections.sort(updatedList);
			initialList = new ArrayList();

		}

		if ((updatedList == null || updatedList.size() == 0)
				&& (initialList != null && initialList.size() > 0)) {

			loopCounter = initialList.size();
			Collections.sort(initialList);
			updatedList = new ArrayList();
		}

		if (updatedList.size() > 0 && initialList.size() > 0) {

			Collections.sort(initialList);
			Collections.sort(updatedList);
			reArrangeList(initialList, updatedList);
			loopCounter = Math.max(initialList.size(), updatedList.size());

		}
		for (int i = 0; i < loopCounter; i++) {
			Object initialObject = null;
			Object updatedObject = null;
			try {

				initialObject = initialList.get(i);
			}

			catch (IndexOutOfBoundsException e) {
			}
			try {

				updatedObject = updatedList.get(i);
			} catch (IndexOutOfBoundsException e) {
			}
			if (!(initialObject == null && updatedObject == null)) {
				reviewActionLogs.addAll(getReviewActionLog(clazz,
						initialObject, updatedObject, reviewFormInfoVO));
			}
		}

		return reviewActionLogs;
	}

	public void reArrangeList(List initialList, List updatedList) {
		int loopCounter = 0;

		if ((initialList == null || initialList.size() == 0)
				&& (updatedList == null || updatedList.size() == 0)) {

			return;
		}

		if ((initialList == null || initialList.size() == 0)
				&& (updatedList != null && updatedList.size() > 0)) {
			loopCounter = updatedList.size();

			for (int i = 0; i < loopCounter; i++) {

				initialList.add(null);
			}

		}

		if ((updatedList == null || updatedList.size() == 0)
				&& (initialList != null && initialList.size() > 0)) {

			loopCounter = initialList.size();

			for (int i = 0; i < loopCounter; i++) {

				updatedList.add(null);
			}
		}

		if (updatedList.size() > 0 && initialList.size() > 0) {
			loopCounter = initialList.size() + updatedList.size();
			if (initialList.get(0).getClass() == ReviewObjectiveVO.class) {
				for (int i = 0; i < loopCounter; i++) {

					ReviewObjectiveVO initialObject = null;
					ReviewObjectiveVO updatedObject = null;

					try {
						initialObject = (ReviewObjectiveVO) initialList.get(i);

					}

					catch (IndexOutOfBoundsException e) {
						initialList.add(i, null);
						continue;

					}

					try {

						updatedObject = (ReviewObjectiveVO) updatedList.get(i);

					}

					catch (IndexOutOfBoundsException e) {
						updatedList.add(i, null);
						continue;

					}

					if (initialObject.getReviewObjectiveId() > updatedObject
							.getReviewObjectiveId()) {
						initialList.add(i, null);
					}
					if (initialObject.getReviewObjectiveId() < updatedObject
							.getReviewObjectiveId()) {
						updatedList.add(i, null);
					}

				}
			}

		}

	}

	private String getReviewFeildGroupName(Object object) {

		String reviewFeildGroupName = null;

		if (object.getClass().isAssignableFrom(ReviewCompetencyVO.class)) {

			reviewFeildGroupName = ((ReviewCompetencyVO) object)
					.getCompetencyName();

		}
		if (object.getClass().isAssignableFrom(
				ReviewDevelopmentPlanActivityVO.class)) {

			reviewFeildGroupName = ((ReviewDevelopmentPlanActivityVO) object)
					.getGoalComment();

		}
		if (object.getClass().isAssignableFrom(ReviewObjectiveVO.class)) {

			reviewFeildGroupName = ((ReviewObjectiveVO) object)
					.getObjectiveName();

		}
		if (object.getClass().isAssignableFrom(ReviewSummaryVO.class)) {

			reviewFeildGroupName = "Review Summary";

		}

		return reviewFeildGroupName;

	}

	private ReviewFeildGroupType getReviewFiledGroupType(Object object) {

		ReviewFeildGroupType reviewFeildGroupType = null;

		if (object.getClass().isAssignableFrom(ReviewCompetencyVO.class)) {

			reviewFeildGroupType = ReviewFeildGroupType.COMPETENCY;

		}
		if (object.getClass().isAssignableFrom(
				ReviewDevelopmentPlanActivityVO.class)) {

			reviewFeildGroupType = ReviewFeildGroupType.DEVELOPMENT_PLAN;

		}
		if (object.getClass().isAssignableFrom(ReviewObjectiveVO.class)) {

			reviewFeildGroupType = ReviewFeildGroupType.OBJECTIVE;

		}
		if (object.getClass().isAssignableFrom(ReviewSummaryVO.class)) {

			reviewFeildGroupType = ReviewFeildGroupType.SUMMARY;

		}
		return reviewFeildGroupType;
	}

	private HashSet<Class<?>> getWrapperTypes() {
		HashSet<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		ret.add(String.class);
		ret.add(Date.class);
		ret.add(java.sql.Date.class);
		ret.add(java.util.ArrayList.class);
		return ret;
	}

	private Map<String, String> getObjectiveAuditFeilds() {

		Map<String, String> objectiveAuditFeilds = new HashMap<String, String>();

		objectiveAuditFeilds.put("objectiveName", "Objective Name");
		objectiveAuditFeilds.put("achievmentDate", "Achievement Date");
		objectiveAuditFeilds.put("targetCompletionDate",
				"Target Completion Date");
		objectiveAuditFeilds.put("appraiseNegativeComment",
				"Appraise Negative Comment");
		objectiveAuditFeilds.put("appraisePositiveComment",
				"Appraise Positive Comment");
		objectiveAuditFeilds.put("appraiserNegativeComment",
				"Appraiser Negative Comment");
		objectiveAuditFeilds.put("appraiserPositiveComment",
				"Appraise Positive Comment");
		objectiveAuditFeilds.put("detailsComment", "Details");
		objectiveAuditFeilds.put("successCriteriaComment", "Success Criteria");
		objectiveAuditFeilds
				.put("howYouAchievedComment", "How Did You Achieve");
		objectiveAuditFeilds.put("appraiseRatingId", "Appraise Rating");
		objectiveAuditFeilds.put("appraiserRatingId", "Appraiser Rating");
		objectiveAuditFeilds.put("projectIds", "Project Name");
		objectiveAuditFeilds.put("isApproved",
				"Objective is not applicable set by manager");

		return objectiveAuditFeilds;
	}

	private Map<String, String> getCompetencyAuditFeilds() {

		Map<String, String> competencyAuditFeilds = new HashMap<String, String>();

		competencyAuditFeilds.put("appraiseCommentText", "Appraise Comment");
		competencyAuditFeilds.put("appraiserCommentText", "Appraiser Comment");
		competencyAuditFeilds.put("appraiseRatingId", "Appraise Rating");
		competencyAuditFeilds.put("appraiserRatingId", "Appraiser Rating");
		competencyAuditFeilds.put("expectedProficiencyLevelId", "Expected Proficiency Level");

		return competencyAuditFeilds;
	}

	private Map<String, String> getReviewDevlopmentPlanActivityAuditFeilds() {

		Map<String, String> reviewDevlopmentPlanActivityAuditFeilds = new HashMap<String, String>();

		reviewDevlopmentPlanActivityAuditFeilds.put("actionStepsComment",
				"Action Steps");
		reviewDevlopmentPlanActivityAuditFeilds.put("appraiseComment",
				"Appraise Comment");
		reviewDevlopmentPlanActivityAuditFeilds.put("appraiserComment",
				"Appraiser Comment");
		reviewDevlopmentPlanActivityAuditFeilds.put("goalComment", "Goal");
		reviewDevlopmentPlanActivityAuditFeilds.put("measurementComment",
				"Measurement");

		return reviewDevlopmentPlanActivityAuditFeilds;
	}

	private Map<String, String> getReviewSummaryAuditFeilds() {

		Map<String, String> reviewSummaryAuditFeilds = new HashMap<String, String>();

		reviewSummaryAuditFeilds.put("appraiseComment", "Appraise Comment");
		reviewSummaryAuditFeilds.put("appraiserComment", "Appraiser Comment");
		reviewSummaryAuditFeilds.put("appraiseRatingId", "Appraise Rating");
		reviewSummaryAuditFeilds.put("appraiserRatingId", "Appraiser Rating");

		return reviewSummaryAuditFeilds;
	}

	private Map<ReviewFeildGroupType, Map<String, String>> getAuditFeilds() {

		Map<ReviewFeildGroupType, Map<String, String>> auditFields = new HashMap<ReviewFeildGroupType, Map<String, String>>();

		auditFields.put(ReviewFeildGroupType.COMPETENCY,
				getCompetencyAuditFeilds());
		auditFields.put(ReviewFeildGroupType.OBJECTIVE,
				getObjectiveAuditFeilds());
		auditFields.put(ReviewFeildGroupType.DEVELOPMENT_PLAN,
				getReviewDevlopmentPlanActivityAuditFeilds());
		auditFields.put(ReviewFeildGroupType.SUMMARY,
				getReviewSummaryAuditFeilds());

		return auditFields;

	}

	@Override
	public ReviewActionLog createReviewActionLog(
			ReviewFormInfoVO reviewFormInfoVO, String actionType,
			ReviewFeildGroupType reviewFeildGroupType,
			String reviewFieldGroupName, String fieldName, String initialValue,
			String updatedValue, String userCommentText) {

		ReviewActionLog reviewActionLog = new ReviewActionLog(reviewFormInfoVO);
		reviewActionLog.setActionType(actionType);
		reviewActionLog.setReviewFieldGroupType(reviewFeildGroupType);
		reviewActionLog.setReviewFieldGroupName(reviewFieldGroupName);
		reviewActionLog.setFieldName(fieldName);
		// reviewActionLog.setUpdatedValue(HtmlUtils.htmlEscape(updatedValue));
		// reviewActionLog.setInitialValue(HtmlUtils.htmlEscape(initialValue));
		// reviewActionLog.setUserCommentText(HtmlUtils.htmlEscape(userCommentText));
		reviewActionLog.setUpdatedValue(updatedValue);
		reviewActionLog.setInitialValue(initialValue);
		reviewActionLog.setUserCommentText(userCommentText);

		return reviewActionLog;
	}

	@Override
	public void saveReviewActionLog(ReviewActionLog reviewActionLog) {
		reviewActionLogDAO.saveReviewActionLog(reviewActionLog);

	}

	@Override
	public void saveReviewActionLog(List<ReviewActionLog> reviewActionLogs) {
		reviewActionLogDAO.saveReviewActionLogs(reviewActionLogs);
	}

	@Override
	public List<ReviewActionLogVO> getReviewActionLog(int reviewHeaderId,
			String reviewFormRole,int startIndex, int pageDisplayLength,
			String searchValue,String sortDirection, String colIndex,DataTablesJSONWrapper reviewFormLinkVOWrapper) {

		List<ReviewActionLogVO> reviewActionLogs = new ArrayList<ReviewActionLogVO>();

		List list = reviewActionLogDAO.getReviewLog(reviewHeaderId,
				reviewFormRole,startIndex,pageDisplayLength,searchValue,sortDirection,colIndex);
		
		for (Object object : list) {

			Object[] objects = (Object[]) object;
			ReviewActionLogVO reviewActionLogVO = new ReviewActionLogVO();
			reviewActionLogVO.setCreatedByName(objects[0].toString());
			reviewActionLogVO.setCreatedByRole(objects[1].toString()
					.toLowerCase());
			reviewActionLogVO.setReviewFieldGroupType(objects[2].toString()
					.toLowerCase());
			reviewActionLogVO.setCreatedDate(objects[3].toString());
			// reviewActionLogVO.setCommentText(objects[4].toString());
		
			try {
				Clob clob = (Clob) objects[4];
				StringBuffer buffer = new StringBuffer();
				if(clob.length() <= Integer.MAX_VALUE) {
					buffer.append(clob.getSubString(1, (int) clob.length()));
				} else {
					BufferedReader reader = new BufferedReader(new InputStreamReader(clob.getAsciiStream()));
					String read = null;
					while ((read = reader.readLine()) != null) {
						buffer.append(read + "\n");
					}
				}
				reviewActionLogVO.setCommentText(buffer);
			} catch (Exception e) {
				log.error("Exception while displaying reviewLogs:"+e);
			}
			reviewActionLogs.add(reviewActionLogVO);
		}
		
		Long totalActivityCount=reviewActionLogDAO.getTotalActivityCount(reviewHeaderId,reviewFormRole);
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(totalActivityCount);
		reviewFormLinkVOWrapper.setiTotalRecords(totalActivityCount);

		return reviewActionLogs;
	}

	@Override
	public void substituteRatingIdsWithRatingValues(List reviewEntities,
			Map<Integer, String> dropdown, Class clazz) {
		if (reviewEntities != null && reviewEntities.size() > 0) {
			if (clazz == ReviewCompetencyVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {

					ReviewCompetencyVO reviewCompetencyVO = (ReviewCompetencyVO) reviewEntities
							.get(i);
					if (reviewCompetencyVO != null) {
						if (dropdown.containsKey(reviewCompetencyVO
								.getAppraiseRatingId())) {

							reviewCompetencyVO.setAppraiseRatingId(Integer
									.valueOf(dropdown.get(
											reviewCompetencyVO
													.getAppraiseRatingId())
											.split("-")[0].trim()));
						}
						if (dropdown.containsKey(reviewCompetencyVO
								.getAppraiserRatingId())) {

							reviewCompetencyVO.setAppraiserRatingId(Integer
									.valueOf(dropdown.get(
											reviewCompetencyVO
													.getAppraiserRatingId())
											.split("-")[0].trim()));
						}
					}
				}

			}
			if (clazz == ReviewObjectiveVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {

					ReviewObjectiveVO reviewObjectiveVO = (ReviewObjectiveVO) reviewEntities
							.get(i);
					if (reviewObjectiveVO != null) {
						if (dropdown.containsKey(reviewObjectiveVO
								.getAppraiseRatingId())) {

							reviewObjectiveVO.setAppraiseRatingId(Integer
									.valueOf(dropdown.get(
											reviewObjectiveVO
													.getAppraiseRatingId())
											.split("-")[0].trim()));
						}
						if (dropdown.containsKey(reviewObjectiveVO
								.getAppraiserRatingId())) {

							reviewObjectiveVO.setAppraiserRatingId(Integer
									.valueOf(dropdown.get(
											reviewObjectiveVO
													.getAppraiserRatingId())
											.split("-")[0].trim()));
						}

					}
				}
			}
			if (clazz == ReviewHeaderVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {

					ReviewHeaderVO reviewHeaderVO = (ReviewHeaderVO) reviewEntities
							.get(i);
					if (reviewHeaderVO != null) {
						if (dropdown.containsKey(reviewHeaderVO
								.getAppraiseRatingId())) {

							reviewHeaderVO.setAppraiseRatingId(Integer
									.valueOf(dropdown.get(reviewHeaderVO
											.getAppraiseRatingId())));
						}
						if (dropdown.containsKey(reviewHeaderVO
								.getAppraiserRatingId())) {

							reviewHeaderVO.setAppraiserRatingId(Integer
									.valueOf(dropdown.get(reviewHeaderVO
											.getAppraiserRatingId())));
						}

					}
				}
			}
		}

	}

	private Map<String, Integer> reverseDropDown(Map<Integer, String> dropdown) {

		Map<String, Integer> dropdownMap = new HashMap<String, Integer>();
		for (Integer key : dropdown.keySet()) {

			dropdownMap.put(dropdown.get(key), key);
		}

		return dropdownMap;
	}

	@Override
	public void substituteRatingValuesWithRatingIds(List reviewEntities,
			Map<Integer, String> dropdown, Class clazz) {

		Map<String, Integer> dropdownMap = reverseDropDown(dropdown);

		if (reviewEntities != null && reviewEntities.size() > 0) {
			if (clazz == ReviewCompetencyVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {
					ReviewCompetencyVO reviewCompetencyVO = (ReviewCompetencyVO) reviewEntities
							.get(i);
					if (reviewCompetencyVO != null) {
						if (reviewCompetencyVO.getAppraiseRatingId() != null
								&& dropdownMap.containsKey(reviewCompetencyVO
										.getAppraiseRatingId().toString())) {

							reviewCompetencyVO.setAppraiseRatingId(dropdownMap
									.get(reviewCompetencyVO
											.getAppraiseRatingId().toString()));

						}
						if (reviewCompetencyVO.getAppraiserRatingId() != null
								&& dropdownMap.containsKey(reviewCompetencyVO
										.getAppraiserRatingId().toString())) {
							reviewCompetencyVO
									.setAppraiserRatingId(dropdownMap
											.get(reviewCompetencyVO
													.getAppraiserRatingId()
													.toString()));
						}

					}

				}

			}

			if (clazz == ReviewObjectiveVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {
					ReviewObjectiveVO reviewObjectiveVO = (ReviewObjectiveVO) reviewEntities
							.get(i);
					if (reviewObjectiveVO != null) {

						if (reviewObjectiveVO.getAppraiseRatingId() != null
								&& dropdownMap.containsKey(reviewObjectiveVO
										.getAppraiseRatingId().toString())) {
							reviewObjectiveVO.setAppraiseRatingId(dropdownMap
									.get(reviewObjectiveVO
											.getAppraiseRatingId().toString()));

						}
						if (reviewObjectiveVO.getAppraiserRatingId() != null
								&& dropdownMap.containsKey(reviewObjectiveVO
										.getAppraiserRatingId().toString())) {

							reviewObjectiveVO
									.setAppraiserRatingId(dropdownMap
											.get(reviewObjectiveVO
													.getAppraiserRatingId()
													.toString()));
						}
					}
				}

			}

			if (clazz == ReviewHeaderVO.class) {
				for (int i = 0; i < reviewEntities.size(); i++) {
					ReviewHeaderVO reviewHeaderVO = (ReviewHeaderVO) reviewEntities
							.get(i);
					if (reviewHeaderVO != null) {

						if (reviewHeaderVO.getAppraiseRatingId() != null
								&& dropdownMap.containsKey(reviewHeaderVO
										.getAppraiseRatingId().toString())) {
							reviewHeaderVO.setAppraiseRatingId(dropdownMap
									.get(reviewHeaderVO.getAppraiseRatingId()
											.toString()));

						}
						if (reviewHeaderVO.getAppraiserRatingId() != null
								&& dropdownMap.containsKey(reviewHeaderVO
										.getAppraiserRatingId().toString())) {

							reviewHeaderVO.setAppraiserRatingId(dropdownMap
									.get(reviewHeaderVO.getAppraiserRatingId()
											.toString()));
						}
					}
				}

			}

		}
	}
	
	private ReviewActionLog getActionLogForProjects(String reviewFeildGroupName,List<Integer> initialList,List<Integer> updatedList,ReviewFormInfoVO reviewFormInfoVO){

		if (initialList == null) {

			initialList = new ArrayList<Integer>();
		}
		if (updatedList == null) {

			updatedList = new ArrayList<Integer>();
		}
		List<Integer> projectsToDelete = new ArrayList<Integer>();
		List<Integer> projectsToAdd = new ArrayList<Integer>();
		String logText = "";

		for (Integer projectIdUpdated : updatedList) {
			boolean isNew = true;

			for (Integer projectIdinitial : initialList) {

				if (projectIdinitial == projectIdUpdated) {
					isNew = false;
					break;
				}

			}
			if (isNew) {
				projectsToAdd.add(projectIdUpdated);
			}

		}

		for (Integer projectIdinitial : initialList) {

			boolean canDelete = true;

			for (Integer projectIdUpdated : updatedList) {

				if (projectIdinitial == projectIdUpdated) {

					canDelete = false;
					break;
				}

			}

			if (canDelete) {

				projectsToDelete.add(projectIdinitial);

			}

		}
		if (projectsToAdd.size() == 0 && projectsToDelete.size() == 0) {
			return null;
		}

		if (projectDropDown == null) {

			createProjectDropDown();
		}
		if (projectsToAdd.size() > 0) {

			logText = logText + "\n\nAdded Projects :";

			for (Integer projectId : projectsToAdd) {

				logText = logText + "\n" + projectDropDown.get(projectId);

			}
		}
		if (projectsToDelete.size() > 0) {

			logText = logText + "\n\nDeleted Projects :";

			for (Integer projectId : projectsToDelete) {

				logText = logText + "\n" + projectDropDown.get(projectId);

			}
		}

		return createReviewActionLog(reviewFormInfoVO, "UPDATE",
				ReviewFeildGroupType.OBJECTIVE, reviewFeildGroupName,
				"Projects", "", logText, null);

	}

}
