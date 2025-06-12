package com.dsytnykov.javahttpclient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Java11HttpClient {
    public static void main(String[] args) throws InterruptedException {
        simpleGetData();
        Thread.sleep(1000);
        asynchronousPostData();
    }

    private static void simpleGetData() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.github.com")).GET().build();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Status: " + response.statusCode());
                System.out.println("Body: " + response.body());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void asynchronousPostData() {
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}"))
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            response.thenApply(HttpResponse::body).thenAccept(System.out::println).join();
        }
    }
}
