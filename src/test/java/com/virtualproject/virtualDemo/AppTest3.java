package com.virtualproject.virtualDemo;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
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
public class AppTest3 
{
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String restApi;
	public static String apiAction;
	//Map<String , Object > map;
	JSONObject map;
	public AppTest3() throws ClassNotFoundException, SQLException {
		extent = new ExtentReports(System.getProperty("user.dir")+"\\report\\ExtentReportResults_"+ new SimpleDateFormat("yyyy-MM-dd'T'HHmmss").format(new Date()) +".html");
		restApi ="http://localhost:8181/registration/registration_api.php";
		apiAction="userRegistration";
		//restApi ="http://localhost:8080/registration_api";
		//restApi = "http://localhost:8181/ionic-api/?apiAction=ideaListing";
		//TestHelper.dataBasePreparation();
		
	}	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8080);
	
	@DataProvider
	public Object[][] readDataHelper() throws Exception {
		//List<String[]> testObjArray1 = TestHelper.ReadInputData();
		Object[][] testObjArray =  TestHelper.ReadInputData();
		return testObjArray;
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignCategory("Dev  :: " + "env" + " :: API VERSION - " + "ver"); // Test
		test.setDescription("<span style='font-size: 1em;color: #009be6;'>Rest API :: " + restApi + "</span>");
	}
	
	@Test(dataProvider = "readDataHelper")
	public void getRequestStatus(String SrNo, String full_name, String company, String job_title, String email , String message) {
		//String a = {"apiAction" : "userRegistration" , "full_name" : "swapnilshete3" , "email" : "swapnil.shete4@bitwiseglobal.com" ,"company" : "Bitwise" ,"message" : "TestData" , "job_title" : "TestjobTitle" };
		RestAssured.baseURI = restApi;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");	
		request.body(TestHelper.setJsonFormat(full_name,company,job_title,email,message).toString());

		Response response = request.post();
		System.out.println(" >>"+ response.asString());
		try{
			assertEquals(response.statusCode(), 200 );
			test.log(LogStatus.PASS,
					"<span style='color: #009406;font-size: 1.4em;'>Header ::</span> <span style='font-size: 0.9em;color: #fb9701;'>"
							+ response.getHeaders() + "</span>"
			);
		} catch(AssertionError ar) {
			test.log(LogStatus.FAIL,"Error :: " + ar.getMessage().toString() +" ");
		}
	}
	
	/*@Test(dataProvider = "readDataHelper")
	public void getRequestParameterValidation(String SrNo, String full_name, String company, String job_title, String email , String message) {
		
		RestAssured.baseURI = restApi;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");	
		request.body(TestHelper.setJsonFormat(full_name,company,job_title,email,message).toString());
		Response response = request.post();
		
		String bodyAsString = response.body().asString();
		
			try{
				assertEquals(bodyAsString.contains("id"), true, "Get proper responce with ID");
				test.log(LogStatus.PASS,
						"<span style='font-size: 1em;color: #fb9701;'>Responce get ID</span>");
			} catch(AssertionError ar) {
				test.log(LogStatus.FAIL,
						"Error :: " + ar.getMessage().toString() +" ");
			}
	}
	*/
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
