package com.dsytnykov.controller;

import com.dsytnykov.dto.CommentRequest;
import com.dsytnykov.model.Comment;
import com.dsytnykov.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        return service.getCommentsByPostId(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addCommentToPost(@PathVariable Long postId, @Valid @RequestBody CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .build();
        return service.addCommentToPost(postId, comment);
    }
}
