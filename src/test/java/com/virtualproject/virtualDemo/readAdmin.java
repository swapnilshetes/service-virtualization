package com.virtualproject.virtualDemo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.junit.Assert;


import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import groovy.json.JsonException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Unit test for simple App.
 */
public class readAdmin 
{
	public String checkStubMappingUUID(String map1) throws ParseException {
		RestAssured.baseURI = "http://localhost:8013/__admin/mappings";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");	
		Response response = request.get();
		
		JSONParser parser = new JSONParser();
		JSONObject containerObjectson = (JSONObject) parser.parse(response.asString());
		 
		
		JSONArray ja = (JSONArray) containerObjectson.get("mappings");
		Iterator itr2 = ja.iterator(); 
		String uuiid = null ;
		String returnUUID = null;
		Iterator<Map.Entry> itr1;
		        while (itr2.hasNext())  
		        { 
		        	itr1 = ((Map) itr2.next()).entrySet().iterator(); 
					            while (itr1.hasNext()) { 
					                Map.Entry pair = itr1.next(); 
					                		if(pair.getKey().toString().contains("id")) {
					                 			uuiid =  (String) pair.getValue(); 
					                 			System.out.println("Contains KEY"+ pair.getKey() + "Contains VALUE"+pair.getValue());
					                		}
					                		
					                			if(pair.getKey().toString().contains("request")) {
					                					
										                JSONObject conta = (JSONObject) parser.parse( pair.getValue().toString());
										                JSONArray a = (JSONArray) conta.get("bodyPatterns");
										                
									                Iterator itr3 = a.iterator(); 
									        		Iterator<Map.Entry> itr4;
											        		 while (itr3.hasNext()) { 
											        			 itr4 = ((Map) itr3.next()).entrySet().iterator(); 
													        			 while (itr4.hasNext()) { 
													        				 Map.Entry pair1 = itr4.next(); 
													        				 
													        				  if(pair1.getKey().toString().equals("equalToJson") && pair1.getValue().toString().equals(map1.toString())){
													        					  System.out.println("actu IF>>"+ pair1.getValue().toString());
														        				  System.out.println("expec IF >>"+ map1.toString());
														        					System.out.println("IFFF"+ uuiid);
													        					  returnUUID = uuiid;
													        					  break;
													        				  } else {
													        					  returnUUID = null;
													        				  }
													        			 }
											        			}
									        		 }
					                }
		               
		                
		            } 
		        
		     return   returnUUID;	        
		        
} 
  
	
		    
		
}
