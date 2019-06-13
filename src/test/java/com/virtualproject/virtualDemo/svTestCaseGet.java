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
import java.sql.SQLException;
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
public class svTestCaseGet {
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String restApi;
	public static String restApiURL="get_responce_api?apiAction=getUserInfo&id=2";
	public static String mainrestApi= "http://localhost:8181/registration/registration_api.php?apiAction=getUserInfo&id=2";
	public static String wireMockAPIHost="http://localhost";
	public static String port ="8112" ;
	JSONObject map;
	Response response;
	String url;
	public static String configProxyUse = "NO";
	
	public svTestCaseGet() throws IOException, CommandLineException, ClassNotFoundException, SQLException {
			restApi = wireMockAPIHost + ":" + port ;
			extent = new ExtentReports(System.getProperty("user.dir") + "\\report\\ExtentReportResults_"
				+ new SimpleDateFormat("yyyy-MM-dd'T'HHmmss").format(new Date()) + ".html");
		
			map = new JSONObject();
			map.put("apiAction", "userRegistration");
		
		
		 String proxyServerHost =wireMockAPIHost +":"+ port;
		 if(!svTestHelper.pingURL(proxyServerHost ,2000)) {
			 System.out.println("Proxy server is not running......! Please start..");
			 System.exit(1);
		 }
		
		if (configProxyUse.equals("NO")) {
			 if(!svTestHelper.pingURL(mainrestApi,2000)) {
				 System.out.println("Main server is not running......! Please check flag..");
				 System.exit(1);
			 }
			 
			 svTestHelper.startRecording(mainrestApi , wireMockAPIHost ,port);
		} 
	}

	@BeforeMethod
	public void beforeMethod(Method method) throws HttpHostConnectException, CommandLineException {
		
		test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()), method.getName());
		test.assignCategory("Dev  :: " + "env" + " :: API VERSION - " + "ver"); // Test
		test.setDescription("<span style='font-size: 1em;color: #009be6;'>Rest API :: " + restApi + "</span>");
		response = svTestHelper.getResponceGetAPI(restApiURL , restApi , map);
	}

	@Test
	public void getRequestStatus() throws CommandLineException, JsonProcessingException, IOException {
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
	public void afterTest() throws ClassNotFoundException, SQLException {
		System.out.println(" in After Test");
				if (configProxyUse.equals("NO")) {
					svTestHelper.stopRecording(wireMockAPIHost ,port);
				}
		}

	@AfterSuite()
	public void afterSuite() {
		extent.close(); // close the Test Suite
	}

}
