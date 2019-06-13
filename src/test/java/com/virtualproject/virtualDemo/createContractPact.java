package com.virtualproject.virtualDemo;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;



/**
 * Unit test for simple App.
 */
public class createContractPact
{	
	JSONObject mapPact;
	
	
	 @Rule
	 public PactProviderRuleMk2 provider = new PactProviderRuleMk2("APIService", "localhost", 8155 , this);
	 
	 @Pact(consumer = "APIconsumer")
	 public RequestResponsePact createPact(PactDslWithProvider builder) throws JSONException {
		String jsonRsp = "{\"registration_info\":[{\"id\":\"2\",\"full_name\":\"swapnilshete111\",\"company\":\"Bitwise\",\"job_title\":\"TestjobTitle\",\"email\":\"swapnil.shete111@bitwiseglobal.com\",\"message\":\"TestData\"}]}";
			
	     Map<String, String> headers = new HashMap<String, String>();
	     headers.put("Content-Type", "application/json");
	  
	     return builder
	       .given("test GET")
	         .uponReceiving("GET REQUEST")
	         .path("/get_responce_api/getUserInfo/2")
	         .method("GET")
	       .willRespondWith()
	         .status(200)
	         .headers(headers)
	         .body(jsonRsp).toPact();
	        
	 }
	 
 	@Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() throws RestClientException, URISyntaxException, ClientProtocolException, IOException {
 		//System.out.println(provider.getUrl().toString() + "/get_responce_api?apiAction=getUserInfo&id=2");
 		String url=String.format("http://localhost:%d/get_responce_api/getUserInfo/2", 8155);
      
        ResponseEntity<String> response = new RestTemplate()
			      .getForEntity(url, String.class);
        	System.out.println("Responce Body " + response.getBody());
        	assertEquals(response.getStatusCode().value(), 200 );
        	assertTrue(response.getHeaders().get("Content-Type").contains("application/json"));
        	assertTrue(response.getBody().contains("registration_info"));
        
    }
}
	
