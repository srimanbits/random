package com.ggk.hrms.review.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggk.hrms.review.constants.Role;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller("documentController")
@RequestMapping("/documents")
public class DocumentController {

	Logger log = Logger.getLogger(DocumentController.class);
	
	@Resource
	private ReviewHeaderService reviewHeaderService;
	
	@Resource
	private String helpDocsLocation;
	
	@Resource
	private String toolKitLocation;
	

	
	@RequestMapping(value = "/showManagementToolKitDocs.html", method = RequestMethod.GET)
	public String showManagementToolKitDocs(ModelMap model) {
		
		Map<String,String> documentNamePath = new HashMap<String,String>();
		File managementToolKitUserDocsFolder = new File(toolKitLocation + "userDocs");
		
		populateDocumentNamePath(documentNamePath, managementToolKitUserDocsFolder);
		List<String> authorities=SecurityDetailsAccessor.getAuthorities();
		List<Integer> leadEmpIds= reviewHeaderService.getLeadEmpIds();
		if(authorities.contains(Role.ROLE_MANAGER) || authorities.contains(Role.ROLE_SUPERUSER)||leadEmpIds.contains(SecurityDetailsAccessor.getEmpId())) {
			
			File managementToolKitManagerDocsFolder = new File(toolKitLocation + "managerDocs");
			populateDocumentNamePath(documentNamePath, managementToolKitManagerDocsFolder);
		}
		model.addAttribute("documentNamePath",documentNamePath);
		return "documents/showDocuments";
	}
	
	@RequestMapping(value = "/downloadDocument.html", method = RequestMethod.GET)
		public void downloadDocument(@RequestParam(required = true, value = "docURL") String docURL,
			HttpServletResponse response,
			ModelMap model)throws IOException {
		
		File document = new File(docURL);
		String fileName = document.getName();
		response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
		InputStream in = new FileInputStream(document);
		IOUtils.copy(in, response.getOutputStream());
	    response.flushBuffer();
	}
	
	@RequestMapping(value = "/showHelpDocs.html", method = RequestMethod.GET)
	public String showHelpDocs(ModelMap model) {
		File helpDocsFolder = new File(helpDocsLocation);
		Map<String,String> documentNamePath = new HashMap<String,String>();
		populateDocumentNamePath(documentNamePath, helpDocsFolder);
		model.addAttribute("documentNamePath",documentNamePath);
		return "documents/showDocuments";
	}
	
	public void populateDocumentNamePath(Map<String,String> documentNamePath, File folder) {
		if(folder.isDirectory()) {
			File[] documents = folder.listFiles();
			for(File document : documents) {
				documentNamePath.put(FilenameUtils.removeExtension(document.getName()), document.getAbsolutePath());
			}
		}
	}
}
