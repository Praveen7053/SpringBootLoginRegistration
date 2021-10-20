package net.codejava.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.codejava.User;
import net.codejava.UserRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service 
public class UserService
{
	@Autowired
	private UserRepository userRepository;
	
	/* for search */
	public List<User> getAllUser(){
	  List<User> list =  (List<User>)userRepository.findAll();
	  return list;
	}
	
	/* for search */
	 public List<User> getBySearchUsers(String searchUsers){
	  return userRepository.findBySearchUsers(searchUsers);
	 }

	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		String path = "D:\\Report";
		
		
		
		List<User> list = userRepository.findAll();
		
	
		
		File file = ResourceUtils.getFile("classpath:reports\\UserReport.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath()); 
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("CreatedBy", "Praveen");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
		//create for HTML
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\User_File.html");
		}
		
		//create for PDF
		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\User_File.pdf");
		} 
		
		return "Report generated in path : "+ path;
	}
}
