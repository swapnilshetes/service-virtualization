package com.virtualproject.virtualDemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.codehaus.plexus.util.cli.CommandLineException;
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
import static com.jayway.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

import com.relevantcodes.extentreports.LogStatus;


import static io.restassured.RestAssured.given;

/**
 * Unit test for simple App.
 */
public class wmock
{
	JSONObject map1;
	private int port =8084;
	WireMockServer wireMockServer=null;
	private String realServer ="http://localhost:8181/registration/registration_api.php";
	void wmock() {
		
		    
	}
	@BeforeMethod
	public void beforeMethod(Method method) throws HttpHostConnectException, CommandLineException {
		
		wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
	}
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(port);
		@Test
		public void contextLoad() {
			
		}
		
		@Test
		public void testAPI() {
			 map1 = new JSONObject();
				map1.put("apiAction", "userRegistration");
				map1.put("full_name", "swapnilshete123");
				map1.put("email", "swapnil.shete123@test.com");
				map1.put("company", "test");
				map1.put("message", "TestData");
				map1.put("job_title", "TestjobTitle");
				
			stubFor(post(urlPathMatching("/regi_api"))
					.willReturn(aResponse()
							.withHeader("Content-Type", "application/json")
							.withBody(map1.toString())
							)
					);
			
			RestTemplate restTemplate = new  RestTemplate();
			String resourceURL = realServer ;
			ResponseEntity<String> response = restTemplate.getForEntity(realServer, String.class);
			
			assertNotNull(response);
		}
}