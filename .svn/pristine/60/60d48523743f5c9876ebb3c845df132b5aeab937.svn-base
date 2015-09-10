package com.ggk.hrms.review.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;

import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.service.ReviewDevelopmentPlanService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.ReviewObjectiveService;
import com.ggk.hrms.review.service.ReviewSummaryService;
import com.ggk.hrms.review.ui.vo.ReviewCompetencyVO;
import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;
import com.ggk.hrms.review.ui.vo.ReviewSummaryVO;

public class AppraisalFormPDFTask implements Runnable {

	Logger log = Logger.getLogger(AppraisalFormPDFTask.class);
	
	ReviewHeaderService reviewHeaderService;
	
	ReviewObjectiveService reviewObjectiveService;
	
	ReviewCompetencyService reviewCompetencyService;
	
	ReviewDevelopmentPlanService reviewDevelopmentPlanService;
	
	int reviewHeaderId;
	
	String jrxmlLocation;
	
	String pdfLocation;
	
	MailingService mailingService;
	
	String subject;
	
	String text;
	
	String sendTo;
	
	String sendCC[];
	
	String reviewCycleName;
	
	ReviewFormRole reviewFormRole;
	
	// Added for superuser PDF download functionality.
	
	String requestType;
	
	String superUserPdfLocation;
	
	String superUserJrxmlLocation;
	
	ReviewSummaryService reviewSummaryService;
	
	static JasperReport objectivesJR;
	
	static JasperReport competenciesJR ;
	
	static JasperReport developmentPlanJR;
	
    static JasperReport superUserObjectivesJR;
	
	static JasperReport superUserCompetenciesJR ;
	
	static JasperReport superUserDevelopmentPlanJR; 
	
	static JasperReport superUserSummaryJR;
	

	
	public AppraisalFormPDFTask(ReviewHeaderService reviewHeaderService, ReviewObjectiveService reviewObjectiveService, ReviewCompetencyService reviewCompetencyService,
			ReviewDevelopmentPlanService reviewDevelopmentPlanService,
			ReviewSummaryService reviewSummaryService, ReviewFormRole reviewFormRole, int reviewHeaderId, String jrxmlLocation, String pdfLocation,
			MailingService mailingService, String subject, String text, String sendTo, String[] sendCC, String reviewCycleName,String requestType,String superUserPdfLocation,
			String superUserJrxmlLocation) {
		super();
		this.reviewHeaderService = reviewHeaderService;
		this.reviewObjectiveService = reviewObjectiveService;
		this.reviewCompetencyService = reviewCompetencyService;
		this.reviewDevelopmentPlanService = reviewDevelopmentPlanService;
		this.reviewFormRole = reviewFormRole;
		this.reviewHeaderId = reviewHeaderId;
		this.jrxmlLocation = jrxmlLocation;
		this.pdfLocation = pdfLocation;
		this.mailingService = mailingService;
		this.subject = subject;
		this.text = text;
		this.sendTo = sendTo;
		this.sendCC = sendCC;
		this.reviewCycleName = reviewCycleName;
		// Added for superuser PDF download functionality.
		this.requestType= requestType;
		this.superUserJrxmlLocation = superUserJrxmlLocation;
		this.superUserPdfLocation = superUserPdfLocation;
		this.reviewSummaryService = reviewSummaryService;
	}

	public void run() {
		try {
			File appraisalFormPdf = generateAppraisalFormPDF();
			if(requestType.equals("SYS_GENERATED")== false)
				return;
			if(appraisalFormPdf != null) {
				mailingService.sendAppraisalPDF(sendTo, sendCC, subject, text, reviewCycleName, appraisalFormPdf);
				if(appraisalFormPdf.delete()) {
					log.info("pdf deleted successfully");
				}
			} else {
				log.info("appraisal form pdf did not generate.");
			}
		} catch (FileNotFoundException fnfe) {
			log.error("Could not locate jrxml file:", fnfe);
		} catch (JRException jre) {
			log.error("Could not generate pdf:", jre);
		}
	}
	
	public File generateAppraisalFormPDF() throws JRException, FileNotFoundException {
		//boolean isMailSent = false;
		ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(reviewHeaderId);
		log.info("PDF Generation started... for "+reviewHeader.getEmployee().getDisplayName() +"--"+reviewHeader.getReviewCycle().getReviewCycleName());
		List<JasperPrint> appraisalJPList = new ArrayList<JasperPrint>();
		
		
		//Added summary part to PDF for superusers to download
		
          if(requestType.equals("USER_REQUESTED") ){
			
			ReviewSummary reviewSummary=reviewSummaryService.getReviewSummary(reviewHeaderId, reviewFormRole);
			if(reviewSummary == null)
				reviewSummary = reviewSummaryService.getReviewSummary(reviewHeaderId, ReviewFormRole.APPRAISE);
			ReviewSummaryVO reviewSummaryVO = new ReviewSummaryVO();
			reviewSummaryService.convertToVO(reviewSummaryVO, reviewSummary);
			List<ReviewSummaryVO> reviewSummaryVOs = new ArrayList<ReviewSummaryVO>();
			reviewSummaryVOs.add(reviewSummaryVO);
			JRDataSource summaryDataSource = new JRBeanCollectionDataSource(reviewSummaryVOs);
			Map<String,Object> reviewSummaryMap = new HashMap<String,Object>();
			reviewSummaryMap.put("jrxmlLocation", jrxmlLocation);
			reviewSummaryMap.put("title", "Appraisal Form");
			reviewSummaryMap.put("appraise",reviewHeader.getEmployee().getDisplayName());
			reviewSummaryMap.put("appraiser",reviewHeader.getMainAppraiserEmployee()== null ? "No Appraiser":reviewHeader.getMainAppraiserEmployee().getDisplayName());
			reviewSummaryMap.put("reviewCycleName",reviewHeader.getReviewCycle().getReviewCycleName());
			
			JasperReport superUserSummaryJR =  JasperCompileManager.compileReport(new FileInputStream(superUserJrxmlLocation+"hrmsSummary.jrxml"));
			JasperPrint summaryJP = JasperFillManager.fillReport(superUserSummaryJR, reviewSummaryMap, summaryDataSource);
			appraisalJPList.add(summaryJP);
			
		}
		
		List<ReviewObjectiveVO> reviewObjectiveVOs = reviewObjectiveService.getReviewObjectiveVOByHeaderId(reviewHeaderId, reviewFormRole,requestType);
		if(reviewObjectiveVOs.size() > 0) {
			JRDataSource objectivesDataSource = new JRBeanCollectionDataSource(reviewObjectiveVOs);
			Map<String,Object> objectivesParameterMap = new HashMap<String,Object>();
			objectivesParameterMap.put("jrxmlLocation", jrxmlLocation);
			objectivesParameterMap.put("title", "Appraisal Form");
			objectivesParameterMap.put("appraise",reviewHeader.getEmployee().getDisplayName());
			objectivesParameterMap.put("appraiser",reviewHeader.getMainAppraiserEmployee().getDisplayName());
			objectivesParameterMap.put("reviewCycleName",reviewHeader.getReviewCycle().getReviewCycleName());
			objectivesParameterMap.put("totalRecords",reviewObjectiveVOs.size());
			if(objectivesJR == null)
			 objectivesJR = JasperCompileManager.compileReport(new FileInputStream(jrxmlLocation+"hrmsObjectives.jrxml"));
			if(superUserObjectivesJR == null)
				superUserObjectivesJR = JasperCompileManager.compileReport(new FileInputStream(superUserJrxmlLocation+"hrmsObjectives.jrxml")); //TODO
			JasperPrint objectivesJP=null;
			if(requestType.equals("SYS_GENERATED"))
				objectivesJP = JasperFillManager.fillReport(objectivesJR, objectivesParameterMap, objectivesDataSource);
			else
				objectivesJP = JasperFillManager.fillReport(superUserObjectivesJR, objectivesParameterMap, objectivesDataSource);
			appraisalJPList.add(objectivesJP);
		}
		
		List<ReviewCompetencyVO> reviewCompetencyVOs = reviewCompetencyService.getReviewCompetencyVOByHeaderId(reviewHeaderId, reviewFormRole,requestType);
		if(reviewCompetencyVOs.size() > 0) {
			JRDataSource competenciesDataSource = new JRBeanCollectionDataSource(reviewCompetencyVOs);
			Map<String,Object> competenciesParameterMap = new HashMap<String,Object>();
			competenciesParameterMap.put("jrxmlLocation", jrxmlLocation);
			if(reviewObjectiveVOs.size() <= 0) {
				competenciesParameterMap.put("title", "Appraisal Form");
			}
			competenciesParameterMap.put("appraise",reviewHeader.getEmployee().getDisplayName());
			competenciesParameterMap.put("appraiser",reviewHeader.getMainAppraiserEmployee().getDisplayName());
			competenciesParameterMap.put("reviewCycleName",reviewHeader.getReviewCycle().getReviewCycleName());
			competenciesParameterMap.put("totalRecords",reviewCompetencyVOs.size());
			if (competenciesJR == null)
				competenciesJR = JasperCompileManager
						.compileReport(new FileInputStream(jrxmlLocation
								+ "hrmsCompetencies.jrxml"));
			if (superUserCompetenciesJR == null)
				superUserCompetenciesJR = JasperCompileManager
						.compileReport(new FileInputStream(superUserJrxmlLocation
								+ "hrmsCompetencies.jrxml")); // TODO
			JasperPrint competenciesJP = null;
			if (requestType.equals("SYS_GENERATED"))
				competenciesJP = JasperFillManager.fillReport(competenciesJR,
						competenciesParameterMap, competenciesDataSource);
			else
				competenciesJP = JasperFillManager.fillReport(superUserCompetenciesJR,
						competenciesParameterMap, competenciesDataSource);
			appraisalJPList.add(competenciesJP);
		}
		
		List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivityVOs = reviewDevelopmentPlanService.getReviewDevelopmentPlanActivityVOByHeaderId(reviewHeaderId, reviewFormRole,requestType);
		if(reviewDevelopmentPlanActivityVOs.size() > 0) {
			JRDataSource developmentPlanDataSource = new JRBeanCollectionDataSource(reviewDevelopmentPlanActivityVOs);
			Map<String,Object> developmentPlanParameterMap = new HashMap<String,Object>();
			developmentPlanParameterMap.put("jrxmlLocation", jrxmlLocation);
			if(reviewObjectiveVOs.size() <= 0 && reviewCompetencyVOs.size() <= 0) {
				developmentPlanParameterMap.put("title", "Appraisal Form");
			}
			developmentPlanParameterMap.put("appraise",reviewHeader.getEmployee().getDisplayName());
			developmentPlanParameterMap.put("appraiser",reviewHeader.getMainAppraiserEmployee().getDisplayName());
			developmentPlanParameterMap.put("reviewCycleName",reviewHeader.getReviewCycle().getReviewCycleName());
			developmentPlanParameterMap.put("totalRecords",reviewDevelopmentPlanActivityVOs.size());
			if(developmentPlanJR == null)
				developmentPlanJR = JasperCompileManager.compileReport(new FileInputStream(jrxmlLocation+"hrmsDevelopmentPlan.jrxml"));
			if(superUserDevelopmentPlanJR == null)
				superUserDevelopmentPlanJR = JasperCompileManager.compileReport(new FileInputStream(superUserJrxmlLocation+"hrmsDevelopmentPlan.jrxml"));//TODO
			JasperPrint developmentPlanJP = null;
			if (requestType.equals("SYS_GENERATED"))
				developmentPlanJP = JasperFillManager.fillReport(developmentPlanJR, developmentPlanParameterMap, developmentPlanDataSource);
			else
				developmentPlanJP = JasperFillManager.fillReport(superUserDevelopmentPlanJR, developmentPlanParameterMap, developmentPlanDataSource);
			appraisalJPList.add(developmentPlanJP);
		}
		
		
		
		String fileName = reviewHeader.getEmployee().getFirstName()+reviewHeader.getEmployee().getSurName()+"_AppraisalForm_"+reviewHeader.getReviewCycle().getReviewCycleName()+".pdf";
		File appraisalFormPdf = null;
		if (requestType.equals("SYS_GENERATED")) {
			appraisalFormPdf = new File(pdfLocation + fileName);
		} else {
			appraisalFormPdf = new File(superUserPdfLocation + fileName);			
		}
		if (!appraisalFormPdf.getParentFile().exists()) {
			
			appraisalFormPdf.getParentFile().mkdirs();
		}
			
    	FileOutputStream fos = new FileOutputStream(appraisalFormPdf);
    	
		try {
			JRPdfExporter appraisalExporter = new JRPdfExporter();
			appraisalExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, appraisalJPList);
			appraisalExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
			appraisalExporter.exportReport();
			log.info("PDF Generation Completed... for "+reviewHeader.getEmployee().getDisplayName() +"--"+reviewHeader.getReviewCycle().getReviewCycleName());
		} catch (Exception e) {
			log.error("Error while generating appraisal form : "+ appraisalFormPdf,e);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				log.error("Could not close appraisalForm pdf outputStream ",e);
			}
		}
		return appraisalFormPdf;
	}
	
	
}
