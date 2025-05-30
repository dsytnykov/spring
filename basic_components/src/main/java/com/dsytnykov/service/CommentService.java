package com.dsytnykov.service;

import com.dsytnykov.exception.ResourceNotFoundException;
import com.dsytnykov.model.Comment;
import com.dsytnykov.model.Post;
import com.dsytnykov.repository.CommentRepository;
import com.dsytnykov.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    public Comment addCommentToPost(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id " + postId)
        );

        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
