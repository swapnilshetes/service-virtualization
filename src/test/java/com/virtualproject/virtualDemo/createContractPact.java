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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class createContractPact
{	
	JSONObject mapPact;
	
	
	 @Rule
	 public PactProviderRuleMk2 provider = new PactProviderRuleMk2("APIService", "localhost", 8155 , this);
	 
	 @Pact(consumer = "test_consumer")
	 public RequestResponsePact createPact(PactDslWithProvider builder) throws JSONException {
		mapPact = new JSONObject();
		mapPact.put("apiAction", "userRegistration");
		mapPact.put("full_name", "swapnilshete111");
		mapPact.put("email", "swapnil.shete111@test.com");
		mapPact.put("company", "test");
		mapPact.put("message", "TestData");
		mapPact.put("job_title", "TestjobTitle");
			
	     Map<String, String> headers = new HashMap<String, String>();
	     headers.put("Content-Type", "application/json");
	  
	     return builder
	       .given("test GET")
	         .uponReceiving("GET REQUEST")
	         .path("/apiPact")
	         .method("GET")
	       .willRespondWith()
	         .status(200)
	         .headers(headers)
	         .body(mapPact.toString()).toPact();
	        
	 }
	 
 	@Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() throws RestClientException, URISyntaxException, ClientProtocolException, IOException {
 		//System.out.println(provider.getUrl().toString() + "/get_responce_api?apiAction=getUserInfo&id=2");
 		String url=String.format("http://localhost:%d/apiPact", 8155);
        System.out.println("using url: "+url);
        ResponseEntity<String> response = new RestTemplate()
			      .getForEntity(url, String.class);
        System.out.println(response.toString());
		 	assertEquals(response.getStatusCode().value(), 200 );
			assertTrue(response.getHeaders().get("Content-Type").contains("application/json"));
			//assertTrue(response.getBody().contains("registration_info"));
    }
}
	
