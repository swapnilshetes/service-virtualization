package com.virtualproject.virtualDemo;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;


import org.junit.runner.RunWith;

@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
@Provider("APIService") // Set up name of tested provider
@PactFolder("target/pacts") // Point where to find pacts (See also section Pacts source in documentation)
public class testproviderPact
{	
	
	@TestTarget
	public final Target target = new HttpTarget( 8112);
	
	@State("test GET")
	public void toGetState() { }
	
}
	
