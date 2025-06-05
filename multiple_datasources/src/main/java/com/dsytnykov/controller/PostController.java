package com.dsytnykov.controller;

import com.dsytnykov.postgresql.model.Post;
import com.dsytnykov.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> findAll() { return postService.findAll(); }
}
