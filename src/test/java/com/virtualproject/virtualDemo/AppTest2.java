package com.virtualproject.virtualDemo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.conn.HttpHostConnectException;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

/**
 * Unit test for simple App.
 */
public class AppTest2 {
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String restApi;
	// Map<String , Object > map;
	JSONObject map;
	Response resp;
	String url;
	public static String configProxyUse = "NO";
	private WireMockServer wireMockServer1;

	
	public AppTest2() throws IOException {
		extent = new ExtentReports(System.getProperty("user.dir") + "\\report\\ExtentReportResults_"
				+ new SimpleDateFormat("yyyy-MM-dd'T'HHmmss").format(new Date()) + ".html");
		url = "http://localhost:8181/registration/registration_api.php";

		// restApi = "http://localhost:8181/ionic-api/?apiAction=ideaListing";
		map = new JSONObject();
		map.put("apiAction", "userRegistration");
		map.put("full_name", "swapnilshete123");
		map.put("email", "swapnil.shete123@test.com");
		map.put("company", "test");
		map.put("message", "TestData");
		map.put("job_title", "TestjobTitle");
	}

	@Rule
	public WireMockRule wireMockRule1 = new WireMockRule(8080);

	@BeforeMethod
	public void beforeMethod(Method method) throws HttpHostConnectException, CommandLineException {

		
		/*
		 * if(upBoolen){ System.out.println("Site Running ...............");
		 * System.out.println(
		 * "Connect with server with continious recording ...............");
		 * restApi ="http://localhost:8181/registration/registration_api.php";
		 * // resp = AppTest4.tst(); }else { System.out.println(
		 * "Site down ..............."); System.out.println(
		 * "Use Proxy ..............."); restApi
		 * ="http://localhost:8013/registration_api";
		 * //this.runProcess("start-virt.bat", System.getProperty("user.dir")+
		 * "/tmp"); //wireMockServer1 = new WireMockServer(); //
		 * wireMockServer1.start(); // configureFor(8013); }
		 * 
		 */
		
		
		//wireMockServer1 = new WireMockServer(8013); 
		//wireMockServer1.start(); // 
		// configureFor("localhost", 8013);
		restApi = "http://localhost:8013/registration_api";
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignCategory("Dev  :: " + "env" + " :: API VERSION - " + "ver"); // Test
		test.setDescription("<span style='font-size: 1em;color: #009be6;'>Rest API :: " + restApi + "</span>");
	}

	@Test
	public void getRequestStatus() throws CommandLineException, JsonProcessingException, IOException {
		
		    Response r1 = RestAssured.given().contentType("application/json").body(map).when().post("http://localhost:8013/registration_api");
		 
		 	RestAssured.baseURI = restApi;
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");	
			request.body(virtualTestCase.map.toString());

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
			
			
		//System.out.println("***********IN*****************");
		if (configProxyUse.equals("NO")) {
			resp = (Response) AppTest4.tst(map.toString());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tree1 = mapper.readTree(resp.asString());
			JsonNode tree2 = mapper.readTree(r1.asString());
			System.out.println("**********Responce 1 ************" + resp.asString());
			System.out.println("**********Responce 2************" + r1.asString());
					if(tree1.equals(tree2)) {
							try {
								assertEquals(resp.statusCode(), 200);
								test.log(LogStatus.PASS,
										"<span style='color: #009406;font-size: 1.4em;'>Header ::</span> <span style='font-size: 0.9em;color: #fb9701;'>"
												+ resp.getHeaders() + "</span>");
							} catch (AssertionError ar) {
								test.log(LogStatus.FAIL, "Error :: " + ar.getMessage().toString() + " ");
							}
					}

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
		wireMockRule1.stop();
	}

	@AfterSuite()
	public void afterSuite() {
		extent.close(); // close the Test Suite
	}

}
