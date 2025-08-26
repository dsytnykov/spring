package com.dsytnykov.controller;

import com.dsytnykov.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService service;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetAllPosts() throws Exception {
        when(service.getAllPosts(anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                        .param("page", "1")
                        .param("size", "11")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(service).getAllPosts(eq(1), eq(11), eq("createdAt"), eq("desc"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldSearchPost() throws Exception {
        when(service.searchPosts(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/search")
                        .param("keyword", "keyword")
                        .param("page", "1")
                        .param("size", "11")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(service).searchPosts(eq("keyword"), eq(1), eq(11), eq("createdAt"), eq("desc"));
    }

    @Test
    void shouldThrowUnauthorizedDuringSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/search")
                        .param("keyword", "keyword")
                        .param("page", "1")
                        .param("size", "11")
                        .param("sortBy", "createdAt")
                        .param("direction", "desc")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldGetPostById() throws Exception {
        when(service.getByPostId(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", 1L)).andExpect(status().isOk());

        verify(service).getByPostId(eq(1L));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldCreatePost() throws Exception {
        when(service.createPost(any())).thenReturn(null);

        mockMvc.perform((MockMvcRequestBuilders.post("/api/posts")
                        .contentType("application/json")
                        .content("{\"title\": \"title\", \"content\": \"content should be more than 10 characters\"}")
                        .with(csrf())))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldDeletePost() throws Exception {
        doNothing().when(service).deletePost(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}", 1L).with(csrf()))
                .andExpect(status().isNoContent());
    }

}