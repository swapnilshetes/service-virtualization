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
public class virtualTestCase {
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String restApi;
	public static String mainrestApi;
	JSONObject map;
	Response resp;
	String url;
	public static String configProxyUse = "NO";

	
	public static boolean pingURL(String url, int timeout) {
		url = url.replaceFirst("^https", "http"); // Otherwise an exception may
													// be thrown on invalid SSL
													// certificates.

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			return true;
		} catch (IOException exception) {
			return false;
		}
	}

	public virtualTestCase() throws IOException, CommandLineException {
		extent = new ExtentReports(System.getProperty("user.dir") + "\\report\\ExtentReportResults_"
				+ new SimpleDateFormat("yyyy-MM-dd'T'HHmmss").format(new Date()) + ".html");
			map = new JSONObject();
			map.put("apiAction", "userRegistration");
			map.put("full_name", "swapnilshete111");
			map.put("email", "swapnil.shete111@bitwiseglobal.com");
			map.put("company", "Bitwise");
			map.put("message", "TestData");
			map.put("job_title", "TestjobTitle");
		
		if (configProxyUse.equals("YES")) {
			restApi = "http://localhost:8013/registration_api";
			//new startVmServer().startVmServerThread();
		} else{
			 System.out.println("Step --1");
			 mainrestApi = "http://localhost:8181/registration/registration_api.php";
			 restApi = "http://localhost:8013/registration_api";
     		// new virtualTestCaseProcess().tst(map.toString());
     	}
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws HttpHostConnectException, CommandLineException {
		
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignCategory("Dev  :: " + "env" + " :: API VERSION - " + "ver"); // Test
		test.setDescription("<span style='font-size: 1em;color: #009be6;'>Rest API :: " + restApi + "</span>");
	}

	@Test
	public void getRequestStatus() throws CommandLineException, JsonProcessingException, IOException {
		
		
		 	RestAssured.baseURI = restApi;
		 	System.out.println(restApi);
		 	
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			request.header("Cache-Control", "no-cache");
			request.body(map.toString());

			Response response = request.post();
			System.out.println("<< ******** >>"+ response.asString());
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
