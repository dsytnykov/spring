package com.dsytnykov.service;

import com.dsytnykov.exception.ResourceNotFoundException;
import com.dsytnykov.model.Post;
import com.dsytnykov.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> getAllPosts() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public Post getByPostId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id " + id)
        );
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return repository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = getByPostId(id);
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return repository.save(post);
    }

    public void deletePost(Long id) {
        Post post = getByPostId(id);
        repository.delete(post);
    }
}
