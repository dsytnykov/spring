package com.dsytnykov.service;

import com.dsytnykov.exception.ResourceNotFoundException;
import com.dsytnykov.model.Post;
import com.dsytnykov.repository.PostRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "postsList", key = "T(String).format('%d:%d:%s:%s', #page, #size, #sortBy, #direction )")
    public Page<Post> getAllPosts(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortBy
        );
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable);
    }

    public Page<Post> searchPosts(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(
                direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortBy
        );
        Pageable pageable = PageRequest.of(page, size, sort);
        if(keyword == null || keyword.isEmpty()) {
            return repository.findAll(pageable);
        }
        return repository.searchPosts(keyword, pageable);
    }

    @Cacheable(value = "posts", key = "#id")
    public Post getByPostId(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id " + id)
        );
    }

    @Cacheable(value = "posts", key = "#result.id")
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return repository.save(post);
    }

    @CacheEvict(value = "posts", key = "#id")
    public Post updatePost(Long id, Post postDetails) {
        Post post = getByPostId(id);
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return repository.save(post);
    }

    @CacheEvict(value = "posts", key = "#id")
    public void deletePost(Long id) {
        Post post = getByPostId(id);
        repository.delete(post);
    }
}
