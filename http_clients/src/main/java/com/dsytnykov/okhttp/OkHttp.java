package com.dsytnykov.okhttp;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttp {
    public static void main(String[] args) throws InterruptedException {
        simpleGetData();
        Thread.sleep(1000);
        asynchronousPostData();
    }

    private static void simpleGetData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("https://api.github.com")
                .build();

        try(Response response = client.newCall(request).execute()) {
            System.out.println("Status: " + response.code());
            if (response.body() != null) {
                System.out.println("Body: " + response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void asynchronousPostData() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}", MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("Status: " + response.code());
                if (response.body() != null) {
                    System.out.println("Body: " + response.body().string());
                }
            }
        });
    }
}
