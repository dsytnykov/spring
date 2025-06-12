package com.dsytnykov.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class SpringRestTemplate {
    public static void main(String[] args) throws InterruptedException {
        simpleGetData();
        Thread.sleep(1000);
        asynchronousPostData();
    }

    private static void simpleGetData() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> respone = restTemplate.getForEntity("https://api.github.com", String.class);
        System.out.println("Status: " + respone.getStatusCode());
        System.out.println("Body: " + respone.getBody());
    }

    private static void asynchronousPostData() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "title", "foo",
                "body", "bar",
                "userId", 1
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", requestEntity, String.class);
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Body: " + response.getBody());
    }
}
