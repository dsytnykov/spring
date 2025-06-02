package com.dsytnykov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank(message = "Content is required")
    @Size(min = 5, max = 500, message = "Content must be between 5 and 500 characters")
    private String content;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 70, message = "Author must be between 2 and 70 characters")
    private String author;
}
