package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

public class ConsumerClientContractTest {
	private static final String guidRegex = "(([0-9a-fA-F]){8}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){12})";

	   @Rule
	    public PactProviderRule rule = new PactProviderRule("Foo_Provider", this);

	    @Pact(provider="Foo_Provider", consumer="Foo_Consumer")
	    public PactFragment createFragment(PactDslWithProvider builder) {
	    	String identifier = UUID.randomUUID().toString();

	        Map<String, String> headers = new HashMap<>();
	        headers.put("Content-Type", "application/json;charset=UTF-8");

	        PactDslJsonBody body = new PactDslJsonBody()
	        		.stringValue("identifier", identifier)
	        		.array("foos")
	        		.object()
	        		.integerType("value", 33)
	        		.closeObject()
	        		.object()
	        		.integerType("value", 35)
	        		.closeObject()

	        		.closeArray().asBody();

	        return builder.given("a set of Foos exists on the server")
	        		.uponReceiving("a request for Foos")
	                .path("/foos")
	                .matchHeader("HEADER_KEY", guidRegex, identifier)
	                .method("GET")

	                .willRespondWith()
	                .status(200)
	                .body(body).toFragment();
	    }
	    
	    @Test
	    @PactVerification("Foo_Provider")
	    public void runTest() {
	    	FooListResponse listResponse = new FooListResponse();
	    	listResponse.setFoos(Arrays.asList(new Foo(42), new Foo(100)));
	        new ConsumerClient(rule.getConfig().url()).foos();
	    }
}
