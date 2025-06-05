package com.dsytnykov.service;

import com.dsytnykov.postgresql.model.Post;
import com.dsytnykov.postgresql.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll() { return postRepository.findAll(); }
}
