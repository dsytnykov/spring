package com.dsytnykov.controller;

import com.dsytnykov.model.Comment;
import com.dsytnykov.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService servcie) {
        this.service = servcie;
    }

    @GetMapping
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return service.getCommentsByPostId(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addCommentToPost(@PathVariable Long postId, @RequestBody Comment comment) {
        return service.addCommentToPost(postId, comment);
    }
}
