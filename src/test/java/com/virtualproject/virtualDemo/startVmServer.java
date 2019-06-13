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

public class startVmServer {

	
	public boolean runProcess(String batfile, String directory) throws CommandLineException {

		Commandline commandLine = new Commandline();

		File executable = new File(directory + "/" + batfile);
		commandLine.setExecutable(executable.getAbsolutePath());

		WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));

		WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.out));

		int returnCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (returnCode != 0) {
			System.out.println("Something Bad Happened!");
		} else {
			System.out.println("Taaa!! ddaaaaa!!");
		}
		return true;
	}

	public boolean startVmServerThread() throws IOException, CommandLineException {
		
		//this.runProcess("start-virt.bat", System.getProperty("user.dir")+ "/tmp");
		Runtime.getRuntime().exec("cmd /c start java -jar " + System.getProperty("user.dir")+ "/tmp/wiremock-standalone-2.23.2.jar --port=8013 -proxy-all=\"http://localhost:8181/registration/registration_api.php\" --record-mapping --verbose");
		//Runtime.getRuntime().exec("cmd /c start  cmd.exe /K  java -jar " + System.getProperty("user.dir")+ "/tmp/start-virt.bat");
		return true;
	}

	public static void main(String arg[]) throws IOException, CommandLineException {
		//new startVmServer();
	}
}
