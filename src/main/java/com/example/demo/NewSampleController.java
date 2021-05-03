package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;







@RestController
public class NewSampleController {
	
	long i=0;
	
	@RequestMapping("/health")
	public String healthCheck() {
		return "OK";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public String employees(HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		List list1 = new ArrayList();
		list1.add(1);
		list1.add("Himanshu");
		list1.add("Development");
		list1.add("Delhi");
		list1.add("Developer");
		

		String json = new Gson().toJson(list1);
		return json;

	}
	
	}
	@RequestMapping(value = "/employees", method = RequestMethod.PUT)
	public String employees1(@RequestParam(required = false) String id,@RequestParam(required = false) String name,@RequestParam(required = false) String city,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(200);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		 org.json.JSONObject obj = new org.json.JSONObject();
		  obj.put("id", id);
		  obj.put("name", name);
		  obj.put("city", city);
		  obj.put("timeOfCreation", ts);
		  String dd = obj.toString(); 
	
		  return "Data has been updated successfully";
		
				}
	}
	
	@RequestMapping(value = "/employees", method = RequestMethod.POST)
	public String employees2(@RequestParam(required = false) String name,@RequestParam(required = false) String city,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(201);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		int max = 100;
		int min = 1;
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		 org.json.JSONObject obj = new org.json.JSONObject();
		  obj.put("id", randomNum);
		  obj.put("name", name);
		  obj.put("city", city);
		  obj.put("timeOfCreation", ts);
		  String dd = obj.toString(); 
	
		  return "Data has been saved successfully";
		

	}
	}
	
	@RequestMapping(value = "/employees", method = RequestMethod.PATCH)
	public String employees3(@RequestParam(required = false) String id,@RequestParam(required = false) String name,@RequestParam(required = false) String city,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(200);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		 org.json.JSONObject obj = new org.json.JSONObject();
		  obj.put("id", id);
		  obj.put("name", name);
		  obj.put("city", city);
		  obj.put("timeOfCreation", ts);
		  String dd = obj.toString(); 
	
		  return "Data has been updated successfully";
		

	}
	}
	@RequestMapping(value = "/employees", method = RequestMethod.DELETE)
	public String employees4(HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(200);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

		  return "Data has been deleted successfully";
		

	}
	}
	List list = new ArrayList();
	
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String employees5(@RequestParam(required = false) String name,@RequestParam(required = false) String email,@RequestParam(required = false) String password,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(201);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {

	list.add(name);
	list.add(email);
	list.add(password);
		
		 org.json.JSONObject obj = new org.json.JSONObject();

		  obj.put("name", name);
		  obj.put("email", email);
		  //obj.put("password", password);
		  obj.put("registration", "successful");
		  String dd = obj.toString(); 
		  
		  Random r = new Random();
		  ArrayList<String> ar = new ArrayList();
		  ar.add("Bhopal");
		  ar.add("Indore");
		  ar.add("Chennai");
		  ar.add("Kolkata");
		  ar.add("London");
		  ar.add("Chicago");
		  ar.add("Sydney");
		  ar.add("Colombo");
		  
		  ArrayList<String> ar1 = new ArrayList();
		  
		  ar1.add("1");
		  ar1.add("2");
		  ar1.add("3");
		  ar1.add("4");
		  ar1.add("5");
		  
		  
		  String str = null;
		  String str1 = null;
		  
		  for(int i=0; i < ar.size(); i++){
	           
	              str =  ar.get( r.nextInt(ar.size()) );
	                    
	        }
		  
		  for(int i=0; i < ar1.size(); i++){
	           
              str1 =  ar1.get( r.nextInt(ar1.size()) );
                    
        }

		//  int i = emp.updateCity(str1, str);
		
	
		  return "User has been registered successfully";
		

	}
	}
	
	@RequestMapping(value = "/unreg", method = RequestMethod.POST)
	public String employees6(@RequestParam(required = false) String name,@RequestParam(required = false) String email,@RequestParam(required = false) String password,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(201);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");

		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
	
		
		 org.json.JSONObject obj = new org.json.JSONObject();

		 
		  obj.put("error", "password format is incorrect");
		  //obj.put("password", password);
		  obj.put("registration", "unsuccessful");
		  String dd = obj.toString(); 
	
		  return "User has been unregistered successfully";
		

	}
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String employees7(@RequestParam(required = false) String name,@RequestParam(required = false) String email,@RequestParam(required = false) String password,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException {
		response.setStatus(201);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
	/*	org.json.JSONObject obj = new org.json.JSONObject();
		if(list.get(0).equals(name) && list.get(1).equals(email) && list.get(2).equals(password)){
			
			 obj.put("login", "successful");
		}
		else {
			 obj.put("login", "unsuccessful");
			
		}

		  String dd = obj.toString(); */
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		  return "login successful";
	

	}
	}
	@RequestMapping(value = "/delay", method = RequestMethod.POST)
	public String employees8(@RequestParam(required = false) String name,@RequestParam(required = false) String email,HttpServletResponse response,@RequestParam(required = false) String qparam) throws ClassNotFoundException, SQLException, InterruptedException {
		response.setStatus(201);  
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		org.json.JSONObject obj = new org.json.JSONObject();
		
		  obj.put("name", name);
		  obj.put("city", email);
          Thread.sleep(3500);
		  String dd = obj.toString(); 
			
		  return "Response delayed";
	

	}
	}
	@RequestMapping(value="/wordtopdf",method = RequestMethod.POST)
	public String wordtopdf(@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(required = false) String qparam,
			HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
         // System.out.println("mmm");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
		 i = Long.parseLong(qparam);
			if(i !=942109925) {

				return "";
			}
				else {
		String encodedString =null;
		long p = Long.parseLong(qparam);
			if(p !=942109925) {

				return "";
			}
				else {
					
					ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
					  XWPFDocument document = new XWPFDocument(in);
					  PdfOptions options = PdfOptions.create();
					  ByteArrayOutputStream out = new ByteArrayOutputStream();
					  PdfConverter.getInstance().convert(document, out, options);
			          byte[] b = ((ByteArrayOutputStream) out).toByteArray();
			          encodedString = Base64.getEncoder().encodeToString(b);
			        
			    }
		
					
				    
			
	      
	
			return encodedString;
}

	}


}
