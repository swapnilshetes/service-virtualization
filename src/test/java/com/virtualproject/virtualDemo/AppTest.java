package com.virtualproject.virtualDemo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.junit.Rule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.junit.Assert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
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
public class AppTest 
{
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String restApi;
	public AppTest() {
		extent = new ExtentReports(System.getProperty("user.dir")+"\\report\\ExtentReportResults_"+ new SimpleDateFormat("yyyy-MM-dd'T'HHmmss").format(new Date()) +".html");
		restApi ="http://localhost:8080/userList";
		//restApi = "http://localhost:8181/ionic-api/?apiAction=ideaListing";
	}	
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8080);
	
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignCategory("Dev  :: " + "env" + " :: API VERSION - " + "ver"); // Test
		test.setDescription("<span style='font-size: 1em;color: #009be6;'>Rest API :: " + restApi + "</span>");
	}
	
	@Test
	public void getRequestStatus() {
		RestAssured.baseURI = restApi;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		
		try{
			assertEquals(200, response.statusCode());
			test.log(LogStatus.PASS,
					"<span style='color: #009406;font-size: 1.4em;'>Header ::</span> <span style='font-size: 0.9em;color: #fb9701;'>"
							+ response.getHeaders() + "</span>"
			);
		} catch(AssertionError ar) {
			test.log(LogStatus.FAIL,"Error :: " + ar.getMessage().toString() +" ");
		}
	}
	
	@Test
	public void getRequestParameterValidation() {
		
	    RestAssured.baseURI = restApi;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		String bodyAsString = response.body().asString();
			//Assert.assertEquals(bodyAsString.contains("Hyderabad") /*Expected value*/, true /*Actual Value*/, "Response body contains Hyderabad");
			//Assert.assertEquals( bodyAsString.contains("id"), true, "Responce get ID");
			//assertEquals("id", bodyAsString.contains("id"), true);
			try{
				assertEquals(bodyAsString.contains("id"), true, "Get proper responce with ID");
				test.log(LogStatus.PASS,
						"<span style='font-size: 1em;color: #fb9701;'>Responce get ID</span>");
			} catch(AssertionError ar) {
				test.log(LogStatus.FAIL,
						"Error :: " + ar.getMessage().toString() +" ");
			}
	}
	
	
		// Test Case Reporting Ends Here
		@AfterMethod()
		public void afterMethod() {
			extent.endTest(test);
			extent.flush();
		}

		@AfterTest()
		public void afterTest() {
			System.out.println(" in After Test");
		}

		@AfterSuite()
		public void afterSuite() {
			extent.close(); // close the Test Suite
		}
	
}
