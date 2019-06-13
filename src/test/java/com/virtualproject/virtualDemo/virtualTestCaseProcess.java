package com.virtualproject.virtualDemo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.junit.Assert;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.RequestTemplateModel;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import groovy.json.JsonException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.jayway.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

import com.relevantcodes.extentreports.LogStatus;


import static io.restassured.RestAssured.given;

/**
 * Unit test for simple App.
 */
public class virtualTestCaseProcess
{
	public static String URL = "/registration_api";
	public static String PROXYBASEURL = "http://localhost:8181/registration/registration_api.php";
	static ResponseBody body;
	static JSONObject map1;
	public static ResponseBody tst(String map) throws HttpHostConnectException {
				WireMock wm = new WireMock();
			    WireMockServer wireMockServer=null;
			    System.out.println("********** MAP" + map);
			    try {
			    
			    	
			        wireMockServer = new WireMockServer(8099);
			        wireMockServer.start();
			        configureFor("localhost", 8099);
			        //FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"/mappings"));
			        //FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"/__files"));
			        wireMockServer.enableRecordMappings(new SingleRootFileSource(System.getProperty("user.dir")+ "/mappings"), new SingleRootFileSource(System.getProperty("user.dir")+ "/__files"));
			        //StubMapping checkUUID =  new readAdmin().checkStubMappingUUID(map.toString());
			       // String checkUUID =  new readAdmin().checkStubMappingUUID(map.toString());
			        
			        verify(postRequestedFor(urlEqualTo(AppTest4.URL)));
			        System.out.println("*************************************************************************");
			        System.exit(1);
			        /*if(checkUUID != null) {
			        	
			        	 //StubMapping checkUUID12 = '735b5757-a2bc-366e-b5a0-851f65b222ea';
			        	 // wm.removeStubMapping(checkUUID12);
			        	 System.out.println(checkUUID);
			        	// System.exit(1);
							RestAssured.baseURI = "http://localhost:8013/__admin/mappings/" + checkUUID;
							RequestSpecification request = RestAssured.given();
							request.header("Content-Type", "application/json");	
							Response response = request.delete();
			        } */
			        //System.exit(1);
			         stubFor(post(urlEqualTo(URL)).willReturn(aResponse().proxiedFrom(PROXYBASEURL)));
			        
			         Response r = RestAssured.given().contentType("application/json").body(map).when().post("http://localhost:8099"+ AppTest4.URL);
			         body = r;
			         
			         WireMock.reset();
			         wireMockServer.stop();
			         
			       
			    }
			    catch (Exception e){
			    	System.out.println(e.getMessage());
			    }
			    catch(AssertionError ar){
			   
			    }
			    finally {
			        if (wireMockServer!=null && wireMockServer.isRunning()) {
			           wireMockServer.stop();
			        }
			    }
			    return body;
	    
	}
	
	
	public static void main(String[] args) throws HttpHostConnectException {
	   map1 = new JSONObject();
		map1.put("apiAction", "userRegistration");
		map1.put("full_name", "swapnilshete111");
		map1.put("email", "swapnil.shete111@test.com");
		map1.put("company", "test");
		map1.put("message", "TestData");
		map1.put("job_title", "TestjobTitle");
	    new AppTest4().tst(map1.toString());
	}
}