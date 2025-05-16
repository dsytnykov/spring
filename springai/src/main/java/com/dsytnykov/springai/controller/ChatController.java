package com.dsytnykov.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/images")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    @GetMapping
    public String aboutImage( @RequestParam("prompt") String prompt, @RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (!Objects.equals(contentType, "image/jpeg") && !Objects.equals(contentType, "image/png")) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        List<Media> images = List.of(
                Media.builder().mimeType(MimeTypeUtils.parseMimeType(contentType)).data(file.getResource()).build()
        );
        UserMessage message = new UserMessage(prompt, images);

        return  chatClient.prompt(new Prompt(message))
                .call()
                .content();

    }

    @GetMapping("/generate")
    public String generateImage(@RequestParam String prompt) {
        return "";
    }
}
