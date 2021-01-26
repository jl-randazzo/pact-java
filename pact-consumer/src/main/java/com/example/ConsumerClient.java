package com.example;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class ConsumerClient{
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public ConsumerClient(@Value("${serviceUrl}") String url) {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

		public FooListResponse foos() {
			ParameterizedTypeReference<FooListResponse> responseType = new ParameterizedTypeReference<FooListResponse>() {};
			HttpHeaders headers = new HttpHeaders();
			headers.add("HEADER_KEY", UUID.randomUUID().toString());

			HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url + "/foos", HttpMethod.GET, entity, responseType).getBody();
    }
}
