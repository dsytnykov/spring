package com.dsytnykov.webclient;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SpringWebClient {
    public static void main(String[] args) throws InterruptedException {
        simpleGetData();
        Thread.sleep(1000);
        asynchronousPostData();
        Thread.sleep(1000);
    }

    private static void simpleGetData() {
        WebClient webClient = WebClient.create("https://api.github.com");
        Mono<String> response = webClient.get().retrieve().bodyToMono(String.class);
        response.subscribe(System.out::println);
    }

    private static void asynchronousPostData() {
        WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");
        Mono<String> response = webClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}")
                .retrieve()
                .bodyToMono(String.class);
        response.subscribe(System.out::println);
    }
}
