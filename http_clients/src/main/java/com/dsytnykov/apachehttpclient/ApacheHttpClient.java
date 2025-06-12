package com.dsytnykov.apachehttpclient;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

public class ApacheHttpClient {
    public static void main(String[] args) throws InterruptedException {
        simpleGetData();
        Thread.sleep(1000);
        asynchronousPostData();
    }

    private static void simpleGetData() {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://api.github.com");

            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                System.out.println("Status: " + response.getCode());
                if(entity != null) {
                    System.out.println("Body: " + EntityUtils.toString(entity));
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void asynchronousPostData() {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost("https://jsonplaceholder.typicode.com/posts");
            request.setHeader("Content-Type", "application/json");
            String json = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
            request.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = client.execute(request)) {
                System.out.println("Status: " + response.getCode());
                System.out.println("Response entity: " + EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

