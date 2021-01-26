package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;

@PactBroker(host = "localhost",
port = "9292", scheme = "http")
@Provider("Foo_Provider")
public class PactProducerVerificationTests {
	
	private static Thread BOOT;
	
	@BeforeAll
	public static void before() {
		System.setProperty("pact.verifier.publishResults", "true");
		BOOT = new Thread(() -> PactProducerApplication.main(new String[] { "8080" }));
		BOOT.run();
	}
	
	@AfterAll
	public static void after() {
		try {
			BOOT.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int port = 8080;
	
	@State("a set of Foos exists on the server")
	public void contextLoads() {
	}
	
	@BeforeEach
	void setupTestTarget(PactVerificationContext context) {
		context.setTarget(new HttpTestTarget("localhost", port, "/"));
	}

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerification(PactVerificationContext context) {
		context.verifyInteraction();
	}

}
