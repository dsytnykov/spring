package com.dsytnykov.controller;

import com.dsytnykov.model.Comment;
import com.dsytnykov.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Captor
    private ArgumentCaptor<Comment> captor;

    @MockitoBean
    private CommentService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetCommentsByPostId() throws Exception {
        mockMvc.perform(get("/api/posts/{postId}/comments", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldAddCommentToPost() throws Exception {
        when(service.addCommentToPost(anyLong(), any(Comment.class))).thenReturn(Comment.builder().build());

        mockMvc.perform(post("/api/posts/{postId}/comments", 1)
                        .contentType("application/json")
                        .content("{\"content\": \"test content\", \"author\": \"test\"}")
                        .with(csrf()))
                .andExpect(status().isCreated());

        verify(service).addCommentToPost(anyLong(), captor.capture());
        assertEquals("test content", captor.getValue().getContent());
    }

}