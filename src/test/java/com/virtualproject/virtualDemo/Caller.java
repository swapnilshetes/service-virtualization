package com.virtualproject.virtualDemo;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;

public class Caller 
{
	void call(String map) {
		 Response r = given().contentType("application/json").body(map.toString()).when().post("http://localhost:8099"+ AppTest4.URL);
         String body = r.getBody().asString();
	    
	}


}
