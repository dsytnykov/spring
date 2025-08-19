package com.dsytnykov.service;

import com.dsytnykov.exception.ResourceNotFoundException;
import com.dsytnykov.model.Post;
import com.dsytnykov.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Captor
    private ArgumentCaptor<Pageable> captor;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    private PostService service;

    @Test
    void shouldGetAllPostsWithPredefinedPageable() {
        when(postRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<Post> allPosts = service.getAllPosts(1, 10, "title", "asc");

        verify(postRepository, times(1)).findAll(captor.capture());
        assertEquals(1, captor.getValue().getPageNumber());
        assertEquals(10, captor.getValue().getPageSize());
        assertEquals("title: ASC", captor.getValue().getSort().toString());
        assertEquals(Page.empty(), allPosts);
    }

    @Nested
    @DisplayName("Test search functionality")
    class SearchTest {

        @Test
        void shouldSearchPosts() {
            when(postRepository.searchPosts(anyString(), any(Pageable.class))).thenReturn(Page.empty());

            service.searchPosts("title", 1, 10, "title", "asc");

            verify(postRepository, never()).findAll(any(Pageable.class));
            verify(postRepository, times(1)).searchPosts(eq("title"), any(Pageable.class));
        }

        @Test
        void shouldSearchAllPostsWithoutKey() {

            service.searchPosts("", 1, 10, "title", "asc");

            verify(postRepository, times(1)).findAll(captor.capture());
            verify(postRepository, never()).searchPosts(anyString(), any(Pageable.class));
            assertEquals(1, captor.getValue().getPageNumber());
        }
    }

    @Test
    void shouldGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(Post.builder().id(1L).title("test").build()));

        Post post = service.getByPostId(1L);

        assertEquals(1L, post.getId());
        assertEquals("test", post.getTitle());
    }

    @Test
    void shouldGetExceptionIfPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByPostId(1L));
    }

}