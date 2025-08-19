package com.dsytnykov.service;

import com.dsytnykov.exception.ResourceNotFoundException;
import com.dsytnykov.model.Comment;
import com.dsytnykov.model.Post;
import com.dsytnykov.repository.CommentRepository;
import com.dsytnykov.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService service;

    @Test
    void shouldGetCommentsByPostId() {
        when(commentRepository.findByPostIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(
                Comment.builder()
                        .id(1L)
                        .build()
                ));

        List<Comment> comments = service.getCommentsByPostId(1L);

        assertEquals(1, comments.size());
        assertEquals(1L, comments.get(0).getId());
    }

    @Test
    void shouldAddCommentToPost() {
        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);

        Post post = Post.builder()
                .id(1L)
                .comments(List.of())
                .title("title")
                .content("content")
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .content("test")
                .author("author")
                .build();
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);

        service.addCommentToPost(1L, comment);

        verify(commentRepository, times(1)).save(captor.capture());
        assertEquals(post, captor.getValue().getPost());
    }

    @Test
    void shouldNotAddCommentIfPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addCommentToPost(1L, Comment.builder().build()));
    }

}