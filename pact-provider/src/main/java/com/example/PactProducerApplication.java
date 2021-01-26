package com.example;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PactProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PactProducerApplication.class, args);
	}
	
    @RequestMapping(value = "/foos", method = RequestMethod.GET)
    public ResponseEntity<FooListResponse> foos(@RequestHeader("HEADER_KEY") String key) {
    	FooListResponse fooLR = new FooListResponse();
    	fooLR.setIdentifier(key);
    	fooLR.setFoos(Arrays.asList(new Foo(33), new Foo(35)));
        return new ResponseEntity<>(fooLR, HttpStatus.OK);
    }
}
